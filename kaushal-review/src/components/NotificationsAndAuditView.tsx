import React, { useState } from 'react';
import {
  FileEdit,
  GraduationCap,
  Settings as SettingsIcon,
  CheckCheck,
  Bell,
  Shield,
  Sliders,
  UserCheck,
} from 'lucide-react';
import {
  AdminPersona,
  AdminSetting,
  AuditLogEntry,
  ContentChangeRequest,
  NotificationCategory,
  NotificationItem,
} from '../types';
import {
  ADMIN_PERSONAS,
  markAllNotificationsReadInDb,
  markNotificationReadInDb,
  updateNotificationSettingsInDb,
} from '../services/kaushalDatabase';

interface NotificationsViewProps {
  notifications: NotificationItem[];
  changeRequests: ContentChangeRequest[];
  onInspectChangeRequest: (req: ContentChangeRequest) => void;
  onOpenSettings: () => void;
}

export const NotificationsView: React.FC<NotificationsViewProps> = ({
  notifications,
  changeRequests,
  onInspectChangeRequest,
  onOpenSettings,
}) => {
  const [activeTab, setActiveTab] = useState<'ALL' | NotificationCategory>(
    'ALL'
  );

  const trainingCount = notifications.filter(
    (n) => n.category === 'TRAINING_UPDATE'
  ).length;
  const contentCount = notifications.filter(
    (n) => n.category === 'CONTENT_EDIT_REQUEST'
  ).length;
  const systemCount = notifications.filter(
    (n) => n.category === 'SYSTEM_NOTIFICATION'
  ).length;

  const filtered =
    activeTab === 'ALL'
      ? notifications
      : notifications.filter((n) => n.category === activeTab);

  const getCategoryIcon = (category: NotificationCategory, idx: number) => {
    if (category === 'CONTENT_EDIT_REQUEST') {
      return (
        <div
          className={`w-10 h-10 rounded-xl flex items-center justify-center shrink-0 ${
            idx % 2 === 0
              ? 'bg-purple-100 text-purple-600'
              : 'bg-rose-100 text-rose-600'
          }`}
        >
          <FileEdit className="w-5 h-5" />
        </div>
      );
    }
    if (category === 'TRAINING_UPDATE') {
      return (
        <div className="w-10 h-10 rounded-xl bg-emerald-100 text-emerald-600 flex items-center justify-center shrink-0">
          <GraduationCap className="w-5 h-5" />
        </div>
      );
    }
    return (
      <div className="w-10 h-10 rounded-xl bg-indigo-100 text-indigo-600 flex items-center justify-center shrink-0">
        <SettingsIcon className="w-5 h-5" />
      </div>
    );
  };

  return (
    <div className="space-y-5">
      {/* Header */}
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl font-bold text-slate-900">Notifications</h2>
          <p className="text-sm text-slate-500">
            Training updates, content edit requests, and system announcements.
          </p>
        </div>

        <div className="flex items-center gap-2.5">
          <button
            type="button"
            onClick={() => markAllNotificationsReadInDb(notifications)}
            className="px-3.5 py-2 rounded-lg border border-slate-200 bg-white hover:bg-slate-50 text-slate-700 text-xs font-semibold flex items-center gap-1.5 cursor-pointer"
          >
            <CheckCheck className="w-4 h-4 text-[#1d63dc]" />
            Mark All Read
          </button>
          <button
            type="button"
            onClick={onOpenSettings}
            className="px-3.5 py-2 rounded-lg bg-slate-900 hover:bg-slate-800 text-white text-xs font-semibold flex items-center gap-1.5 cursor-pointer"
          >
            <Sliders className="w-3.5 h-3.5" />
            Notification Settings
          </button>
        </div>
      </div>

      {/* Main Card matching Panel 9 */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-2xs overflow-hidden max-w-4xl">
        {/* Category Tabs matching Panel 9: All (8) | Training Updates (3) | Content Edits (3) | System (2) */}
        <div className="flex items-center gap-6 px-6 border-b border-slate-200 overflow-x-auto text-sm">
          <button
            type="button"
            onClick={() => setActiveTab('ALL')}
            className={`py-3.5 font-semibold border-b-2 transition-colors whitespace-nowrap cursor-pointer ${
              activeTab === 'ALL'
                ? 'border-[#1d63dc] text-[#1d63dc]'
                : 'border-transparent text-slate-500 hover:text-slate-800'
            }`}
          >
            All ({notifications.length})
          </button>
          <button
            type="button"
            onClick={() => setActiveTab('TRAINING_UPDATE')}
            className={`py-3.5 font-semibold border-b-2 transition-colors whitespace-nowrap cursor-pointer ${
              activeTab === 'TRAINING_UPDATE'
                ? 'border-[#1d63dc] text-[#1d63dc]'
                : 'border-transparent text-slate-500 hover:text-slate-800'
            }`}
          >
            Training Updates ({trainingCount})
          </button>
          <button
            type="button"
            onClick={() => setActiveTab('CONTENT_EDIT_REQUEST')}
            className={`py-3.5 font-semibold border-b-2 transition-colors whitespace-nowrap cursor-pointer ${
              activeTab === 'CONTENT_EDIT_REQUEST'
                ? 'border-[#1d63dc] text-[#1d63dc]'
                : 'border-transparent text-slate-500 hover:text-slate-800'
            }`}
          >
            Content Edits ({contentCount})
          </button>
          <button
            type="button"
            onClick={() => setActiveTab('SYSTEM_NOTIFICATION')}
            className={`py-3.5 font-semibold border-b-2 transition-colors whitespace-nowrap cursor-pointer ${
              activeTab === 'SYSTEM_NOTIFICATION'
                ? 'border-[#1d63dc] text-[#1d63dc]'
                : 'border-transparent text-slate-500 hover:text-slate-800'
            }`}
          >
            System ({systemCount})
          </button>
        </div>

        {/* Notification Items List */}
        <div className="divide-y divide-slate-100">
          {filtered.length === 0 ? (
            <div className="p-10 text-center text-sm text-slate-500">
              No notifications in this category.
            </div>
          ) : (
            filtered.map((item, idx) => (
              <div
                key={item.notificationId}
                onClick={() => {
                  if (!item.read) {
                    markNotificationReadInDb(item.notificationId);
                  }
                  if (item.category === 'CONTENT_EDIT_REQUEST' && item.targetId) {
                    const found = changeRequests.find(
                      (r) => r.requestId === item.targetId
                    );
                    if (found) onInspectChangeRequest(found);
                  }
                }}
                className={`p-5 flex items-start justify-between gap-4 hover:bg-slate-50 transition-colors cursor-pointer ${
                  !item.read ? 'bg-blue-50/20' : ''
                }`}
              >
                <div className="flex items-start gap-4">
                  {getCategoryIcon(item.category, idx)}
                  <div>
                    <div className="flex items-center gap-2">
                      <h4 className="text-sm font-bold text-slate-900">
                        {item.title}
                      </h4>
                      {!item.read && (
                        <span className="w-2 h-2 rounded-full bg-[#1d63dc]" />
                      )}
                    </div>
                    <p className="text-sm text-slate-600 mt-0.5">
                      {item.message}
                    </p>
                  </div>
                </div>

                <span className="text-xs text-slate-400 whitespace-nowrap shrink-0">
                  {item.timestamp}
                </span>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

// PANEL 11: AUDIT LOGS VIEW (Explicitly excludes Certificate Verification)
interface AuditLogsViewProps {
  auditLogs: AuditLogEntry[];
  changeRequests: ContentChangeRequest[];
  onInspectChangeRequest: (req: ContentChangeRequest) => void;
}

export const AuditLogsView: React.FC<AuditLogsViewProps> = ({
  auditLogs,
  changeRequests,
  onInspectChangeRequest,
}) => {
  // Explicitly filter out any hypothetical certificate verification events per PRD Section 16
  const governanceLogs = auditLogs.filter(
    (log) =>
      log.action !== ('Certificate Verified' as any) &&
      log.action !== ('certificate_verification' as any)
  );

  const getActionStyle = (action: AuditLogEntry['action']) => {
    switch (action) {
      case 'Approved':
        return 'text-emerald-600 font-semibold';
      case 'Published':
        return 'text-emerald-600 font-bold';
      case 'Rejected':
        return 'text-rose-600 font-semibold';
      default:
        return 'text-slate-800 font-medium';
    }
  };

  return (
    <div className="space-y-5">
      <div>
        <h2 className="text-2xl font-bold text-slate-900">Audit Logs</h2>
        <p className="text-sm text-slate-500">
          Track content changes and approvals.
        </p>
      </div>

      {/* Audit Logs Table Card matching Panel 11 */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-2xs overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-slate-200 bg-slate-50/80 text-xs font-semibold text-slate-500">
                <th className="py-3.5 px-6">Date</th>
                <th className="py-3.5 px-4">Admin</th>
                <th className="py-3.5 px-4">Action</th>
                <th className="py-3.5 px-4">Module</th>
                <th className="py-3.5 px-4">Version</th>
                <th className="py-3.5 px-6">Governance Detail</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 text-sm">
              {governanceLogs.map((log) => {
                const linkedRequest = changeRequests.find(
                  (r) => r.requestId === log.targetId
                );
                return (
                  <tr
                    key={log.logId}
                    onClick={() => {
                      if (linkedRequest) onInspectChangeRequest(linkedRequest);
                    }}
                    className={`hover:bg-slate-50/80 transition-colors ${
                      linkedRequest ? 'cursor-pointer' : ''
                    }`}
                  >
                    <td className="py-3.5 px-6 text-slate-700 whitespace-nowrap">
                      {log.timestamp}
                    </td>
                    <td className="py-3.5 px-4 font-medium text-slate-900 whitespace-nowrap">
                      {log.actorName}
                    </td>
                    <td
                      className={`py-3.5 px-4 whitespace-nowrap ${getActionStyle(
                        log.action
                      )}`}
                    >
                      {log.action}
                    </td>
                    <td className="py-3.5 px-4 text-slate-800 whitespace-nowrap">
                      {log.moduleName}
                    </td>
                    <td className="py-3.5 px-4 font-mono text-slate-700 whitespace-nowrap">
                      {log.version}
                    </td>
                    <td className="py-3.5 px-6 text-xs text-slate-500">
                      {log.details || `Request Ref: ${log.targetId}`}
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
};

// PANEL 10: NOTIFICATION SETTINGS & ADMIN SETTINGS VIEW
interface SettingsViewProps {
  settings: AdminSetting;
  currentAdmin: AdminPersona;
  onSelectAdmin: (persona: AdminPersona) => void;
}

export const SettingsView: React.FC<SettingsViewProps> = ({
  settings,
  currentAdmin,
  onSelectAdmin,
}) => {
  const handleToggle = async (
    key:
      | 'trainingUpdates'
      | 'contentEditRequests'
      | 'systemNotifications'
      | 'requirePeerApprovalDefault',
    currentVal: boolean
  ) => {
    await updateNotificationSettingsInDb({ [key]: !currentVal });
  };

  return (
    <div className="space-y-6 max-w-3xl">
      <div>
        <h2 className="text-2xl font-bold text-slate-900">
          Notification Settings
        </h2>
        <p className="text-sm text-slate-500">
          Choose what you want to be notified about.
        </p>
      </div>

      {/* Notification Settings Card matching Panel 10 */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-2xs divide-y divide-slate-100">
        {/* 1. Training Updates */}
        <div className="p-6 flex items-center justify-between gap-6">
          <div>
            <h3 className="text-base font-bold text-slate-900">
              Training Updates
            </h3>
            <p className="text-sm text-slate-500 mt-0.5">
              Get notified about training related updates (e.g. new content, module updates).
            </p>
          </div>
          <button
            type="button"
            role="switch"
            aria-checked={settings.trainingUpdates}
            onClick={() =>
              handleToggle('trainingUpdates', settings.trainingUpdates)
            }
            className={`relative inline-flex h-6 w-11 shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out ${
              settings.trainingUpdates ? 'bg-[#1d63dc]' : 'bg-slate-300'
            }`}
          >
            <span
              className={`pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow-sm ring-0 transition duration-200 ease-in-out ${
                settings.trainingUpdates ? 'translate-x-5' : 'translate-x-0'
              }`}
            />
          </button>
        </div>

        {/* 2. Content Edit Requests */}
        <div className="p-6 flex items-center justify-between gap-6">
          <div>
            <h3 className="text-base font-bold text-slate-900">
              Content Edit Requests
            </h3>
            <p className="text-sm text-slate-500 mt-0.5">
              Get notified when a new edit request requires your review.
            </p>
          </div>
          <button
            type="button"
            role="switch"
            aria-checked={settings.contentEditRequests}
            onClick={() =>
              handleToggle('contentEditRequests', settings.contentEditRequests)
            }
            className={`relative inline-flex h-6 w-11 shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out ${
              settings.contentEditRequests ? 'bg-[#1d63dc]' : 'bg-slate-300'
            }`}
          >
            <span
              className={`pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow-sm ring-0 transition duration-200 ease-in-out ${
                settings.contentEditRequests ? 'translate-x-5' : 'translate-x-0'
              }`}
            />
          </button>
        </div>

        {/* 3. System Notifications */}
        <div className="p-6 flex items-center justify-between gap-6">
          <div>
            <h3 className="text-base font-bold text-slate-900">
              System Notifications
            </h3>
            <p className="text-sm text-slate-500 mt-0.5">
              Get important system updates and announcements.
            </p>
          </div>
          <button
            type="button"
            role="switch"
            aria-checked={settings.systemNotifications}
            onClick={() =>
              handleToggle('systemNotifications', settings.systemNotifications)
            }
            className={`relative inline-flex h-6 w-11 shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out ${
              settings.systemNotifications ? 'bg-[#1d63dc]' : 'bg-slate-300'
            }`}
          >
            <span
              className={`pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow-sm ring-0 transition duration-200 ease-in-out ${
                settings.systemNotifications ? 'translate-x-5' : 'translate-x-0'
              }`}
            />
          </button>
        </div>
      </div>

      {/* Site & Peer Governance Role Switcher Card */}
      <div className="bg-white rounded-xl border border-slate-200 p-6 shadow-2xs space-y-4">
        <div className="flex items-center gap-2.5">
          <UserCheck className="w-5 h-5 text-[#1d63dc]" />
          <div>
            <h3 className="text-base font-bold text-slate-900">
              Same-Industry Peer Governance Scope
            </h3>
            <p className="text-xs text-slate-500">
              Switch active admin perspective to test multi-admin peer approval (Admin A, Admin B, Admin C)
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-1">
          {ADMIN_PERSONAS.map((persona) => {
            const isSelected = currentAdmin.id === persona.id;
            return (
              <button
                key={persona.id}
                type="button"
                onClick={() => onSelectAdmin(persona)}
                className={`p-3.5 rounded-xl border text-left transition-all flex items-center gap-3 cursor-pointer ${
                  isSelected
                    ? 'border-[#1d63dc] bg-blue-50/50 ring-2 ring-[#1d63dc]/20'
                    : 'border-slate-200 hover:bg-slate-50'
                }`}
              >
                <img
                  src={persona.avatarUrl}
                  alt={persona.name}
                  className="w-10 h-10 rounded-full object-cover"
                />
                <div>
                  <p className="text-sm font-bold text-slate-900">
                    {persona.name}
                  </p>
                  <p className="text-xs text-slate-500">
                    {persona.siteName} • {persona.sector}
                  </p>
                </div>
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
};
