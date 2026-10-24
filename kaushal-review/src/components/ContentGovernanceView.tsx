import React, { useState, useEffect } from 'react';
import {
  Sparkles,
  CheckCircle2,
  ArrowLeft,
  ArrowRight,
  ShieldAlert,
  Check,
  X,
  Edit3,
  Loader2,
  Clock,
  FileText,
  History,
  UserCheck,
} from 'lucide-react';
import {
  AdminPersona,
  ContentChangeRequest,
  ContentVersion,
  ModuleData,
} from '../types';
import {
  ADMIN_PERSONAS,
  generateAiSafetyDraft,
  reviewContentChangeRequestInDb,
  submitContentChangeRequestInDb,
} from '../services/kaushalDatabase';

interface ContentGovernanceViewProps {
  modules: ModuleData[];
  changeRequests: ContentChangeRequest[];
  versions: ContentVersion[];
  currentAdmin: AdminPersona;
  onSelectAdmin: (persona: AdminPersona) => void;
  initialRequestToInspect?: ContentChangeRequest | null;
  requirePeerApprovalDefault: boolean;
  onBackToDashboard: () => void;
}

export const ContentGovernanceView: React.FC<ContentGovernanceViewProps> = ({
  modules,
  changeRequests,
  versions,
  currentAdmin,
  onSelectAdmin,
  initialRequestToInspect,
  requirePeerApprovalDefault,
  onBackToDashboard,
}) => {
  // Stepper state: 1 = Select Content, 2 = Describe Change & AI Draft, 3 = Review & Peer Approval Flow
  const [step, setStep] = useState<1 | 2 | 3>(initialRequestToInspect ? 3 : 1);
  const [activeRequest, setActiveRequest] = useState<ContentChangeRequest | null>(
    initialRequestToInspect || null
  );

  // Form state
  const [selectedModuleId, setSelectedModuleId] = useState<string>(
    modules[0]?.moduleId || 'M01'
  );
  const selectedModule =
    modules.find((m) => m.moduleId === selectedModuleId) || modules[0];

  const [selectedChapterId, setSelectedChapterId] = useState<string>(
    selectedModule?.chapters[1]?.chapterId ||
      selectedModule?.chapters[0]?.chapterId ||
      'M01C02'
  );
  const selectedChapter =
    selectedModule?.chapters.find((c) => c.chapterId === selectedChapterId) ||
    selectedModule?.chapters[0];

  const [selectedScreenId, setSelectedScreenId] = useState<string>(
    selectedChapter?.screens[0]?.screenId || 'M01C02S04'
  );
  const selectedScreen =
    selectedChapter?.screens.find((s) => s.screenId === selectedScreenId) ||
    selectedChapter?.screens[0];

  const [changeDescription, setChangeDescription] = useState<string>(
    'Change the instruction to tell workers to evacuate immediately if the fire is too large to control.'
  );
  const [manualOrFinalContent, setManualOrFinalContent] = useState<string>('');
  const [requirePeerApproval, setRequirePeerApproval] = useState<boolean>(
    requirePeerApprovalDefault
  );

  // AI Draft state (Panels 5 & 6)
  const [isGeneratingAi, setIsGeneratingAi] = useState(false);
  const [aiDraftResult, setAiDraftResult] = useState<{
    draftEn: string;
    draftHi: string;
    draftSat: string;
    summaryNote: string;
  } | null>(null);
  const [aiDraftStatus, setAiDraftStatus] = useState<
    'IDLE' | 'PENDING_REVIEW' | 'ACCEPTED' | 'EDITING' | 'DISCARDED'
  >('IDLE');
  const [editableAiDraft, setEditableAiDraft] = useState<string>('');

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [reviewingMessage, setReviewingMessage] = useState<string | null>(null);

  useEffect(() => {
    if (initialRequestToInspect) {
      setActiveRequest(initialRequestToInspect);
      setStep(3);
    }
  }, [initialRequestToInspect]);

  // Keep activeRequest synced with real-time Firestore updates
  useEffect(() => {
    if (activeRequest) {
      const updated = changeRequests.find(
        (r) => r.requestId === activeRequest.requestId
      );
      if (updated) {
        setActiveRequest(updated);
      }
    }
  }, [changeRequests, activeRequest]);

  const handleModuleChange = (modId: string) => {
    setSelectedModuleId(modId);
    const mod = modules.find((m) => m.moduleId === modId);
    if (mod && mod.chapters.length > 0) {
      const firstCh = mod.chapters[0];
      setSelectedChapterId(firstCh.chapterId);
      if (firstCh.screens.length > 0) {
        setSelectedScreenId(firstCh.screens[0].screenId);
      }
    }
  };

  const handleChapterChange = (chId: string) => {
    setSelectedChapterId(chId);
    const ch = selectedModule?.chapters.find((c) => c.chapterId === chId);
    if (ch && ch.screens.length > 0) {
      setSelectedScreenId(ch.screens[0].screenId);
    }
  };

  const handleGenerateWithAi = async () => {
    if (!changeDescription.trim()) return;
    setIsGeneratingAi(true);
    try {
      const draft = await generateAiSafetyDraft({
        moduleName: selectedModule?.name || 'Fire & Explosion Response',
        chapterName: selectedChapter?.title || 'Fire Extinguisher Use',
        screenName: selectedScreen?.title || 'Correct Extinguisher Selection',
        currentContent: selectedScreen?.contentEn || '',
        changeDescription,
        sector: currentAdmin.sector,
      });
      setAiDraftResult(draft);
      setEditableAiDraft(draft.draftEn);
      setAiDraftStatus('PENDING_REVIEW');
    } finally {
      setIsGeneratingAi(false);
    }
  };

  const handleAcceptAiDraft = () => {
    if (!aiDraftResult) return;
    const finalWording = editableAiDraft.trim() || aiDraftResult.draftEn;
    setManualOrFinalContent(finalWording);
    setAiDraftStatus('ACCEPTED');
  };

  const handleDiscardAiDraft = () => {
    setAiDraftResult(null);
    setEditableAiDraft('');
    setAiDraftStatus('DISCARDED');
  };

  const handleSubmitChangeRequest = async () => {
    if (!selectedModule || !selectedChapter || !selectedScreen) return;
    const finalWording =
      manualOrFinalContent.trim() ||
      (aiDraftStatus === 'ACCEPTED' ? editableAiDraft.trim() : '') ||
      changeDescription.trim();

    if (!finalWording) return;

    setIsSubmitting(true);
    try {
      const created = await submitContentChangeRequestInDb({
        moduleId: selectedModule.moduleId,
        moduleName: selectedModule.name,
        chapterId: selectedChapter.chapterId,
        chapterName: selectedChapter.title,
        screenId: selectedScreen.screenId,
        screenName: selectedScreen.title,
        currentContent: selectedScreen.contentEn,
        description: changeDescription,
        proposedContent: finalWording,
        proposedContentHi: aiDraftResult?.draftHi,
        proposedContentSat: aiDraftResult?.draftSat,
        aiDraftUsed: aiDraftStatus === 'ACCEPTED',
        adminReviewed: true,
        approvalRequired: requirePeerApproval,
        actor: currentAdmin,
        baseVersion: selectedModule.currentVersion,
      });
      setActiveRequest(created);
      setStep(3);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handlePeerDecision = async (
    reviewerPersona: AdminPersona,
    decision: 'approved' | 'rejected'
  ) => {
    if (!activeRequest) return;
    setReviewingMessage(null);
    try {
      const updated = await reviewContentChangeRequestInDb(
        activeRequest,
        reviewerPersona,
        decision
      );
      setActiveRequest(updated);
      setReviewingMessage(
        decision === 'approved'
          ? updated.status === 'PUBLISHED'
            ? `All 3/3 peer approvals complete! Version ${updated.proposedVersion} has been automatically published.`
            : `Approved by ${reviewerPersona.name}. Status updated.`
          : `Change request rejected by ${reviewerPersona.name}.`
      );
    } catch (err: any) {
      setReviewingMessage(err?.message || 'Unable to process peer review action.');
    }
  };

  const startNewRequest = () => {
    setActiveRequest(null);
    setAiDraftResult(null);
    setAiDraftStatus('IDLE');
    setManualOrFinalContent('');
    setStep(1);
  };

  return (
    <div className="space-y-6">
      {/* Top Bar with Back link */}
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div className="flex items-center gap-3">
          <button
            type="button"
            onClick={onBackToDashboard}
            className="inline-flex items-center gap-1.5 text-xs font-semibold text-slate-600 hover:text-[#1d63dc] bg-white px-3 py-1.5 rounded-lg border border-slate-200 shadow-2xs transition-colors cursor-pointer"
          >
            <ArrowLeft className="w-3.5 h-3.5" />
            Back to Dashboard
          </button>
          <div>
            <h2 className="text-xl font-bold text-slate-900">Request Content Edit</h2>
            <p className="text-xs text-slate-500">
              Controlled safety content governance with same-industry peer review
            </p>
          </div>
        </div>

        {/* Request switcher pills so admin can inspect existing requests or create new */}
        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={startNewRequest}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold border transition-colors cursor-pointer ${
              step !== 3
                ? 'bg-[#1d63dc] text-white border-[#1d63dc]'
                : 'bg-white text-slate-700 border-slate-200 hover:bg-slate-50'
            }`}
          >
            + New Edit Request
          </button>
          {changeRequests.slice(0, 3).map((req) => {
            const approvedCount = req.approvals.filter(
              (a) => a.status === 'approved'
            ).length;
            return (
              <button
                key={req.requestId}
                type="button"
                onClick={() => {
                  setActiveRequest(req);
                  setStep(3);
                }}
                className={`px-3 py-1.5 rounded-lg text-xs font-semibold border transition-colors cursor-pointer ${
                  activeRequest?.requestId === req.requestId && step === 3
                    ? 'bg-slate-900 text-white border-slate-900'
                    : 'bg-white text-slate-600 border-slate-200 hover:bg-slate-50'
                }`}
              >
                {req.moduleName.split(' ')[0]} ({approvedCount}/{req.approvals.length})
              </button>
            );
          })}
        </div>
      </div>

      {/* 3-Step Horizontal Stepper matching Panels 4 & 5 */}
      <div className="bg-white rounded-xl border border-slate-200 px-6 py-4 shadow-2xs">
        <div className="flex items-center justify-between max-w-2xl">
          {/* Step 1 */}
          <button
            type="button"
            onClick={() => {
              setActiveRequest(null);
              setStep(1);
            }}
            className="flex items-center gap-2.5 text-left cursor-pointer"
          >
            <span
              className={`w-7 h-7 rounded-full text-xs font-bold flex items-center justify-center transition-colors ${
                step === 1
                  ? 'bg-[#1d63dc] text-white'
                  : step > 1
                    ? 'bg-emerald-500 text-white'
                    : 'bg-slate-100 text-slate-500'
              }`}
            >
              {step > 1 ? <Check className="w-4 h-4" /> : '1'}
            </span>
            <span
              className={`text-sm font-semibold ${
                step === 1
                  ? 'text-[#1d63dc]'
                  : step > 1
                    ? 'text-emerald-700'
                    : 'text-slate-500'
              }`}
            >
              Select Content
            </span>
          </button>

          <div className="flex-1 h-px bg-slate-200 mx-4" />

          {/* Step 2 */}
          <button
            type="button"
            onClick={() => {
              setActiveRequest(null);
              setStep(2);
            }}
            className="flex items-center gap-2.5 text-left cursor-pointer"
          >
            <span
              className={`w-7 h-7 rounded-full text-xs font-bold flex items-center justify-center transition-colors ${
                step === 2
                  ? 'bg-[#1d63dc] text-white'
                  : step > 2
                    ? 'bg-emerald-500 text-white'
                    : 'bg-slate-100 text-slate-500'
              }`}
            >
              {step > 2 ? <Check className="w-4 h-4" /> : '2'}
            </span>
            <span
              className={`text-sm font-semibold ${
                step === 2
                  ? 'text-[#1d63dc]'
                  : step > 2
                    ? 'text-emerald-700'
                    : 'text-slate-500'
              }`}
            >
              Describe Change
            </span>
          </button>

          <div className="flex-1 h-px bg-slate-200 mx-4" />

          {/* Step 3 */}
          <button
            type="button"
            onClick={() => {
              if (activeRequest || changeRequests[0]) {
                if (!activeRequest) setActiveRequest(changeRequests[0]);
                setStep(3);
              }
            }}
            className="flex items-center gap-2.5 text-left cursor-pointer"
          >
            <span
              className={`w-7 h-7 rounded-full text-xs font-bold flex items-center justify-center transition-colors ${
                step === 3
                  ? 'bg-[#1d63dc] text-white'
                  : 'bg-slate-100 text-slate-500'
              }`}
            >
              3
            </span>
            <span
              className={`text-sm font-semibold ${
                step === 3 ? 'text-[#1d63dc]' : 'text-slate-500'
              }`}
            >
              Review & Submit
            </span>
          </button>
        </div>
      </div>

      {/* PANEL 4: Step 1 — Select Content */}
      {step === 1 && (
        <div className="bg-white rounded-xl border border-slate-200 p-6 shadow-2xs space-y-6">
          <div className="max-w-2xl space-y-5">
            {/* Module Dropdown */}
            <div>
              <label className="block text-sm font-bold text-slate-800 mb-2">
                Module
              </label>
              <select
                value={selectedModuleId}
                onChange={(e) => handleModuleChange(e.target.value)}
                className="w-full px-4 py-3 rounded-lg border border-slate-300 bg-white text-slate-900 text-sm font-medium focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
              >
                {modules.map((m) => (
                  <option key={m.moduleId} value={m.moduleId}>
                    {m.name} (Current Published: v{m.currentVersion})
                  </option>
                ))}
              </select>
            </div>

            {/* Chapter Dropdown */}
            <div>
              <label className="block text-sm font-bold text-slate-800 mb-2">
                Chapter
              </label>
              <select
                value={selectedChapterId}
                onChange={(e) => handleChapterChange(e.target.value)}
                className="w-full px-4 py-3 rounded-lg border border-slate-300 bg-white text-slate-900 text-sm font-medium focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
              >
                {selectedModule?.chapters.map((ch) => (
                  <option key={ch.chapterId} value={ch.chapterId}>
                    {ch.title}
                  </option>
                ))}
              </select>
            </div>

            {/* Screen / Section Dropdown */}
            <div>
              <label className="block text-sm font-bold text-slate-800 mb-2">
                Screen / Section
              </label>
              <select
                value={selectedScreenId}
                onChange={(e) => setSelectedScreenId(e.target.value)}
                className="w-full px-4 py-3 rounded-lg border border-slate-300 bg-white text-slate-900 text-sm font-medium focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
              >
                {selectedChapter?.screens.map((sc) => (
                  <option key={sc.screenId} value={sc.screenId}>
                    {sc.title}
                  </option>
                ))}
              </select>
            </div>

            {/* Current Published Version Context Preview (Immutable active version) */}
            {selectedScreen && (
              <div className="p-4 rounded-xl bg-slate-50 border border-slate-200 space-y-2">
                <div className="flex items-center justify-between">
                  <span className="text-xs font-bold uppercase tracking-wider text-slate-500 flex items-center gap-1.5">
                    <FileText className="w-3.5 h-3.5 text-[#1d63dc]" />
                    Current Active Published Content (v{selectedModule?.currentVersion})
                  </span>
                  <span className="text-[11px] font-semibold px-2 py-0.5 rounded bg-emerald-100 text-emerald-800">
                    Immutable Active Version
                  </span>
                </div>
                <p className="text-sm text-slate-800 leading-relaxed">
                  {selectedScreen.contentEn}
                </p>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 pt-2 border-t border-slate-200/80 text-xs text-slate-600">
                  <div>
                    <span className="font-semibold text-slate-700">Hindi: </span>
                    {selectedScreen.contentHi}
                  </div>
                  <div>
                    <span className="font-semibold text-slate-700">Santali: </span>
                    {selectedScreen.contentSat}
                  </div>
                </div>
              </div>
            )}

            <div className="pt-2 flex justify-end">
              <button
                type="button"
                onClick={() => setStep(2)}
                className="px-5 py-2.5 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white text-sm font-semibold flex items-center gap-2 shadow-xs cursor-pointer"
              >
                Continue to Describe Change
                <ArrowRight className="w-4 h-4" />
              </button>
            </div>
          </div>
        </div>
      )}

      {/* PANELS 5 & 6: Step 2 — Describe Change with Optional AI + Peer Approval Setting */}
      {step === 2 && (
        <div className="space-y-6">
          <div className="bg-white rounded-xl border border-slate-200 p-6 shadow-2xs">
            {/* Selected breadcrumb context */}
            <div className="mb-5 pb-4 border-b border-slate-100 flex flex-wrap items-center justify-between gap-2">
              <div className="text-xs text-slate-500">
                Target:{' '}
                <span className="font-semibold text-slate-800">
                  {selectedModule?.name}
                </span>{' '}
                →{' '}
                <span className="font-semibold text-slate-800">
                  {selectedChapter?.title}
                </span>{' '}
                →{' '}
                <span className="font-semibold text-[#1d63dc]">
                  {selectedScreen?.title}
                </span>
              </div>
              <span className="text-xs font-mono bg-slate-100 text-slate-700 px-2.5 py-1 rounded-md">
                Base v{selectedModule?.currentVersion} → Proposed v
                {(() => {
                  const parts = (selectedModule?.currentVersion || '2.0').split('.');
                  return `${parts[0]}.${parseInt(parts[1] || '0', 10) + 1}`;
                })()}
              </span>
            </div>

            {/* Side-by-Side Layout from Panel 5 */}
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
              {/* Left: Describe the Required Change */}
              <div className="lg:col-span-7 space-y-4">
                <div>
                  <label className="block text-sm font-bold text-slate-900 mb-2">
                    Describe the Required Change
                  </label>
                  <div className="relative">
                    <textarea
                      rows={5}
                      maxLength={500}
                      value={changeDescription}
                      onChange={(e) => setChangeDescription(e.target.value)}
                      placeholder="Write what needs to be changed or enter exact updated safety wording..."
                      className="w-full p-4 rounded-xl border border-slate-300 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-[#1d63dc] leading-relaxed"
                    />
                    <span className="absolute bottom-3 right-3 text-xs text-slate-400 font-mono">
                      {changeDescription.length}/500
                    </span>
                  </div>
                </div>

                {/* Direct manual wording box (optional if admin wants to type exact text or after accepting AI draft) */}
                <div>
                  <div className="flex items-center justify-between mb-1.5">
                    <label className="text-xs font-bold uppercase tracking-wider text-slate-600">
                      Proposed Screen Wording (Admin Controlled)
                    </label>
                    {aiDraftStatus === 'ACCEPTED' && (
                      <span className="text-[11px] font-semibold text-emerald-700 bg-emerald-50 px-2 py-0.5 rounded border border-emerald-200">
                        ✓ AI Draft Accepted by Admin
                      </span>
                    )}
                  </div>
                  <textarea
                    rows={3}
                    value={manualOrFinalContent}
                    onChange={(e) => setManualOrFinalContent(e.target.value)}
                    placeholder="Leave blank to submit your change description directly, or click 'Generate with AI' to draft..."
                    className="w-full p-3.5 rounded-xl border border-slate-200 bg-slate-50/70 text-sm text-slate-800 focus:bg-white focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
                  />
                </div>
              </div>

              {/* Right: Optional AI Assistance Card (Panel 5) & AI Suggested Content Review (Panel 6) */}
              <div className="lg:col-span-5 space-y-4">
                <div className="rounded-xl border border-indigo-100 bg-gradient-to-br from-indigo-50/70 via-purple-50/50 to-white p-5 shadow-2xs">
                  <div className="flex items-start gap-3">
                    <div className="w-9 h-9 rounded-lg bg-indigo-600/10 text-indigo-600 flex items-center justify-center shrink-0">
                      <Sparkles className="w-5 h-5" />
                    </div>
                    <div>
                      <h4 className="text-sm font-bold text-slate-900">
                        Use AI to Draft (Optional)
                      </h4>
                      <p className="text-xs text-slate-600 mt-1 leading-relaxed">
                        Get a suggested version based on your description. AI drafts never publish directly and require your review.
                      </p>
                    </div>
                  </div>

                  <button
                    type="button"
                    onClick={handleGenerateWithAi}
                    disabled={isGeneratingAi || !changeDescription.trim()}
                    className="mt-4 w-full py-2.5 px-4 rounded-lg bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white font-semibold text-sm shadow-xs transition-all flex items-center justify-center gap-2 cursor-pointer disabled:opacity-50"
                  >
                    {isGeneratingAi ? (
                      <>
                        <Loader2 className="w-4 h-4 animate-spin" />
                        Drafting with Gemini AI...
                      </>
                    ) : (
                      <>
                        <Sparkles className="w-4 h-4" />
                        Generate with AI
                      </>
                    )}
                  </button>
                </div>

                {/* PANEL 6: AI Suggested Content (Admin Review) */}
                {aiDraftResult && aiDraftStatus !== 'DISCARDED' && (
                  <div className="rounded-xl border border-blue-200 bg-white p-5 shadow-sm space-y-4 animate-in fade-in duration-150">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-2">
                        <Sparkles className="w-4 h-4 text-indigo-600" />
                        <h4 className="text-sm font-bold text-slate-900">
                          AI Suggested Content
                        </h4>
                      </div>
                      <span className="text-[11px] font-medium text-slate-500">
                        Admin Review Required
                      </span>
                    </div>

                    {aiDraftStatus === 'EDITING' ? (
                      <textarea
                        rows={4}
                        value={editableAiDraft}
                        onChange={(e) => setEditableAiDraft(e.target.value)}
                        className="w-full p-3.5 rounded-xl border border-blue-300 bg-blue-50/40 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
                      />
                    ) : (
                      <div className="p-4 rounded-xl bg-blue-50/60 border border-blue-200/80 text-sm text-slate-800 leading-relaxed">
                        {editableAiDraft}
                      </div>
                    )}

                    {/* Action Buttons matching Panel 6: Accept Draft | Edit | Discard */}
                    <div className="flex items-center gap-2.5 pt-1">
                      <button
                        type="button"
                        onClick={handleAcceptAiDraft}
                        className="flex-1 py-2 px-3 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white text-xs font-semibold shadow-2xs transition-colors cursor-pointer"
                      >
                        Accept Draft
                      </button>
                      <button
                        type="button"
                        onClick={() =>
                          setAiDraftStatus(
                            aiDraftStatus === 'EDITING' ? 'PENDING_REVIEW' : 'EDITING'
                          )
                        }
                        className="py-2 px-4 rounded-lg border border-blue-300 bg-white hover:bg-blue-50 text-[#1d63dc] text-xs font-semibold transition-colors flex items-center gap-1 cursor-pointer"
                      >
                        <Edit3 className="w-3.5 h-3.5" />
                        {aiDraftStatus === 'EDITING' ? 'Done' : 'Edit'}
                      </button>
                      <button
                        type="button"
                        onClick={handleDiscardAiDraft}
                        className="py-2 px-4 rounded-lg border border-rose-300 bg-white hover:bg-rose-50 text-rose-600 text-xs font-semibold transition-colors cursor-pointer"
                      >
                        Discard
                      </button>
                    </div>
                  </div>
                )}
              </div>
            </div>

            {/* Peer Approval Setting (PRD Section 10.4 / TASK-ADM-110..114) */}
            <div className="mt-6 pt-5 border-t border-slate-200 flex flex-col sm:flex-row sm:items-center justify-between gap-4 bg-slate-50/70 p-4 rounded-xl border border-slate-200/80">
              <div className="space-y-1">
                <div className="flex items-center gap-2">
                  <ShieldAlert className="w-4 h-4 text-[#1d63dc]" />
                  <span className="text-sm font-bold text-slate-900">
                    Require peer approval before publishing changes
                  </span>
                </div>
                <p className="text-xs text-slate-600">
                  Approval Group:{' '}
                  <span className="font-semibold text-slate-800">
                    Same industry/content scope ({currentAdmin.sector} Safety Peer Group)
                  </span>{' '}
                  • Required approvals:{' '}
                  <span className="font-semibold text-slate-800">ALL (3/3)</span>
                </p>
              </div>

              <button
                type="button"
                role="switch"
                aria-checked={requirePeerApproval}
                onClick={() => setRequirePeerApproval(!requirePeerApproval)}
                className={`relative inline-flex h-6 w-11 shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out ${
                  requirePeerApproval ? 'bg-[#1d63dc]' : 'bg-slate-300'
                }`}
              >
                <span
                  className={`pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow-sm ring-0 transition duration-200 ease-in-out ${
                    requirePeerApproval ? 'translate-x-5' : 'translate-x-0'
                  }`}
                />
              </button>
            </div>

            {/* Footer navigation */}
            <div className="mt-6 flex items-center justify-between">
              <button
                type="button"
                onClick={() => setStep(1)}
                className="px-4 py-2.5 rounded-lg border border-slate-300 bg-white hover:bg-slate-50 text-slate-700 text-xs font-semibold cursor-pointer"
              >
                ← Back to Select Content
              </button>

              <button
                type="button"
                onClick={handleSubmitChangeRequest}
                disabled={isSubmitting || !changeDescription.trim()}
                className="px-6 py-2.5 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white text-sm font-semibold shadow-xs transition-colors flex items-center gap-2 cursor-pointer disabled:opacity-50"
              >
                {isSubmitting ? (
                  <>
                    <Loader2 className="w-4 h-4 animate-spin" />
                    Submitting Request...
                  </>
                ) : requirePeerApproval ? (
                  'Send Change Request'
                ) : (
                  'Publish Change Version'
                )}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* PANEL 7: Step 3 — Edit Request Submitted & Peer Approval Flow */}
      {step === 3 && activeRequest && (
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
          {/* Left: Panel 7 Peer Approval Card */}
          <div className="lg:col-span-7 bg-white rounded-xl border border-slate-200 p-6 shadow-2xs space-y-6">
            {/* Top Status Banner matching Panel 7 */}
            <div className="flex items-start gap-3.5">
              <div
                className={`w-11 h-11 rounded-full flex items-center justify-center shrink-0 ${
                  activeRequest.status === 'REJECTED'
                    ? 'bg-rose-500 text-white'
                    : 'bg-emerald-500 text-white'
                }`}
              >
                {activeRequest.status === 'REJECTED' ? (
                  <X className="w-6 h-6" />
                ) : (
                  <Check className="w-6 h-6" />
                )}
              </div>
              <div>
                <h3 className="text-lg font-bold text-slate-900">
                  {activeRequest.status === 'PUBLISHED'
                    ? 'Change Request Published'
                    : activeRequest.status === 'REJECTED'
                      ? 'Change Request Rejected'
                      : 'Edit Request Submitted'}
                </h3>
                <p className="text-sm text-slate-500">
                  {activeRequest.status === 'PUBLISHED'
                    ? `All required peer approvals received. Version ${activeRequest.proposedVersion} is now the active published version.`
                    : activeRequest.status === 'REJECTED'
                      ? 'This proposed version was rejected during peer review. The current published version remains unchanged.'
                      : 'Your edit request has been submitted for peer review.'}
                </p>
              </div>
            </div>

            {/* Inner Approval Card matching Panel 7 */}
            <div className="rounded-xl border border-slate-200 bg-slate-50/50 p-5 space-y-5">
              <div className="flex flex-wrap items-center justify-between gap-2 border-b border-slate-200/80 pb-4">
                <div>
                  <h4 className="text-base font-bold text-slate-900">
                    {activeRequest.moduleName} – Version {activeRequest.proposedVersion}
                  </h4>
                  <p className="text-sm font-bold text-slate-700 mt-0.5">
                    Approval Status:{' '}
                    <span className="text-[#1d63dc]">
                      {
                        activeRequest.approvals.filter((a) => a.status === 'approved')
                          .length
                      }{' '}
                      / {activeRequest.approvals.length}
                    </span>
                  </p>
                </div>
                <span
                  className={`px-2.5 py-1 rounded-md text-xs font-bold uppercase tracking-wider ${
                    activeRequest.status === 'PUBLISHED'
                      ? 'bg-emerald-100 text-emerald-800'
                      : activeRequest.status === 'REJECTED'
                        ? 'bg-rose-100 text-rose-800'
                        : 'bg-amber-100 text-amber-800'
                  }`}
                >
                  {activeRequest.status}
                </span>
              </div>

              {/* Timeline of Same-Industry Peer Reviewers (Admin A, Admin B, Admin C) */}
              <div className="space-y-4 relative pl-2">
                {activeRequest.approvals.map((peer, idx) => {
                  const isApproved = peer.status === 'approved';
                  const isRejected = peer.status === 'rejected';
                  return (
                    <div
                      key={peer.reviewerId}
                      className="flex items-center justify-between gap-3 relative"
                    >
                      {/* Connector line */}
                      {idx < activeRequest.approvals.length - 1 && (
                        <div
                          className={`absolute left-[7px] top-6 w-0.5 h-8 ${
                            isApproved ? 'bg-emerald-400' : 'bg-slate-300'
                          }`}
                        />
                      )}

                      <div className="flex items-center gap-3">
                        <span
                          className={`w-4 h-4 rounded-full shrink-0 z-10 ${
                            isApproved
                              ? 'bg-emerald-500'
                              : isRejected
                                ? 'bg-rose-500'
                                : 'bg-[#1d63dc]'
                          }`}
                        />
                        <img
                          src={peer.avatarUrl}
                          alt={peer.reviewerName}
                          className="w-8 h-8 rounded-full object-cover border border-slate-200"
                        />
                        <div>
                          <p className="text-sm font-semibold text-slate-900">
                            {peer.reviewerName}
                          </p>
                          <p className="text-[11px] text-slate-500">{peer.siteName}</p>
                        </div>
                      </div>

                      <div className="flex items-center gap-4">
                        <span
                          className={`px-2.5 py-1 rounded-md text-xs font-semibold ${
                            isApproved
                              ? 'bg-emerald-100 text-emerald-700'
                              : isRejected
                                ? 'bg-rose-100 text-rose-700'
                                : 'bg-rose-50 text-rose-600 border border-rose-200'
                          }`}
                        >
                          {isApproved
                            ? 'Approved'
                            : isRejected
                              ? 'Rejected'
                              : 'Pending'}
                        </span>

                        <span className="text-xs text-slate-500 w-24 text-right font-mono">
                          {peer.timestamp || '—'}
                        </span>
                      </div>
                    </div>
                  );
                })}
              </div>

              {/* Interactive Peer Reviewer Action Bar (TASK-ADM-113) */}
              {activeRequest.status !== 'PUBLISHED' &&
                activeRequest.status !== 'REJECTED' && (
                  <div className="pt-4 border-t border-slate-200 space-y-3">
                    <div className="flex flex-wrap items-center justify-between gap-2">
                      <span className="text-xs font-semibold text-slate-700 flex items-center gap-1.5">
                        <UserCheck className="w-4 h-4 text-[#1d63dc]" />
                        Same-Industry Peer Review Actions:
                      </span>
                      <div className="flex items-center gap-1.5">
                        <span className="text-[11px] text-slate-500">Acting as:</span>
                        {ADMIN_PERSONAS.map((p) => (
                          <button
                            key={p.id}
                            type="button"
                            onClick={() => onSelectAdmin(p)}
                            className={`px-2 py-0.5 rounded text-[11px] font-semibold cursor-pointer ${
                              currentAdmin.id === p.id
                                ? 'bg-[#1d63dc] text-white'
                                : 'bg-white border border-slate-200 text-slate-600 hover:bg-slate-100'
                            }`}
                          >
                            {p.name}
                          </button>
                        ))}
                      </div>
                    </div>

                    <div className="flex flex-wrap items-center gap-2.5">
                      {activeRequest.approvals
                        .filter((a) => a.status === 'pending')
                        .map((pendingPeer) => {
                          const persona =
                            ADMIN_PERSONAS.find(
                              (p) => p.id === pendingPeer.reviewerId
                            ) || currentAdmin;
                          return (
                            <div
                              key={pendingPeer.reviewerId}
                              className="flex items-center gap-2 bg-white p-2 rounded-lg border border-slate-200"
                            >
                              <span className="text-xs font-semibold text-slate-700">
                                {pendingPeer.reviewerName}:
                              </span>
                              <button
                                type="button"
                                onClick={() =>
                                  handlePeerDecision(persona, 'approved')
                                }
                                className="px-3 py-1.5 rounded-md bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-semibold cursor-pointer"
                              >
                                Approve
                              </button>
                              <button
                                type="button"
                                onClick={() =>
                                  handlePeerDecision(persona, 'rejected')
                                }
                                className="px-3 py-1.5 rounded-md bg-rose-50 hover:bg-rose-100 text-rose-700 border border-rose-200 text-xs font-semibold cursor-pointer"
                              >
                                Reject
                              </button>
                            </div>
                          );
                        })}
                    </div>
                  </div>
                )}

              {reviewingMessage && (
                <div className="p-3 rounded-lg bg-blue-50 border border-blue-200 text-xs font-medium text-[#1d63dc]">
                  {reviewingMessage}
                </div>
              )}
            </div>
          </div>

          {/* Right: Proposed vs Published Isolation & Version History (TASK-ADM-120..123) */}
          <div className="lg:col-span-5 space-y-4">
            <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs space-y-4">
              <div className="flex items-center justify-between">
                <h4 className="text-sm font-bold text-slate-900">
                  Version Isolation Details
                </h4>
                <span className="text-xs font-mono text-slate-500">
                  {activeRequest.requestId}
                </span>
              </div>

              <div className="space-y-3 text-xs">
                <div className="p-3 rounded-lg bg-slate-50 border border-slate-200">
                  <span className="font-bold text-slate-600 uppercase tracking-wider text-[10px]">
                    Active Published Version (v{activeRequest.baseVersion})
                  </span>
                  <p className="text-slate-700 mt-1 leading-relaxed">
                    {activeRequest.currentContent}
                  </p>
                </div>

                <div className="p-3 rounded-lg bg-blue-50/50 border border-blue-200">
                  <div className="flex items-center justify-between">
                    <span className="font-bold text-[#1d63dc] uppercase tracking-wider text-[10px]">
                      Proposed Version (v{activeRequest.proposedVersion})
                    </span>
                    {activeRequest.aiDraftUsed && (
                      <span className="text-[10px] font-semibold text-indigo-600 bg-indigo-50 px-1.5 py-0.5 rounded">
                        ✨ AI Assisted + Human Reviewed
                      </span>
                    )}
                  </div>
                  <p className="text-slate-900 font-medium mt-1 leading-relaxed">
                    {activeRequest.proposedContent}
                  </p>
                </div>
              </div>
            </div>

            {/* Immutable Version History */}
            <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs space-y-3">
              <div className="flex items-center gap-2">
                <History className="w-4 h-4 text-[#1d63dc]" />
                <h4 className="text-sm font-bold text-slate-900">
                  Published Version History
                </h4>
              </div>
              <div className="divide-y divide-slate-100 text-xs">
                {versions.slice(0, 4).map((v) => (
                  <div key={v.versionId} className="py-2.5 space-y-1">
                    <div className="flex items-center justify-between">
                      <span className="font-semibold text-slate-800">
                        {v.moduleName} • v{v.versionNumber}
                      </span>
                      <span className="text-slate-400 font-mono">{v.publishedAt}</span>
                    </div>
                    <p className="text-slate-600 line-clamp-2">{v.content}</p>
                    <p className="text-[11px] text-emerald-700 font-medium">
                      {v.approvalSummary}
                    </p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
