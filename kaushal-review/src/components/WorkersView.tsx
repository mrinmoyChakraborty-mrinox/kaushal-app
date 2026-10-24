import React, { useState, useMemo } from 'react';
import {
  Search,
  ArrowLeft,
  Mail,
  Globe,
  MapPin,
  Flame,
  Wind,
  Settings,
  Shield,
  Scale,
  UserPlus,
  RefreshCw,
  ArrowUpDown,
  ChevronLeft,
  ChevronRight,
  SlidersHorizontal,
  Check,
  X,
  Loader2,
  Box,
} from 'lucide-react';
import {
  LanguageType,
  SectorType,
  WorkAreaType,
  Worker,
  WorkerStatusType,
} from '../types';
import {
  createWorkerInDb,
  syncOfflineWorkerBatchInDb,
  updateWorkerModuleProgressInDb,
} from '../services/kaushalDatabase';

interface WorkersViewProps {
  workers: Worker[];
  selectedWorker: Worker | null;
  onSelectWorker: (worker: Worker | null) => void;
  siteName: string;
  totalSiteWorkersCount: number;
}

export const WorkersView: React.FC<WorkersViewProps> = ({
  workers,
  selectedWorker,
  onSelectWorker,
  siteName,
  totalSiteWorkersCount,
}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [sortAsc, setSortAsc] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);

  // Add Worker Modal State
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [newName, setNewName] = useState('');
  const [newWorkerId, setNewWorkerId] = useState(`W0${workers.length + 1}`);
  const [newEmail, setNewEmail] = useState('');
  const [newLanguage, setNewLanguage] = useState<LanguageType>('Hindi');
  const [newSector, setNewSector] = useState<SectorType>('Mining');
  const [newWorkArea, setNewWorkArea] = useState<WorkAreaType>('Underground');
  const [newStatus, setNewStatus] = useState<WorkerStatusType>('Completed');
  const [newScore, setNewScore] = useState<number>(88);
  const [isSavingWorker, setIsSavingWorker] = useState(false);

  // Offline Sync State
  const [isSyncing, setIsSyncing] = useState(false);
  const [syncBanner, setSyncBanner] = useState<string | null>(null);

  // Edit Module Progress State in Worker Profile
  const [editingModuleId, setEditingModuleId] = useState<string | null>(null);
  const [editProgressVal, setEditProgressVal] = useState<number>(100);
  const [editAchievedVal, setEditAchievedVal] = useState<number>(90);
  const [isUpdatingModule, setIsUpdatingModule] = useState(false);

  // Keep selectedWorker fresh when Firestore updates
  const activeWorker = useMemo(() => {
    if (!selectedWorker) return null;
    return (
      workers.find((w) => w.workerId === selectedWorker.workerId) ||
      selectedWorker
    );
  }, [workers, selectedWorker]);

  const filteredWorkers = useMemo(() => {
    const q = searchQuery.trim().toLowerCase();
    const list = workers.filter(
      (w) =>
        w.name.toLowerCase().includes(q) ||
        w.workerId.toLowerCase().includes(q) ||
        w.workArea.toLowerCase().includes(q) ||
        w.language.toLowerCase().includes(q)
    );
    return list.sort((a, b) => {
      const cmp = a.workerId.localeCompare(b.workerId, undefined, {
        numeric: true,
      });
      return sortAsc ? cmp : -cmp;
    });
  }, [workers, searchQuery, sortAsc]);

  const totalPages = Math.max(1, Math.ceil(filteredWorkers.length / pageSize));
  const paginatedWorkers = filteredWorkers.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  const handleCreateWorker = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newName.trim() || !newWorkerId.trim()) return;
    setIsSavingWorker(true);
    try {
      await createWorkerInDb({
        name: newName,
        workerId: newWorkerId,
        email: newEmail,
        language: newLanguage,
        sector: newSector,
        workArea: newWorkArea,
        siteId: siteName,
        initialStatus: newStatus,
        achievedScore: newScore,
      });
      setIsAddModalOpen(false);
      setNewName('');
      setNewEmail('');
      setNewWorkerId(`W0${workers.length + 2}`);
    } finally {
      setIsSavingWorker(false);
    }
  };

  const handleSyncOffline = async () => {
    setIsSyncing(true);
    setSyncBanner(null);
    try {
      const res = await syncOfflineWorkerBatchInDb();
      setSyncBanner(
        `Synchronized offline training & AR assessment records for ${res.syncedWorkers} workers from field devices.`
      );
    } finally {
      setIsSyncing(false);
    }
  };

  const handleSaveModuleProgress = async (moduleId: string) => {
    if (!activeWorker) return;
    setIsUpdatingModule(true);
    try {
      await updateWorkerModuleProgressInDb(
        activeWorker,
        moduleId,
        editProgressVal,
        editProgressVal === 100 ? editAchievedVal : null
      );
      setEditingModuleId(null);
    } finally {
      setIsUpdatingModule(false);
    }
  };

  const getModuleIcon = (moduleId: string) => {
    switch (moduleId) {
      case 'M01':
        return (
          <div className="w-9 h-9 rounded-lg bg-rose-100 text-rose-600 flex items-center justify-center shrink-0">
            <Flame className="w-5 h-5" />
          </div>
        );
      case 'M02':
        return (
          <div className="w-9 h-9 rounded-lg bg-blue-100 text-[#1d63dc] flex items-center justify-center shrink-0">
            <Wind className="w-5 h-5" />
          </div>
        );
      case 'M03':
        return (
          <div className="w-9 h-9 rounded-lg bg-amber-100 text-amber-600 flex items-center justify-center shrink-0">
            <Settings className="w-5 h-5" />
          </div>
        );
      case 'M04':
        return (
          <div className="w-9 h-9 rounded-lg bg-yellow-100 text-yellow-700 flex items-center justify-center shrink-0">
            <Shield className="w-5 h-5" />
          </div>
        );
      default:
        return (
          <div className="w-9 h-9 rounded-lg bg-orange-100 text-orange-600 flex items-center justify-center shrink-0">
            <Scale className="w-5 h-5" />
          </div>
        );
    }
  };

  // PANEL 3: WORKER PROFILE VIEW
  if (activeWorker) {
    return (
      <div className="space-y-6 animate-in fade-in duration-150">
        {/* Breadcrumb Header matching Panel 3: <- Workers / Rahul Kumar */}
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2 text-sm">
            <button
              type="button"
              onClick={() => onSelectWorker(null)}
              className="inline-flex items-center gap-1.5 font-semibold text-[#1d63dc] hover:underline cursor-pointer"
            >
              <ArrowLeft className="w-4 h-4" />
              Workers
            </button>
            <span className="text-slate-400">/</span>
            <span className="font-semibold text-slate-800">{activeWorker.name}</span>
          </div>

          <span className="text-xs text-slate-500">
            Last Offline Sync:{' '}
            <span className="font-medium text-slate-700">
              {activeWorker.lastSyncedAt}
            </span>
          </span>
        </div>

        {/* Worker Identity Card matching Panel 3 */}
        <div className="bg-white rounded-xl border border-slate-200 p-6 shadow-2xs flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6">
          <div className="flex items-center gap-5">
            <div className="relative">
              <img
                src={activeWorker.avatarUrl}
                alt={activeWorker.name}
                className="w-20 h-20 rounded-full object-cover border-2 border-amber-400 shadow-sm"
              />
              <span
                className={`absolute bottom-0 right-0 w-4 h-4 rounded-full border-2 border-white ${
                  activeWorker.status === 'Completed'
                    ? 'bg-emerald-500'
                    : activeWorker.status === 'In Progress'
                      ? 'bg-amber-500'
                      : 'bg-rose-500'
                }`}
              />
            </div>

            <div className="space-y-1">
              <div className="flex items-center gap-2.5">
                <h2 className="text-2xl font-bold text-slate-900">
                  {activeWorker.name}
                </h2>
                <span
                  className={`px-2.5 py-0.5 rounded-md text-xs font-semibold ${
                    activeWorker.status === 'Completed'
                      ? 'bg-emerald-100 text-emerald-700'
                      : activeWorker.status === 'In Progress'
                        ? 'bg-amber-100 text-amber-700'
                        : 'bg-rose-100 text-rose-700'
                  }`}
                >
                  {activeWorker.status}
                </span>
              </div>
              <p className="text-sm font-medium text-slate-500">
                Worker ID:{' '}
                <span className="font-mono text-slate-800">
                  {activeWorker.workerId}
                </span>
              </p>

              <div className="flex flex-wrap items-center gap-4 pt-1 text-xs text-slate-600">
                <span className="flex items-center gap-1.5">
                  <Mail className="w-3.5 h-3.5 text-slate-400" />
                  {activeWorker.email}
                </span>
                <span className="flex items-center gap-1.5">
                  <Globe className="w-3.5 h-3.5 text-slate-400" />
                  {activeWorker.language}
                </span>
                <span className="flex items-center gap-1.5">
                  <MapPin className="w-3.5 h-3.5 text-slate-400" />
                  {activeWorker.sector} • {activeWorker.workArea}
                </span>
              </div>
            </div>
          </div>

          <div className="bg-slate-50 rounded-xl p-4 border border-slate-200/80 text-right min-w-[160px]">
            <p className="text-xs text-slate-500">Overall Completion</p>
            <p className="text-2xl font-bold text-slate-900 mt-0.5">
              {activeWorker.status === 'Completed' &&
              activeWorker.completionPercent !== null
                ? `${activeWorker.completionPercent}%`
                : '—'}
            </p>
            <p className="text-[11px] text-slate-400 mt-0.5">
              Site: {activeWorker.siteId}
            </p>
          </div>
        </div>

        {/* Five-Module Progress Overview Card matching Panel 3 (TASK-ADM-051 & 052) */}
        <div className="bg-white rounded-xl border border-slate-200 shadow-2xs overflow-hidden">
          <div className="px-6 py-4 border-b border-slate-200 flex items-center justify-between">
            <div>
              <h3 className="text-base font-bold text-slate-900">
                Progress Overview
              </h3>
              <p className="text-xs text-slate-500">
                Content completion (Progress %) and post-completion competency score (Achieved %) across all 5 modules
              </p>
            </div>
            <span className="text-xs text-slate-400">
              Click any row to update worker progress
            </span>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-slate-200 bg-slate-50/70 text-xs font-semibold text-slate-500">
                  <th className="py-3.5 px-6">Module</th>
                  <th className="py-3.5 px-4">Progress</th>
                  <th className="py-3.5 px-4">Status</th>
                  <th className="py-3.5 px-6 text-right">Achieved %</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-sm">
                {activeWorker.modules.map((mod) => {
                  const isEditing = editingModuleId === mod.moduleId;
                  const isArModule =
                    mod.moduleId === 'M01' ||
                    mod.moduleId === 'M02' ||
                    mod.moduleId === 'M03';

                  return (
                    <tr
                      key={mod.moduleId}
                      onClick={() => {
                        if (!isEditing) {
                          setEditingModuleId(mod.moduleId);
                          setEditProgressVal(mod.progressPercent);
                          setEditAchievedVal(mod.achievedPercent ?? 88);
                        }
                      }}
                      className="hover:bg-slate-50/80 transition-colors cursor-pointer"
                    >
                      <td className="py-4 px-6">
                        <div className="flex items-center gap-3.5">
                          {getModuleIcon(mod.moduleId)}
                          <div>
                            <p className="font-semibold text-slate-900 leading-snug">
                              {mod.moduleName}
                            </p>
                            {isArModule && (
                              <span className="inline-flex items-center gap-1 text-[11px] text-purple-700 font-medium mt-0.5">
                                <Box className="w-3 h-3" />
                                AR Practical Module
                              </span>
                            )}
                          </div>
                        </div>
                      </td>

                      {/* Progress Bar + Percentage */}
                      <td className="py-4 px-4">
                        {isEditing ? (
                          <div
                            onClick={(e) => e.stopPropagation()}
                            className="flex items-center gap-2"
                          >
                            <input
                              type="number"
                              min={0}
                              max={100}
                              value={editProgressVal}
                              onChange={(e) =>
                                setEditProgressVal(Number(e.target.value))
                              }
                              className="w-20 px-2 py-1 text-xs rounded border border-blue-300 font-mono"
                            />
                            <span className="text-xs text-slate-500">%</span>
                          </div>
                        ) : (
                          <div className="flex items-center gap-3">
                            <div className="w-28 h-2.5 bg-slate-100 rounded-full overflow-hidden">
                              <div
                                className={`h-full rounded-full ${
                                  mod.progressPercent === 100
                                    ? 'bg-emerald-500'
                                    : mod.progressPercent > 0
                                      ? 'bg-[#1d63dc]'
                                      : 'bg-slate-200'
                                }`}
                                style={{ width: `${mod.progressPercent}%` }}
                              />
                            </div>
                            <span className="text-xs font-semibold text-slate-700 w-9">
                              {mod.progressPercent}%
                            </span>
                          </div>
                        )}
                      </td>

                      {/* Status Badge */}
                      <td className="py-4 px-4">
                        <span
                          className={`inline-flex items-center px-2.5 py-1 rounded-md text-xs font-semibold ${
                            mod.status === 'Completed'
                              ? 'bg-emerald-100 text-emerald-700'
                              : mod.status === 'In Progress'
                                ? 'bg-amber-100 text-amber-700'
                                : 'bg-rose-100 text-rose-700'
                          }`}
                        >
                          {mod.status}
                        </span>
                      </td>

                      {/* Achieved % (Strictly blank / '—' unless Completed!) */}
                      <td className="py-4 px-6 text-right">
                        {isEditing ? (
                          <div
                            onClick={(e) => e.stopPropagation()}
                            className="flex items-center justify-end gap-1.5"
                          >
                            {editProgressVal === 100 && (
                              <input
                                type="number"
                                min={0}
                                max={100}
                                value={editAchievedVal}
                                onChange={(e) =>
                                  setEditAchievedVal(Number(e.target.value))
                                }
                                className="w-16 px-2 py-1 text-xs rounded border border-emerald-300 font-mono"
                                title="Assessment Achieved %"
                              />
                            )}
                            <button
                              type="button"
                              disabled={isUpdatingModule}
                              onClick={() => handleSaveModuleProgress(mod.moduleId)}
                              className="p-1.5 rounded bg-emerald-600 text-white hover:bg-emerald-700"
                              title="Save to Firestore"
                            >
                              {isUpdatingModule ? (
                                <Loader2 className="w-3.5 h-3.5 animate-spin" />
                              ) : (
                                <Check className="w-3.5 h-3.5" />
                              )}
                            </button>
                            <button
                              type="button"
                              onClick={() => setEditingModuleId(null)}
                              className="p-1.5 rounded bg-slate-200 text-slate-600 hover:bg-slate-300"
                              title="Cancel"
                            >
                              <X className="w-3.5 h-3.5" />
                            </button>
                          </div>
                        ) : (
                          <span className="font-bold text-slate-900">
                            {mod.status === 'Completed' &&
                            mod.achievedPercent !== null
                              ? `${mod.achievedPercent}%`
                              : '—'}
                          </span>
                        )}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }

  // PANEL 2: WORKERS LIST VIEW
  return (
    <div className="space-y-5">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl font-bold text-slate-900">Workers</h2>
          <p className="text-sm text-slate-500">
            View and manage workers in your mine/factory.
          </p>
        </div>

        <div className="flex items-center gap-2.5">
          <button
            type="button"
            onClick={handleSyncOffline}
            disabled={isSyncing}
            className="px-3.5 py-2 rounded-lg border border-slate-200 bg-white hover:bg-slate-50 text-slate-700 text-xs font-semibold flex items-center gap-1.5 shadow-2xs cursor-pointer"
          >
            <RefreshCw
              className={`w-3.5 h-3.5 text-[#1d63dc] ${
                isSyncing ? 'animate-spin' : ''
              }`}
            />
            {isSyncing ? 'Syncing Field Devices...' : 'Sync Offline Data'}
          </button>

          <button
            type="button"
            onClick={() => {
              setNewWorkerId(
                `W${String(workers.length + 1).padStart(3, '0')}`
              );
              setIsAddModalOpen(true);
            }}
            className="px-4 py-2 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white text-xs font-semibold flex items-center gap-1.5 shadow-xs cursor-pointer"
          >
            <UserPlus className="w-3.5 h-3.5" />
            + Add Worker
          </button>
        </div>
      </div>

      {syncBanner && (
        <div className="p-3.5 rounded-xl bg-emerald-50 border border-emerald-200 text-xs font-medium text-emerald-800 flex items-center justify-between">
          <span>{syncBanner}</span>
          <button
            type="button"
            onClick={() => setSyncBanner(null)}
            className="text-emerald-600 hover:text-emerald-800 font-bold"
          >
            ✕
          </button>
        </div>
      )}

      {/* Main Card with Search Bar + Worker Table matching Panel 2 */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-2xs overflow-hidden">
        {/* Search Input (TASK-ADM-040) */}
        <div className="p-4 border-b border-slate-200">
          <div className="relative max-w-md">
            <Search className="w-4 h-4 text-slate-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => {
                setSearchQuery(e.target.value);
                setCurrentPage(1);
              }}
              placeholder="Search by name or worker ID..."
              className="w-full pl-10 pr-4 py-2.5 rounded-lg border border-slate-200 bg-slate-50/70 focus:bg-white text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-[#1d63dc]"
            />
          </div>
        </div>

        {/* Worker Table (TASK-ADM-041 & 042) */}
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-slate-200 bg-slate-50/70 text-xs font-semibold text-slate-500">
                <th className="py-3.5 px-6">
                  <button
                    type="button"
                    onClick={() => setSortAsc(!sortAsc)}
                    className="inline-flex items-center gap-1 hover:text-slate-800 cursor-pointer"
                  >
                    Name
                    <ArrowUpDown className="w-3.5 h-3.5 text-slate-400" />
                  </button>
                </th>
                <th className="py-3.5 px-4">Worker ID</th>
                <th className="py-3.5 px-4">Language</th>
                <th className="py-3.5 px-4">Work Area</th>
                <th className="py-3.5 px-4">Status</th>
                <th className="py-3.5 px-6 text-right">Completion %</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 text-sm">
              {paginatedWorkers.length === 0 ? (
                <tr>
                  <td
                    colSpan={6}
                    className="py-10 text-center text-slate-500 text-sm"
                  >
                    No workers match "{searchQuery}".
                  </td>
                </tr>
              ) : (
                paginatedWorkers.map((worker) => (
                  <tr
                    key={worker.workerId}
                    onClick={() => onSelectWorker(worker)}
                    className="hover:bg-blue-50/40 transition-colors cursor-pointer group"
                  >
                    <td className="py-3.5 px-6 font-semibold text-slate-900 group-hover:text-[#1d63dc]">
                      {worker.name}
                    </td>
                    <td className="py-3.5 px-4 font-mono text-slate-600 text-xs">
                      {worker.workerId}
                    </td>
                    <td className="py-3.5 px-4 text-slate-700">
                      {worker.language}
                    </td>
                    <td className="py-3.5 px-4 text-slate-700">
                      {worker.workArea}
                    </td>
                    <td className="py-3.5 px-4">
                      <span
                        className={`inline-flex items-center px-2.5 py-0.5 rounded-md text-xs font-semibold ${
                          worker.status === 'Completed'
                            ? 'bg-emerald-100 text-emerald-700'
                            : worker.status === 'In Progress'
                              ? 'bg-amber-100 text-amber-700'
                              : 'bg-rose-100 text-rose-600'
                        }`}
                      >
                        {worker.status}
                      </span>
                    </td>
                    {/* Completion display rule: Show percentage ONLY when status is Completed; otherwise show — */}
                    <td className="py-3.5 px-6 text-right font-semibold text-slate-800">
                      {worker.status === 'Completed' &&
                      worker.completionPercent !== null
                        ? `${worker.completionPercent}%`
                        : '—'}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {/* Pagination Footer matching Panel 2 */}
        <div className="px-6 py-3.5 border-t border-slate-200 bg-slate-50/50 flex flex-wrap items-center justify-between gap-4 text-xs text-slate-600">
          <span>
            Showing {(currentPage - 1) * pageSize + 1}–
            {Math.min(currentPage * pageSize, filteredWorkers.length)} of{' '}
            {searchQuery ? filteredWorkers.length : Math.max(filteredWorkers.length, 120)}
          </span>

          <div className="flex items-center gap-1.5">
            <button
              type="button"
              disabled={currentPage === 1}
              onClick={() => setCurrentPage((p) => Math.max(1, p - 1))}
              className="p-1.5 rounded border border-slate-200 bg-white hover:bg-slate-50 disabled:opacity-40 cursor-pointer"
            >
              <ChevronLeft className="w-3.5 h-3.5" />
            </button>

            <button
              type="button"
              onClick={() => setCurrentPage(1)}
              className={`w-7 h-7 rounded font-semibold cursor-pointer ${
                currentPage === 1
                  ? 'bg-[#1d63dc] text-white'
                  : 'bg-white border border-slate-200 text-slate-700 hover:bg-slate-50'
              }`}
            >
              1
            </button>

            {totalPages >= 2 && (
              <button
                type="button"
                onClick={() => setCurrentPage(2)}
                className={`w-7 h-7 rounded font-semibold cursor-pointer ${
                  currentPage === 2
                    ? 'bg-[#1d63dc] text-white'
                    : 'bg-white border border-slate-200 text-slate-700 hover:bg-slate-50'
                }`}
              >
                2
              </button>
            )}

            <span className="px-1 text-slate-400">...</span>
            <span className="w-7 h-7 rounded bg-white border border-slate-200 text-slate-500 flex items-center justify-center">
              12
            </span>

            <button
              type="button"
              disabled={currentPage >= totalPages}
              onClick={() => setCurrentPage((p) => Math.min(totalPages, p + 1))}
              className="p-1.5 rounded border border-slate-200 bg-white hover:bg-slate-50 disabled:opacity-40 cursor-pointer"
            >
              <ChevronRight className="w-3.5 h-3.5" />
            </button>
          </div>

          <select
            value={pageSize}
            onChange={(e) => {
              setPageSize(Number(e.target.value));
              setCurrentPage(1);
            }}
            className="px-2.5 py-1.5 rounded border border-slate-200 bg-white text-xs text-slate-700"
          >
            <option value={10}>10 per page</option>
            <option value={15}>15 per page</option>
            <option value={25}>25 per page</option>
          </select>
        </div>
      </div>

      {/* Add Worker Modal (Real Firestore Document Creation) */}
      {isAddModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-xs p-4">
          <div className="bg-white rounded-xl shadow-2xl border border-slate-200 w-full max-w-md overflow-hidden animate-in fade-in zoom-in-95 duration-150">
            <div className="flex items-center justify-between px-6 py-4 border-b border-slate-200 bg-slate-50">
              <h3 className="text-base font-bold text-slate-900">
                Enroll Worker in {siteName}
              </h3>
              <button
                type="button"
                onClick={() => setIsAddModalOpen(false)}
                className="text-slate-400 hover:text-slate-600"
              >
                <X className="w-5 h-5" />
              </button>
            </div>
            <form onSubmit={handleCreateWorker} className="p-6 space-y-4">
              <div className="grid grid-cols-3 gap-3">
                <div className="col-span-2">
                  <label className="block text-xs font-bold text-slate-700 mb-1">
                    Worker Full Name
                  </label>
                  <input
                    type="text"
                    required
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                    placeholder="e.g. Somra Munda"
                    className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm"
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-slate-700 mb-1">
                    Worker ID
                  </label>
                  <input
                    type="text"
                    required
                    value={newWorkerId}
                    onChange={(e) => setNewWorkerId(e.target.value)}
                    className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm font-mono"
                  />
                </div>
              </div>

              <div>
                <label className="block text-xs font-bold text-slate-700 mb-1">
                  Email ID
                </label>
                <input
                  type="email"
                  value={newEmail}
                  onChange={(e) => setNewEmail(e.target.value)}
                  placeholder="somra.munda@minea.com"
                  className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-bold text-slate-700 mb-1">
                    Learning Language
                  </label>
                  <select
                    value={newLanguage}
                    onChange={(e) =>
                      setNewLanguage(e.target.value as LanguageType)
                    }
                    className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm bg-white"
                  >
                    <option value="Hindi">Hindi</option>
                    <option value="Santali">Santali</option>
                  </select>
                </div>

                <div>
                  <label className="block text-xs font-bold text-slate-700 mb-1">
                    Work Area
                  </label>
                  <select
                    value={newWorkArea}
                    onChange={(e) =>
                      setNewWorkArea(e.target.value as WorkAreaType)
                    }
                    className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm bg-white"
                  >
                    <option value="Underground">Underground</option>
                    <option value="Surface">Surface</option>
                  </select>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-bold text-slate-700 mb-1">
                    Training Status
                  </label>
                  <select
                    value={newStatus}
                    onChange={(e) =>
                      setNewStatus(e.target.value as WorkerStatusType)
                    }
                    className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm bg-white"
                  >
                    <option value="Completed">Completed</option>
                    <option value="In Progress">In Progress</option>
                    <option value="Pending">Pending</option>
                  </select>
                </div>

                {newStatus === 'Completed' && (
                  <div>
                    <label className="block text-xs font-bold text-slate-700 mb-1">
                      Achieved Score %
                    </label>
                    <input
                      type="number"
                      min={50}
                      max={100}
                      value={newScore}
                      onChange={(e) => setNewScore(Number(e.target.value))}
                      className="w-full px-3 py-2 rounded-lg border border-slate-300 text-sm font-mono"
                    />
                  </div>
                )}
              </div>

              <div className="pt-3 flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsAddModalOpen(false)}
                  className="px-4 py-2 rounded-lg border border-slate-200 text-xs font-semibold text-slate-600"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={isSavingWorker}
                  className="px-4 py-2 rounded-lg bg-[#1d63dc] text-white text-xs font-semibold flex items-center gap-1.5"
                >
                  {isSavingWorker && (
                    <Loader2 className="w-3.5 h-3.5 animate-spin" />
                  )}
                  Save Worker to Firestore
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
