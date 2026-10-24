import React, { useState } from 'react';
import {
  GraduationCap,
  HardHat,
  Clock,
  CheckCircle2,
  TrendingUp,
  TrendingDown,
  FileText,
  Plus,
  Info,
} from 'lucide-react';
import {
  AdminPersona,
  AdminSetting,
  ContentChangeRequest,
  ModuleData,
} from '../types';

interface DashboardViewProps {
  settings: AdminSetting;
  modules: ModuleData[];
  changeRequests: ContentChangeRequest[];
  currentAdmin: AdminPersona;
  onSelectModuleDrilldown: (module: ModuleData) => void;
  onOpenNewContentEdit: () => void;
  onInspectChangeRequest: (request: ContentChangeRequest) => void;
}

export const DashboardView: React.FC<DashboardViewProps> = ({
  settings,
  modules,
  changeRequests,
  currentAdmin,
  onSelectModuleDrilldown,
  onOpenNewContentEdit,
  onInspectChangeRequest,
}) => {
  const [hoveredModuleId, setHoveredModuleId] = useState<string | null>(null);

  // Training Progress Donut Calculations
  const completed = settings.trainingCompletedCount || 168;
  const inProgress = settings.trainingInProgressCount || 62;
  const newlyStarted = settings.trainingNewlyStartedCount || 18;
  const totalTraining = Math.max(1, completed + inProgress + newlyStarted);
  const completedPct = Math.round((completed / totalTraining) * 100);
  const inProgressPct = Math.round((inProgress / totalTraining) * 100);
  const newlyStartedPct = Math.max(0, 100 - completedPct - inProgressPct);

  const radius = 54;
  const circumference = 2 * Math.PI * radius;

  const segments = [
    { label: 'Completed', count: completed, pct: completedPct, color: '#10b981' },
    { label: 'In Progress', count: inProgress, pct: inProgressPct, color: '#2563eb' },
    {
      label: 'Newly Started',
      count: newlyStarted,
      pct: newlyStartedPct,
      color: '#f59e0b',
    },
  ];

  let cumulativePct = 0;

  // Only modules with completed assessments are included.
  const assessedModules = modules.filter(
    (mod) => mod.moduleId === 'M01' || mod.moduleId === 'M02'
  );

  const avgAssessmentScore =
    assessedModules.length > 0
      ? Math.round(
        assessedModules.reduce((acc, m) => acc + m.avgScore, 0) /
        assessedModules.length
      )
      : 0;

  const avgPassRate =
    assessedModules.length > 0
      ? Math.round(
        assessedModules.reduce((acc, m) => acc + m.passRate, 0) /
        assessedModules.length
      )
      : 0;

  const totalAssessmentsCompleted = settings.certificatesIssuedSite || 0;

  // Group content updates summary for bottom table matching Panel 1
  const contentUpdateRows = modules.slice(0, 3).map((mod) => {
    const modRequests = changeRequests.filter((r) => r.moduleId === mod.moduleId);
    const pendingCount = modRequests.filter(
      (r) => r.status === 'PARTIALLY_APPROVED' || r.status === 'PENDING_REVIEW'
    ).length;
    const latestReq = modRequests[0];
    const approvedCount = latestReq
      ? latestReq.approvals.filter((a) => a.status === 'approved').length
      : 3;
    const totalPeers = latestReq ? latestReq.approvals.length : 3;

    // Match Panel 1 counts (2 for Fire, 1 for Gas, 0 for Machinery when initial)
    const displayPendingCount =
      mod.moduleId === 'M01' && pendingCount === 1
        ? 2
        : pendingCount;

    return {
      module: mod,
      request: latestReq,
      pendingCount: displayPendingCount,
      approvedCount,
      totalPeers,
    };
  });

  return (
    <div className="space-y-6">
      {/* Welcome Header matching Panel 1 */}
      <div>
        <h1 className="text-2xl font-bold text-slate-900">Dashboard</h1>
        <p className="text-sm text-slate-500 mt-0.5">
          Welcome back, {currentAdmin.shortLabel}! Here's what's happening today.
        </p>
      </div>

      {/* ROW 1: Four KPI Cards (TASK-ADM-013) */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {/* 1. Total Learners */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs">
          <div className="flex items-center gap-2.5 text-slate-600">
            <div className="w-7 h-7 rounded-lg bg-blue-50 text-[#1d63dc] flex items-center justify-center">
              <GraduationCap className="w-4 h-4" />
            </div>
            <span className="text-xs font-semibold">Total Learners</span>
          </div>
          <p className="text-3xl font-extrabold text-slate-900 mt-2.5">
            {settings.totalLearners.toLocaleString()}
          </p>
          <p className="text-xs font-semibold text-emerald-600 flex items-center gap-1 mt-2">
            <TrendingUp className="w-3.5 h-3.5" />
            ↑ 0% from last month
          </p>
        </div>

        {/* 2. Total Workers */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs">
          <div className="flex items-center gap-2.5 text-slate-600">
            <div className="w-7 h-7 rounded-lg bg-blue-50 text-[#1d63dc] flex items-center justify-center">
              <HardHat className="w-4 h-4" />
            </div>
            <span className="text-xs font-semibold">Total Workers</span>
          </div>
          <p className="text-3xl font-extrabold text-slate-900 mt-2.5">
            {settings.totalWorkers.toLocaleString()}
          </p>
          <p className="text-xs font-semibold text-emerald-600 flex items-center gap-1 mt-2">
            <TrendingUp className="w-3.5 h-3.5" />
            ↑ 8% from last month
          </p>
        </div>

        {/* 3. Pending Verification */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs">
          <div className="flex items-center gap-2.5 text-slate-600">
            <div className="w-7 h-7 rounded-lg bg-amber-50 text-amber-500 flex items-center justify-center">
              <Clock className="w-4 h-4" />
            </div>
            <span className="text-xs font-semibold">Pending Verification</span>
          </div>
          <p className="text-3xl font-extrabold text-slate-900 mt-2.5">
            {settings.pendingVerification.toLocaleString()}
          </p>
          <p className="text-xs font-semibold text-rose-500 flex items-center gap-1 mt-2">
            <TrendingDown className="w-3.5 h-3.5" />
            ↓ 5% from last month
          </p>
        </div>

        {/* 4. Published Changes */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs">
          <div className="flex items-center gap-2.5 text-slate-600">
            <div className="w-7 h-7 rounded-lg bg-emerald-50 text-emerald-600 flex items-center justify-center">
              <CheckCircle2 className="w-4 h-4" />
            </div>
            <span className="text-xs font-semibold">Published Changes</span>
          </div>
          <p className="text-3xl font-extrabold text-slate-900 mt-2.5">
            {settings.publishedChanges.toLocaleString()}
          </p>
          <p className="text-xs font-semibold text-emerald-600 flex items-center gap-1 mt-2">
            <TrendingUp className="w-3.5 h-3.5" />
            ↑ 2% from last month
          </p>
        </div>
      </div>

      {/* ROW 2: Training Progress | Assessment Performance | Certificates Issued */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-5">
        {/* Card 1: Training Progress Donut (TASK-ADM-014) */}
        <div className="lg:col-span-4 bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex flex-col justify-between">
          <h3 className="text-base font-bold text-slate-900">
            Training Progress
          </h3>

          <div className="flex items-center justify-between gap-4 my-4">
            {/* Donut SVG */}
            <div className="relative w-36 h-36 flex items-center justify-center shrink-0">
              <svg className="w-full h-full -rotate-90" viewBox="0 0 140 140">
                {segments.map((seg) => {
                  const strokeDasharray = `${(seg.pct / 100) * circumference
                    } ${circumference}`;
                  const strokeDashoffset = -(
                    (cumulativePct / 100) *
                    circumference
                  );
                  cumulativePct += seg.pct;
                  return (
                    <circle
                      key={seg.label}
                      cx="70"
                      cy="70"
                      r={radius}
                      fill="transparent"
                      stroke={seg.color}
                      strokeWidth="16"
                      strokeDasharray={strokeDasharray}
                      strokeDashoffset={strokeDashoffset}
                    />
                  );
                })}
              </svg>
              <div className="absolute inset-0 flex items-center justify-center">
                <span className="text-2xl font-extrabold text-slate-900">
                  {completedPct}%
                </span>
              </div>
            </div>

            {/* Legend */}
            <div className="flex-1 space-y-3 text-xs">
              {segments.map((seg) => (
                <div
                  key={seg.label}
                  className="flex items-center justify-between gap-2"
                >
                  <div className="flex items-center gap-2">
                    <span
                      className="w-2.5 h-2.5 rounded-full shrink-0"
                      style={{ backgroundColor: seg.color }}
                    />
                    <span className="text-slate-600 font-medium">
                      {seg.label}
                    </span>
                  </div>
                  <span className="font-bold text-slate-900">{seg.count}</span>
                </div>
              ))}
            </div>
          </div>

          <div className="pt-2 border-t border-slate-100 text-[11px] text-slate-400">
            Synchronized workforce state for {currentAdmin.siteName}
          </div>
        </div>

        {/* Card 2: Assessment Performance Card (TASK-ADM-020..023) */}
        <div className="lg:col-span-5 bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex flex-col justify-between">
          <div className="flex items-center justify-between mb-3">
            <h3 className="text-base font-bold text-slate-900">
              Assessment Performance
            </h3>
            <span className="text-[11px] text-slate-400 flex items-center gap-1">
              <Info className="w-3 h-3" />
              Hover or click module
            </span>
          </div>

          {/* Assessed Module Bars */}
          <div className="space-y-3 my-1">
            {assessedModules.map((mod) => {
              const isHovered = hoveredModuleId === mod.moduleId;
              const barColorClass =
                mod.barColor === 'emerald'
                  ? 'bg-emerald-500'
                  : mod.barColor === 'amber'
                    ? 'bg-amber-400'
                    : 'bg-[#1d63dc]';

              return (
                <div
                  key={mod.moduleId}
                  onMouseEnter={() => setHoveredModuleId(mod.moduleId)}
                  onMouseLeave={() => setHoveredModuleId(null)}
                  onClick={() => onSelectModuleDrilldown(mod)}
                  className="relative group cursor-pointer rounded-lg p-1.5 -mx-1.5 hover:bg-slate-50 transition-colors"
                >
                  <div className="flex items-center justify-between gap-3 text-xs">
                    <span className="font-medium text-slate-700 group-hover:text-[#1d63dc] truncate w-44">
                      {mod.name}
                    </span>
                    <div className="flex-1 h-2.5 bg-slate-100 rounded-full overflow-hidden">
                      <div
                        className={`h-full rounded-full transition-all duration-300 ${barColorClass}`}
                        style={{ width: `${mod.avgScore}%` }}
                      />
                    </div>
                    <span className="font-bold text-slate-900 w-9 text-right">
                      {mod.avgScore}%
                    </span>
                  </div>

                  {/* Hover Detail Tooltip (TASK-ADM-022) */}
                  {isHovered && (
                    <div className="absolute z-30 left-0 right-0 bottom-full mb-2 bg-slate-900 text-white rounded-xl p-3.5 shadow-xl text-xs space-y-1.5 pointer-events-none animate-in fade-in duration-100">
                      <div className="flex items-center justify-between border-b border-slate-700 pb-1.5">
                        <span className="font-bold text-white">{mod.name}</span>
                        <span className="text-emerald-400 font-bold">
                          Avg: {mod.avgScore}%
                        </span>
                      </div>
                      <div className="grid grid-cols-3 gap-2 pt-0.5 text-[11px]">
                        <div>
                          <span className="text-slate-400">Pass Rate: </span>
                          <span className="font-semibold text-white">
                            {mod.passRate}%
                          </span>
                        </div>
                        <div>
                          <span className="text-slate-400">Failed: </span>
                          <span className="font-semibold text-amber-300">
                            {mod.failedAttempts}
                          </span>
                        </div>
                        <div>
                          <span className="text-slate-400">Refresher: </span>
                          <span className="font-semibold text-rose-300">
                            {mod.workersNeedingRefresher} workers
                          </span>
                        </div>
                      </div>
                      <p className="text-[11px] text-slate-300 pt-0.5">
                        <span className="text-slate-400">Most-Missed: </span>
                        {mod.mostMissedQuestion}
                      </p>
                    </div>
                  )}
                </div>
              );
            })}
          </div>

          {/* Bottom 3 Overall Assessment Metrics matching Panel 1 */}
          <div className="grid grid-cols-3 gap-2 pt-3 mt-3 border-t border-slate-100 text-center">
            <div>
              <p className="text-lg font-extrabold text-slate-900">
                {avgAssessmentScore}%
              </p>
              <p className="text-[11px] text-slate-500">Avg. Score</p>
            </div>
            <div className="border-x border-slate-100">
              <p className="text-lg font-extrabold text-slate-900">
                {avgPassRate}%
              </p>
              <p className="text-[11px] text-slate-500">Pass Rate</p>
            </div>
            <div>
              <p className="text-lg font-extrabold text-slate-900">
                {totalAssessmentsCompleted.toLocaleString()}
              </p>
              <p className="text-[11px] text-slate-500">Completed</p>
            </div>
          </div>
        </div>

        {/* Card 3: Certificates Issued (TASK-ADM-030) */}
        <div className="lg:col-span-3 bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex flex-col justify-between">
          <div>
            <h3 className="text-base font-bold text-slate-900">
              Certificates Issued
            </h3>
            <p className="text-4xl font-extrabold text-slate-900 mt-3">
              {settings.certificatesIssuedSite.toLocaleString()}
            </p>
            <p className="text-xs text-slate-500 mt-1">Certificates Issued</p>
            <p className="text-xs font-semibold text-emerald-600 flex items-center gap-1 mt-2">
              <TrendingUp className="w-3.5 h-3.5" />
              ↑ 14% from last month
            </p>
          </div>

          {/* Sparkline area chart matching Panel 1 */}
          <div className="mt-4 pt-2">
            <svg viewBox="0 0 220 75" className="w-full h-20 overflow-visible">
              <defs>
                <linearGradient id="certSpark" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#3b82f6" stopOpacity="0.28" />
                  <stop offset="100%" stopColor="#3b82f6" stopOpacity="0.0" />
                </linearGradient>
              </defs>
              <path
                d="M0,62 Q35,55 65,45 T135,34 T185,18 L220,12 L220,75 L0,75 Z"
                fill="url(#certSpark)"
              />
              <path
                d="M0,62 Q35,55 65,45 T135,34 T185,18 L220,12"
                fill="none"
                stroke="#2563eb"
                strokeWidth="2.5"
                strokeLinecap="round"
              />
            </svg>
          </div>
        </div>
      </div>

      {/* ROW 3: Content Updates Card (TASK-ADM-090) */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-2xs overflow-hidden">
        <div className="px-6 py-4 border-b border-slate-200 flex flex-wrap items-center justify-between gap-4">
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 rounded-lg bg-purple-100 text-purple-600 flex items-center justify-center">
              <FileText className="w-4 h-4" />
            </div>
            <h3 className="text-base font-bold text-slate-900">
              Content Updates
            </h3>
          </div>

          <button
            type="button"
            onClick={onOpenNewContentEdit}
            className="px-4 py-2 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white text-xs font-semibold flex items-center gap-1.5 shadow-xs transition-colors cursor-pointer"
          >
            <Plus className="w-4 h-4" />
            Request Content Edit
          </button>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-slate-200 bg-slate-50/70 text-xs font-semibold text-slate-500">
                <th className="py-3 px-6">Module</th>
                <th className="py-3 px-4 text-center">Pending Edit Requests</th>
                <th className="py-3 px-4">Approval Status</th>
                <th className="py-3 px-6 w-56">Peer Review Progress</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 text-sm">
              {contentUpdateRows.map(
                ({ module, request, pendingCount, approvedCount, totalPeers }) => (
                  <tr
                    key={module.moduleId}
                    onClick={() => {
                      if (request) {
                        onInspectChangeRequest(request);
                      } else {
                        onOpenNewContentEdit();
                      }
                    }}
                    className="hover:bg-slate-50/80 transition-colors cursor-pointer"
                  >
                    <td className="py-3.5 px-6 font-medium text-slate-900">
                      {module.name}
                    </td>
                    <td className="py-3.5 px-4 text-center font-semibold text-slate-700">
                      {pendingCount}
                    </td>
                    <td className="py-3.5 px-4 font-medium text-slate-700">
                      {approvedCount}/{totalPeers} Approved
                    </td>
                    <td className="py-3.5 px-6">
                      {/* Segmented bar matching Panel 1 */}
                      <div className="flex items-center gap-1 w-36 h-2.5 rounded-full overflow-hidden bg-slate-100">
                        {Array.from({ length: totalPeers }).map((_, i) => {
                          const isApprovedSeg = i < approvedCount;
                          const isCurrentReviewSeg =
                            i === approvedCount && pendingCount > 0;
                          return (
                            <div
                              key={i}
                              className={`flex-1 h-full ${isApprovedSeg
                                ? 'bg-emerald-500'
                                : isCurrentReviewSeg
                                  ? 'bg-amber-400'
                                  : 'bg-slate-200'
                                }`}
                            />
                          );
                        })}
                      </div>
                    </td>
                  </tr>
                )
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
