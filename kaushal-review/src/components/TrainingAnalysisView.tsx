import React, { useState } from 'react';
import { Users, GraduationCap, CheckCircle2, Trophy } from 'lucide-react';
import { AdminSetting } from '../types';

interface TrainingAnalysisViewProps {
  settings: AdminSetting;
}

export const TrainingAnalysisView: React.FC<TrainingAnalysisViewProps> = ({
  settings,
}) => {
  const points = settings.monthlyParticipation || [];
  // Default highlighted point to March (index 2: Mar 2024 - 2,340 participants) matching Panel 8
  const [hoveredIdx, setHoveredIdx] = useState<number>(2);

  // SVG chart dimensions
  const chartWidth = 540;
  const chartHeight = 210;
  const padLeft = 42;
  const padRight = 20;
  const padTop = 25;
  const padBottom = 30;
  const innerW = chartWidth - padLeft - padRight;
  const innerH = chartHeight - padTop - padBottom;
  const maxVal = 4200;

  const coords = points.map((pt, i) => {
    const x =
      padLeft +
      (points.length > 1 ? (i / (points.length - 1)) * innerW : innerW / 2);
    const y = padTop + innerH - (pt.participants / maxVal) * innerH;
    return { x, y, ...pt };
  });

  const polylinePoints = coords.map((c) => `${c.x},${c.y}`).join(' ');
  const areaPoints =
    coords.length > 0
      ? `${coords[0].x},${padTop + innerH} ${polylinePoints} ${
          coords[coords.length - 1].x
        },${padTop + innerH}`
      : '';

  // Donut chart segments for Sector-wise Distribution (Mining 62%, Steel 25%, Mica 13%)
  const sectors = settings.sectorDistribution || [
    { sector: 'Mining', percentage: 62, participants: 7738, color: '#2563eb' },
    { sector: 'Steel', percentage: 25, participants: 3120, color: '#f59e0b' },
    { sector: 'Mica', percentage: 13, participants: 1622, color: '#10b981' },
  ];

  const radius = 64;
  const circumference = 2 * Math.PI * radius;
  let cumulativePercent = 0;

  return (
    <div className="space-y-6">
      {/* Page Title matching Panel 8 */}
      <div>
        <h2 className="text-2xl font-bold text-slate-900">Training Analysis</h2>
        <p className="text-sm text-slate-500">
          Overall platform statistics across all mines and industries.
        </p>
      </div>

      {/* 4 Top Metric Cards (TASK-ADM-060) */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {/* Total Participants */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-blue-50 text-[#1d63dc] flex items-center justify-center shrink-0">
            <Users className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs font-medium text-slate-500">
              Total Participants
            </p>
            <p className="text-2xl font-extrabold text-slate-900 mt-0.5">
              {settings.platformTotalParticipants.toLocaleString()}
            </p>
          </div>
        </div>

        {/* Module Completion */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-purple-50 text-purple-600 flex items-center justify-center shrink-0">
            <GraduationCap className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs font-medium text-slate-500">
              Module Completion
            </p>
            <p className="text-2xl font-extrabold text-slate-900 mt-0.5">
              {settings.platformModuleCompletion}%
            </p>
          </div>
        </div>

        {/* Assessment Pass Rate */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-emerald-50 text-emerald-600 flex items-center justify-center shrink-0">
            <CheckCircle2 className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs font-medium text-slate-500">
              Assessment Pass Rate
            </p>
            <p className="text-2xl font-extrabold text-slate-900 mt-0.5">
              {settings.platformAssessmentPassRate}%
            </p>
          </div>
        </div>

        {/* Certificates Issued */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-2xs flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-amber-50 text-amber-500 flex items-center justify-center shrink-0">
            <Trophy className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs font-medium text-slate-500">
              Certificates Issued
            </p>
            <p className="text-2xl font-extrabold text-slate-900 mt-0.5">
              {settings.platformCertificatesIssued.toLocaleString()}
            </p>
          </div>
        </div>
      </div>

      {/* Main Visualizations Row (TASK-ADM-061 & 062) */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* Month-wise Participation Trend */}
        <div className="lg:col-span-7 bg-white rounded-xl border border-slate-200 p-6 shadow-2xs">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-base font-bold text-slate-900">
              Participation Trend
            </h3>
            <span className="text-xs text-slate-400">
              Month-wise active worker completions
            </span>
          </div>

          <div className="relative w-full overflow-x-auto">
            <svg
              viewBox={`0 0 ${chartWidth} ${chartHeight}`}
              className="w-full h-auto min-w-[440px]"
            >
              <defs>
                <linearGradient id="trendGrad" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#2563eb" stopOpacity="0.22" />
                  <stop offset="100%" stopColor="#2563eb" stopOpacity="0.0" />
                </linearGradient>
              </defs>

              {/* Horizontal grid lines */}
              {[0, 1000, 2000, 3000, 4000].map((val) => {
                const y = padTop + innerH - (val / maxVal) * innerH;
                return (
                  <g key={val}>
                    <line
                      x1={padLeft}
                      y1={y}
                      x2={chartWidth - padRight}
                      y2={y}
                      stroke="#f1f5f9"
                      strokeWidth="1"
                    />
                    <text
                      x={padLeft - 8}
                      y={y + 4}
                      textAnchor="end"
                      className="fill-slate-400 text-[10px]"
                    >
                      {val === 0 ? '0' : `${val / 1000}K`}
                    </text>
                  </g>
                );
              })}

              {/* Shaded area */}
              {areaPoints && (
                <polygon points={areaPoints} fill="url(#trendGrad)" />
              )}

              {/* Line */}
              {polylinePoints && (
                <polyline
                  fill="none"
                  stroke="#2563eb"
                  strokeWidth="2.5"
                  points={polylinePoints}
                />
              )}

              {/* Data points & X-axis labels */}
              {coords.map((pt, idx) => {
                const isSelected = idx === hoveredIdx;
                return (
                  <g
                    key={pt.month}
                    onMouseEnter={() => setHoveredIdx(idx)}
                    className="cursor-pointer"
                  >
                    {isSelected && (
                      <line
                        x1={pt.x}
                        y1={pt.y}
                        x2={pt.x}
                        y2={padTop + innerH}
                        stroke="#93c5fd"
                        strokeDasharray="3 3"
                        strokeWidth="1.5"
                      />
                    )}
                    <circle
                      cx={pt.x}
                      cy={pt.y}
                      r={isSelected ? 5.5 : 3.5}
                      fill={isSelected ? '#1d63dc' : '#ffffff'}
                      stroke="#1d63dc"
                      strokeWidth="2.5"
                    />
                    <text
                      x={pt.x}
                      y={chartHeight - 8}
                      textAnchor="middle"
                      className={`text-[10px] ${
                        isSelected
                          ? 'fill-slate-900 font-bold'
                          : 'fill-slate-400'
                      }`}
                    >
                      {pt.month}
                    </text>
                  </g>
                );
              })}

              {/* Tooltip box matching Panel 8 (e.g. Mar 2024: 2,340 participants) */}
              {coords[hoveredIdx] && (
                <g
                  transform={`translate(${Math.min(
                    Math.max(coords[hoveredIdx].x - 68, padLeft),
                    chartWidth - 150
                  )}, ${Math.max(coords[hoveredIdx].y - 54, 4)})`}
                >
                  <rect
                    width="136"
                    height="42"
                    rx="6"
                    fill="#ffffff"
                    stroke="#e2e8f0"
                    strokeWidth="1"
                  />
                  <text
                    x="10"
                    y="16"
                    className="fill-slate-500 text-[10px] font-medium"
                  >
                    {coords[hoveredIdx].fullMonth}
                  </text>
                  <text
                    x="10"
                    y="32"
                    className="fill-slate-900 text-[11px] font-bold"
                  >
                    {coords[hoveredIdx].participants.toLocaleString()}{' '}
                    participants
                  </text>
                </g>
              )}
            </svg>
          </div>
        </div>

        {/* Sector-wise Distribution (Mining, Steel, Mica) */}
        <div className="lg:col-span-5 bg-white rounded-xl border border-slate-200 p-6 shadow-2xs flex flex-col justify-between">
          <div>
            <h3 className="text-base font-bold text-slate-900">
              Sector-wise Distribution
            </h3>
            <p className="text-xs text-slate-400 mt-0.5">
              Platform deployment across target industrial sectors
            </p>
          </div>

          <div className="flex flex-col sm:flex-row items-center justify-around gap-6 my-4">
            {/* SVG Donut Chart */}
            <div className="relative w-44 h-44 flex items-center justify-center">
              <svg className="w-full h-full -rotate-90" viewBox="0 0 160 160">
                {sectors.map((s) => {
                  const strokeDasharray = `${
                    (s.percentage / 100) * circumference
                  } ${circumference}`;
                  const strokeDashoffset = -(
                    (cumulativePercent / 100) *
                    circumference
                  );
                  cumulativePercent += s.percentage;
                  return (
                    <circle
                      key={s.sector}
                      cx="80"
                      cy="80"
                      r={radius}
                      fill="transparent"
                      stroke={s.color}
                      strokeWidth="20"
                      strokeDasharray={strokeDasharray}
                      strokeDashoffset={strokeDashoffset}
                    />
                  );
                })}
              </svg>
              <div className="absolute inset-0 flex flex-col items-center justify-center text-center">
                <span className="text-xl font-extrabold text-slate-900">
                  {settings.platformTotalParticipants.toLocaleString()}
                </span>
                <span className="text-xs text-slate-500">Participants</span>
              </div>
            </div>

            {/* Sector Legend matching Panel 8 */}
            <div className="space-y-3.5 min-w-[140px]">
              {sectors.map((s) => (
                <div
                  key={s.sector}
                  className="flex items-center justify-between gap-4 text-sm"
                >
                  <div className="flex items-center gap-2.5">
                    <span
                      className="w-3 h-3 rounded-full shrink-0"
                      style={{ backgroundColor: s.color }}
                    />
                    <span className="font-medium text-slate-700">
                      {s.sector}
                    </span>
                  </div>
                  <span className="font-bold text-slate-900">
                    {s.percentage}%
                  </span>
                </div>
              ))}
            </div>
          </div>

          <div className="pt-3 border-t border-slate-100 flex items-center justify-between text-xs text-slate-500">
            <span>Languages: Hindi & Santali</span>
            <span>Offline-Synchronized Telemetry</span>
          </div>
        </div>
      </div>
    </div>
  );
};
