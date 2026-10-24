import React from 'react';
import {
  X,
  AlertTriangle,
  CheckCircle2,
  HelpCircle,
  RefreshCw,
  Box,
  ArrowUpRight,
  Award,
} from 'lucide-react';
import { ModuleData, Worker } from '../types';

interface AssessmentDrilldownModalProps {
  module: ModuleData | null;
  siteName: string;
  workers: Worker[];
  onClose: () => void;
  onSelectWorker: (worker: Worker) => void;
}

export const AssessmentDrilldownModal: React.FC<AssessmentDrilldownModalProps> = ({
  module,
  siteName,
  workers,
  onClose,
  onSelectWorker,
}) => {
  if (!module) return null;

  // Compute site-level worker breakdown for this module from live Firestore workers
  const workerRecords = workers.map((w) => {
    const modEntry = w.modules.find((m) => m.moduleId === module.moduleId);
    return {
      worker: w,
      progress: modEntry?.progressPercent ?? 0,
      status: modEntry?.status ?? 'Pending',
      achieved: modEntry?.achievedPercent ?? null,
    };
  });

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-xs p-4">
      <div className="bg-white rounded-xl shadow-2xl border border-slate-200 w-full max-w-3xl max-h-[90vh] flex flex-col overflow-hidden animate-in fade-in zoom-in-95 duration-150">
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-4 border-b border-slate-200 bg-slate-50">
          <div>
            <div className="flex items-center gap-2">
              <span className="text-xs font-mono font-semibold px-2 py-0.5 rounded bg-blue-100 text-[#1d63dc]">
                {module.moduleId} • v{module.currentVersion}
              </span>
              {module.arEnabled && (
                <span className="text-xs font-semibold px-2 py-0.5 rounded bg-purple-100 text-purple-700 flex items-center gap-1">
                  <Box className="w-3 h-3" />
                  AR Practical Enabled
                </span>
              )}
              <span className="text-xs text-slate-500">Site Context: {siteName}</span>
            </div>
            <h3 className="text-lg font-bold text-slate-900 mt-1">
              {module.name} — Site Assessment Analysis
            </h3>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-100"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Content */}
        <div className="p-6 overflow-y-auto space-y-5">
          {/* Top Metrics Row */}
          <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
            <div className="p-3.5 rounded-xl border border-slate-200 bg-slate-50/60">
              <p className="text-xs text-slate-500">Average Score</p>
              <p className="text-2xl font-bold text-slate-900 mt-0.5">{module.avgScore}%</p>
            </div>
            <div className="p-3.5 rounded-xl border border-slate-200 bg-slate-50/60">
              <p className="text-xs text-slate-500">Site Pass Rate</p>
              <p className="text-2xl font-bold text-emerald-600 mt-0.5">{module.passRate}%</p>
            </div>
            <div className="p-3.5 rounded-xl border border-slate-200 bg-slate-50/60">
              <p className="text-xs text-slate-500">Failed Attempts</p>
              <p className="text-2xl font-bold text-amber-600 mt-0.5">{module.failedAttempts}</p>
            </div>
            <div className="p-3.5 rounded-xl border border-slate-200 bg-slate-50/60">
              <p className="text-xs text-slate-500">Need Refresher</p>
              <p className="text-2xl font-bold text-rose-600 mt-0.5">
                {module.workersNeedingRefresher} Workers
              </p>
            </div>
          </div>

          {/* Most-Missed Question & Refresher Alert */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="p-4 rounded-xl border border-amber-200 bg-amber-50/60 flex items-start gap-3">
              <HelpCircle className="w-5 h-5 text-amber-600 shrink-0 mt-0.5" />
              <div>
                <p className="text-xs font-bold uppercase tracking-wider text-amber-800">
                  Most-Missed Assessment Question (of 14 MCQs)
                </p>
                <p className="text-sm font-semibold text-slate-900 mt-1">
                  {module.mostMissedQuestion}
                </p>
                <p className="text-xs text-slate-600 mt-1">
                  7 learning chapters × 2 situational questions per chapter.
                </p>
              </div>
            </div>

            <div className="p-4 rounded-xl border border-blue-200 bg-blue-50/50 flex items-start gap-3">
              <RefreshCw className="w-5 h-5 text-[#1d63dc] shrink-0 mt-0.5" />
              <div>
                <p className="text-xs font-bold uppercase tracking-wider text-[#1d63dc]">
                  AR & Practical Curriculum Scope
                </p>
                {module.arEnabled ? (
                  <ul className="mt-1 space-y-1 text-xs text-slate-700 list-disc list-inside">
                    {module.arScenarios.map((sc, i) => (
                      <li key={i}>{sc}</li>
                    ))}
                  </ul>
                ) : (
                  <p className="text-xs text-slate-700 mt-1">
                    7 visual/audio chapters (70 screens) + Chapter 8 formal 14-MCQ assessment in Hindi & Santali.
                  </p>
                )}
              </div>
            </div>
          </div>

          {/* Worker Performance Table for this Module */}
          <div>
            <div className="flex items-center justify-between mb-2.5">
              <h4 className="text-sm font-bold text-slate-800">
                Workforce Performance on {module.shortName} ({siteName})
              </h4>
              <span className="text-xs text-slate-500">
                Click any worker to inspect full 5-module profile
              </span>
            </div>
            <div className="border border-slate-200 rounded-xl overflow-hidden">
              <table className="w-full text-left border-collapse">
                <thead>
                  <tr className="bg-slate-50 border-b border-slate-200 text-[11px] font-semibold uppercase tracking-wider text-slate-500">
                    <th className="py-2.5 px-4">Worker</th>
                    <th className="py-2.5 px-3">ID</th>
                    <th className="py-2.5 px-3">Work Area</th>
                    <th className="py-2.5 px-3">Module Progress</th>
                    <th className="py-2.5 px-3">Status</th>
                    <th className="py-2.5 px-4 text-right">Achieved %</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-slate-100 text-xs">
                  {workerRecords.slice(0, 8).map(({ worker, progress, status, achieved }) => (
                    <tr
                      key={worker.workerId}
                      onClick={() => {
                        onClose();
                        onSelectWorker(worker);
                      }}
                      className="hover:bg-blue-50/40 cursor-pointer transition-colors"
                    >
                      <td className="py-2.5 px-4 font-semibold text-slate-900 flex items-center gap-1.5">
                        {worker.name}
                        <ArrowUpRight className="w-3.5 h-3.5 text-[#1d63dc]" />
                      </td>
                      <td className="py-2.5 px-3 font-mono text-slate-600">{worker.workerId}</td>
                      <td className="py-2.5 px-3 text-slate-600">{worker.workArea}</td>
                      <td className="py-2.5 px-3">
                        <div className="flex items-center gap-2">
                          <div className="w-20 h-2 bg-slate-100 rounded-full overflow-hidden">
                            <div
                              className={`h-full rounded-full ${
                                status === 'Completed'
                                  ? 'bg-emerald-500'
                                  : status === 'In Progress'
                                    ? 'bg-blue-500'
                                    : 'bg-slate-300'
                              }`}
                              style={{ width: `${progress}%` }}
                            />
                          </div>
                          <span className="font-medium text-slate-700">{progress}%</span>
                        </div>
                      </td>
                      <td className="py-2.5 px-3">
                        <span
                          className={`inline-flex items-center px-2 py-0.5 rounded text-[11px] font-semibold ${
                            status === 'Completed'
                              ? 'bg-emerald-100 text-emerald-700'
                              : status === 'In Progress'
                                ? 'bg-amber-100 text-amber-700'
                                : 'bg-rose-100 text-rose-700'
                          }`}
                        >
                          {status}
                        </span>
                      </td>
                      <td className="py-2.5 px-4 text-right font-bold text-slate-800">
                        {status === 'Completed' && achieved !== null ? `${achieved}%` : '—'}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="px-6 py-3.5 border-t border-slate-200 bg-slate-50 flex justify-end">
          <button
            onClick={onClose}
            className="px-4 py-2 rounded-lg bg-slate-900 text-white text-xs font-semibold hover:bg-slate-800"
          >
            Close Analysis
          </button>
        </div>
      </div>
    </div>
  );
};
