import React from 'react';

type SkeletonVariant =
  | 'dashboard'
  | 'workers'
  | 'training-analysis'
  | 'notifications'
  | 'audit-logs'
  | 'settings'
  | 'content-edit';

const Block = ({ className = '' }: { className?: string }) => (
  <div aria-hidden="true" className={`animate-pulse rounded-lg bg-slate-200/80 ${className}`} />
);

function PageHeading() {
  return (
    <div className="mb-7 space-y-3">
      <Block className="h-7 w-48" />
      <Block className="h-4 w-72 max-w-full" />
    </div>
  );
}

function StatCards({ count = 4 }: { count?: number }) {
  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4">
      {Array.from({ length: count }, (_, index) => (
        <div key={index} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
          <div className="flex items-center justify-between">
            <Block className="h-3 w-24" />
            <Block className="h-9 w-9 rounded-xl" />
          </div>
          <Block className="mt-5 h-8 w-20" />
          <Block className="mt-3 h-3 w-28" />
        </div>
      ))}
    </div>
  );
}

function TableSkeleton({ rows = 6, columns = 5 }: { rows?: number; columns?: number }) {
  return (
    <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
      <div className="flex items-center justify-between border-b border-slate-100 p-5">
        <Block className="h-5 w-36" />
        <Block className="h-9 w-32 rounded-lg" />
      </div>
      <div className="space-y-0 px-5">
        <div className="grid gap-4 border-b border-slate-100 py-4" style={{ gridTemplateColumns: `repeat(${columns}, minmax(0, 1fr))` }}>
          {Array.from({ length: columns }, (_, i) => <Block key={i} className="h-3 w-20 max-w-full" />)}
        </div>
        {Array.from({ length: rows }, (_, row) => (
          <div key={row} className="grid gap-4 border-b border-slate-50 py-5 last:border-0" style={{ gridTemplateColumns: `repeat(${columns}, minmax(0, 1fr))` }}>
            {Array.from({ length: columns }, (_, col) => <Block key={col} className={`h-4 max-w-full ${col === 0 ? 'w-28' : 'w-20'}`} />)}
          </div>
        ))}
      </div>
    </div>
  );
}

export const SkeletonLoader: React.FC<{ variant: SkeletonVariant }> = ({ variant }) => {
  if (variant === 'workers') {
    return <div role="status" aria-label="Loading workers" className="space-y-6"><PageHeading /><div className="flex flex-wrap gap-3"><Block className="h-10 w-64" /><Block className="h-10 w-28" /><Block className="h-10 w-28" /></div><TableSkeleton rows={7} columns={6}/><span className="sr-only">Loading workers…</span></div>;
  }

  if (variant === 'notifications' || variant === 'audit-logs') {
    return <div role="status" aria-label={variant === 'notifications' ? 'Loading notifications' : 'Loading audit logs'} className="space-y-6"><PageHeading /><div className="space-y-3 rounded-2xl border border-slate-200 bg-white p-5">{Array.from({ length: 5 }, (_, i) => <div key={i} className="flex items-center gap-4 border-b border-slate-100 py-4 last:border-0"><Block className="h-10 w-10 shrink-0 rounded-xl"/><div className="flex-1 space-y-2"><Block className="h-4 w-1/3"/><Block className="h-3 w-2/3"/></div><Block className="h-3 w-20"/></div>)}</div><span className="sr-only">Loading…</span></div>;
  }

  if (variant === 'training-analysis') {
    return <div role="status" aria-label="Loading training analysis" className="space-y-6"><PageHeading /><StatCards /><div className="grid grid-cols-1 gap-5 xl:grid-cols-2"><div className="h-80 rounded-2xl border border-slate-200 bg-white p-5"><Block className="mb-6 h-5 w-40"/><Block className="h-56 w-full rounded-xl"/></div><div className="h-80 rounded-2xl border border-slate-200 bg-white p-5"><Block className="mb-6 h-5 w-40"/><Block className="h-56 w-full rounded-xl"/></div></div><span className="sr-only">Loading training analysis…</span></div>;
  }

  if (variant === 'settings') {
    return <div role="status" aria-label="Loading settings" className="space-y-6"><PageHeading/><div className="max-w-3xl space-y-5 rounded-2xl border border-slate-200 bg-white p-6">{Array.from({ length: 6 }, (_, i) => <div key={i} className="space-y-2"><Block className="h-3 w-32"/><Block className="h-10 w-full rounded-lg"/></div>)}</div><span className="sr-only">Loading settings…</span></div>;
  }

  if (variant === 'content-edit') {
    return <div role="status" aria-label="Loading content governance" className="space-y-6"><PageHeading/><StatCards count={3}/><TableSkeleton rows={5} columns={5}/><span className="sr-only">Loading content governance…</span></div>;
  }

  return <div role="status" aria-label="Loading dashboard" className="space-y-7"><PageHeading/><StatCards/><div className="grid grid-cols-1 gap-5 xl:grid-cols-2"><div className="rounded-2xl border border-slate-200 bg-white p-5"><Block className="mb-6 h-5 w-44"/><div className="flex items-center gap-8"><Block className="h-40 w-40 rounded-full"/><div className="flex-1 space-y-4">{Array.from({ length: 3 }, (_, i) => <div key={i} className="space-y-2"><Block className="h-3 w-28"/><Block className="h-4 w-16"/></div>)}</div></div></div><div className="rounded-2xl border border-slate-200 bg-white p-5"><Block className="mb-6 h-5 w-40"/><Block className="h-56 w-full rounded-xl"/></div></div><TableSkeleton rows={4} columns={4}/><span className="sr-only">Loading dashboard…</span></div>;
};
