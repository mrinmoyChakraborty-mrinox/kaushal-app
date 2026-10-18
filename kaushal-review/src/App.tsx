/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React, { useState, useEffect, useRef } from 'react';
import {
  LayoutDashboard,
  Users,
  BarChart3,
  Bell,
  ClipboardList,
  Settings as SettingsIcon,
  ShieldCheck,
  Menu,
  X,
  ChevronDown,
  LogOut,
} from 'lucide-react';
import { collection, doc, onSnapshot } from 'firebase/firestore';
import { db } from './firebase';

import {
  AdminPersona,
  AdminSetting,
  AuditLogEntry,
  CertificateRecord,
  ContentChangeRequest,
  ContentVersion,
  ModuleData,
  NotificationItem,
  Worker,
} from './types';
import {
  ADMIN_PERSONAS,
  ensureDatabaseInitialized,
  INITIAL_AUDIT_LOGS,
  INITIAL_CERTIFICATES,
  INITIAL_CHANGE_REQUESTS,
  INITIAL_MODULES,
  INITIAL_NOTIFICATIONS,
  INITIAL_SETTINGS,
  INITIAL_VERSIONS,
  INITIAL_WORKERS,
} from './services/kaushalDatabase';
import { DashboardView } from './components/DashboardView';
import { WorkersView } from './components/WorkersView';
import { TrainingAnalysisView } from './components/TrainingAnalysisView';
import {
  AuditLogsView,
  NotificationsView,
  SettingsView,
} from './components/NotificationsAndAuditView';
import { ContentGovernanceView } from './components/ContentGovernanceView';
import { VerifyCertificateModal } from './components/VerifyCertificateModal';
import { AssessmentDrilldownModal } from './components/AssessmentDrilldownModal';
import { LoginView } from './components/LoginView';
import { SkeletonLoader } from './components/SkeletonLoader';

type NavTab =
  | 'dashboard'
  | 'workers'
  | 'training-analysis'
  | 'notifications'
  | 'audit-logs'
  | 'settings'
  | 'content-edit';

export default function App() {
  const [activeTab, setActiveTab] = useState<NavTab>('dashboard');
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [initialDataLoaded, setInitialDataLoaded] = useState(false);
  const loadedSourcesRef = useRef<Set<string>>(new Set());

  // Admin Persona & Auth state
  const [currentAdmin, setCurrentAdmin] = useState<AdminPersona>(
    ADMIN_PERSONAS[0]
  );
  const [profileMenuOpen, setProfileMenuOpen] = useState(false);

  const [profileModalOpen, setProfileModalOpen] = useState(false);
  const [profileName, setProfileName] = useState('');
  const [profileAvatar, setProfileAvatar] = useState('');
  const [profileDraftName, setProfileDraftName] = useState('');
  const [profileDraftAvatar, setProfileDraftAvatar] = useState('');

  // Live Firestore Collections State
  const [settings, setSettings] = useState<AdminSetting>(INITIAL_SETTINGS);
  const [modules, setModules] = useState<ModuleData[]>(INITIAL_MODULES);
  const [workers, setWorkers] = useState<Worker[]>(INITIAL_WORKERS);
  const [changeRequests, setChangeRequests] = useState<ContentChangeRequest[]>(
    INITIAL_CHANGE_REQUESTS
  );
  const [versions, setVersions] = useState<ContentVersion[]>(INITIAL_VERSIONS);
  const [certificates, setCertificates] = useState<CertificateRecord[]>(
    INITIAL_CERTIFICATES
  );
  const [auditLogs, setAuditLogs] =
    useState<AuditLogEntry[]>(INITIAL_AUDIT_LOGS);
  const [notifications, setNotifications] = useState<NotificationItem[]>(
    INITIAL_NOTIFICATIONS
  );

  // Interactive Drilldown & Modals State
  const [selectedWorker, setSelectedWorker] = useState<Worker | null>(null);
  const [selectedModuleDrilldown, setSelectedModuleDrilldown] =
    useState<ModuleData | null>(null);
  const [inspectedChangeRequest, setInspectedChangeRequest] =
    useState<ContentChangeRequest | null>(null);
  const [isVerifyModalOpen, setIsVerifyModalOpen] = useState(false);



  // Track the first response from each Firestore source so the UI can
  // render skeletons instead of briefly showing placeholder records.
  const markSourceLoaded = (source: string) => {
    loadedSourcesRef.current.add(source);
    if (loadedSourcesRef.current.size >= 8) setInitialDataLoaded(true);
  };

  // Initialize and subscribe only after authentication, so the first real
  // data fetch has a visible loading state and does not run on the login page.
  useEffect(() => {
    if (!isLoggedIn) return;
    setInitialDataLoaded(false);
    loadedSourcesRef.current = new Set();

    let unsubSettings: (() => void) | undefined;
    let unsubModules: (() => void) | undefined;
    let unsubWorkers: (() => void) | undefined;
    let unsubRequests: (() => void) | undefined;
    let unsubVersions: (() => void) | undefined;
    let unsubCerts: (() => void) | undefined;
    let unsubLogs: (() => void) | undefined;
    let unsubNotifs: (() => void) | undefined;

    ensureDatabaseInitialized().then(() => {
      unsubSettings = onSnapshot(doc(db, 'settings', 'main_config'), (snap) => {
        if (snap.exists()) {
          setSettings(snap.data() as AdminSetting);
        }
        markSourceLoaded('settings');
      }, () => markSourceLoaded('settings'));

      unsubModules = onSnapshot(collection(db, 'modules'), (snap) => {
        const list = snap.docs
          .map((d) => d.data() as ModuleData)
          .sort((a, b) => a.order - b.order);
        setModules(list);
        markSourceLoaded('modules');
      }, () => markSourceLoaded('modules'));

      unsubWorkers = onSnapshot(collection(db, 'workers'), (snap) => {
        const list = snap.docs
          .map((d) => d.data() as Worker)
          .sort((a, b) =>
            a.workerId.localeCompare(b.workerId, undefined, { numeric: true })
          );
        setWorkers(list);
        markSourceLoaded('workers');
      }, () => markSourceLoaded('workers'));

      unsubRequests = onSnapshot(
        collection(db, 'contentChangeRequests'),
        (snap) => {
          const list = snap.docs
            .map((d) => d.data() as ContentChangeRequest)
            .sort((a, b) => (b.createdAtMs || 0) - (a.createdAtMs || 0));
          setChangeRequests(list);
          markSourceLoaded('requests');
        },
        () => markSourceLoaded('requests')
      );

      unsubVersions = onSnapshot(collection(db, 'contentVersions'), (snap) => {
        const list = snap.docs.map((d) => d.data() as ContentVersion);
        setVersions(list);
        markSourceLoaded('versions');
      }, () => markSourceLoaded('versions'));

      unsubCerts = onSnapshot(collection(db, 'certificates'), (snap) => {
        const list = snap.docs.map((d) => d.data() as CertificateRecord);
        setCertificates(list);
        markSourceLoaded('certificates');
      }, () => markSourceLoaded('certificates'));

      unsubLogs = onSnapshot(collection(db, 'auditLogs'), (snap) => {
        const list = snap.docs
          .map((d) => d.data() as AuditLogEntry)
          .sort((a, b) => (b.createdAtMs || 0) - (a.createdAtMs || 0));
        setAuditLogs(list);
        markSourceLoaded('auditLogs');
      }, () => markSourceLoaded('auditLogs'));

      unsubNotifs = onSnapshot(collection(db, 'notifications'), (snap) => {
        const list = snap.docs
          .map((d) => d.data() as NotificationItem)
          .sort((a, b) => (b.createdAtMs || 0) - (a.createdAtMs || 0));
        setNotifications(list);
        markSourceLoaded('notifications');
      }, () => markSourceLoaded('notifications'));
    }).catch((error) => {
      console.error('Failed to initialize KAUSHAL data subscriptions:', error);
      // Do not leave the workspace stuck in a perpetual skeleton state.
      setInitialDataLoaded(true);
    });

    return () => {
      unsubSettings?.();
      unsubModules?.();
      unsubWorkers?.();
      unsubRequests?.();
      unsubVersions?.();
      unsubCerts?.();
      unsubLogs?.();
      unsubNotifs?.();
    };
  }, [isLoggedIn]);

  const unreadNotificationsCount = notifications.filter((n) => !n.read).length;

  // Primary Sidebar Navigation (TASK-ADM-001: 6 exact items)
  const navItems: {
    id: NavTab;
    label: string;
    icon: React.ComponentType<{ className?: string }>;
    badge?: number;
  }[] = [
      { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard },
      { id: 'workers', label: 'Workers', icon: Users },
      { id: 'training-analysis', label: 'Training Analysis', icon: BarChart3 },
      {
        id: 'notifications',
        label: 'Notifications',
        icon: Bell,
        badge: unreadNotificationsCount > 0 ? unreadNotificationsCount : undefined,
      },
      { id: 'audit-logs', label: 'Audit Logs', icon: ClipboardList },
      { id: 'settings', label: 'Settings', icon: SettingsIcon },
    ];

  const handleNavClick = (tabId: NavTab) => {
    setActiveTab(tabId);
    setMobileMenuOpen(false);
    if (tabId === 'workers') {
      setSelectedWorker(null);
    }
    if (tabId !== 'content-edit') {
      setInspectedChangeRequest(null);
    }
  };

  if (!isLoggedIn) {
    return (
      <LoginView
        onLoginSuccess={(adminPersona) => {
          setCurrentAdmin(adminPersona);

          const savedProfile = localStorage.getItem(
            `kaushal-profile-${adminPersona.mineId}`
          );

          if (savedProfile) {
            try {
              const parsed = JSON.parse(savedProfile);

              setProfileName(
                typeof parsed.name === 'string' ? parsed.name : ''
              );

              setProfileAvatar(
                typeof parsed.avatarUrl === 'string'
                  ? parsed.avatarUrl
                  : ''
              );
            } catch {
              setProfileName('');
              setProfileAvatar('');
            }
          } else {
            setProfileName('');
            setProfileAvatar('');
          }

          setIsLoggedIn(true);
        }}
      />
    );
  }

  return (
    <div className="min-h-screen flex bg-[#f4f7fb] text-slate-900">
      {/* Mobile Sidebar Overlay */}
      {mobileMenuOpen && (
        <div
          onClick={() => setMobileMenuOpen(false)}
          className="fixed inset-0 z-40 bg-slate-950/60 backdrop-blur-xs lg:hidden"
        />
      )}

      {/* LEFT SIDEBAR (Dark Navy #0f1e36 matching reference UI) */}
      <aside
        className={`fixed lg:static inset-y-0 left-0 z-50 w-60 bg-[#0f1e36] text-white flex flex-col justify-between shrink-0 transition-transform duration-200 ease-in-out ${mobileMenuOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'
          }`}
      >
        <div>
          {/* Brand Logo Header */}
          <div className="h-16 px-5 flex items-center justify-between border-b border-white/10">
            <button
              type="button"
              onClick={() => handleNavClick('dashboard')}
              className="flex items-center gap-2.5 cursor-pointer"
            >
              <div className="w-8 h-8 rounded-lg bg-[#1d63dc] flex items-center justify-center shadow-sm">
                <ShieldCheck className="w-5 h-5 text-white" />
              </div>
              <span className="text-lg font-extrabold tracking-wider text-white">
                KAUSHAL
              </span>
            </button>
            <button
              type="button"
              onClick={() => setMobileMenuOpen(false)}
              className="lg:hidden text-slate-400 hover:text-white"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* 6 Required Navigation Items (TASK-ADM-001) */}
          <nav className="p-3 space-y-1.5 mt-2">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive =
                activeTab === item.id ||
                (activeTab === 'content-edit' && item.id === 'dashboard');
              return (
                <button
                  key={item.id}
                  type="button"
                  onClick={() => handleNavClick(item.id)}
                  className={`w-full flex items-center justify-between px-3.5 py-2.5 rounded-lg text-sm font-medium transition-all cursor-pointer ${isActive
                    ? 'bg-[#1d63dc] text-white shadow-sm font-semibold'
                    : 'text-slate-300 hover:bg-white/8 hover:text-white'
                    }`}
                >
                  <div className="flex items-center gap-3">
                    <Icon className="w-4 h-4 shrink-0" />
                    <span>{item.label}</span>
                  </div>
                  {item.badge !== undefined && (
                    <span
                      className={`text-[11px] font-bold px-1.5 py-0.5 rounded-full ${isActive
                        ? 'bg-white text-[#1d63dc]'
                        : 'bg-rose-500 text-white'
                        }`}
                    >
                      {item.badge}
                    </span>
                  )}
                </button>
              );
            })}
          </nav>
        </div>

        {/* Sidebar Bottom Scope Footer */}
        <div className="p-4 m-3 rounded-xl bg-white/5 border border-white/10 text-xs space-y-1.5">
          <div className="flex items-center justify-between text-slate-400">
            <span>Active Scope</span>
            <span className="text-emerald-400 font-semibold flex items-center gap-1">
              <span className="w-1.5 h-1.5 rounded-full bg-emerald-400" />
              Live Sync
            </span>
          </div>
          <p className="font-semibold text-white truncate">
            {currentAdmin.siteName} ({currentAdmin.sector})
          </p>
          <p className="text-[11px] text-slate-400">
            Learn → Practice → Assess → Certify
          </p>
        </div>
      </aside>

      {/* MAIN CONTENT SHELL */}
      <div className="flex-1 flex flex-col min-w-0">
        {/* TOP HEADER (TASK-ADM-011) */}
        <header className="h-16 bg-white border-b border-slate-200 px-4 sm:px-8 flex items-center justify-between sticky top-0 z-30">
          <div className="flex items-center gap-3">
            <button
              type="button"
              onClick={() => setMobileMenuOpen(true)}
              className="lg:hidden p-2 rounded-lg text-slate-600 hover:bg-slate-100"
              aria-label="Open navigation"
            >
              <Menu className="w-5 h-5" />
            </button>

            <div className="hidden sm:flex items-center gap-2 text-xs text-slate-500">
              <span className="font-semibold text-slate-700">
                {currentAdmin.sector} Sector
              </span>
              <span>•</span>
              <span>{currentAdmin.siteName}</span>
            </div>
          </div>

          {/* Right Header Actions: Notifications Icon | Verify Certificate | Admin Profile */}
          <div className="flex items-center gap-3 sm:gap-4">
            {/* 1. Notifications Bell Icon */}
            <button
              type="button"
              onClick={() => handleNavClick('notifications')}
              className="relative p-2 rounded-lg text-slate-600 hover:text-slate-900 hover:bg-slate-100 transition-colors cursor-pointer"
              title="Notifications"
            >
              <Bell className="w-5 h-5" />
              {unreadNotificationsCount > 0 && (
                <span className="absolute top-1.5 right-1.5 w-2.5 h-2.5 rounded-full bg-rose-500 ring-2 ring-white" />
              )}
            </button>

            {/* 2. Verify Certificate Button (TASK-ADM-012) */}
            <button
              type="button"
              onClick={() => setIsVerifyModalOpen(true)}
              className="px-4 py-2 rounded-lg bg-[#0f1e36] hover:bg-[#192f52] text-white text-xs sm:text-sm font-semibold shadow-xs transition-colors cursor-pointer"
            >
              Verify Certificate
            </button>

            {/* 3. Mine / Site Profile */}
            <div className="relative">
              <button
                type="button"
                onClick={() => setProfileMenuOpen(!profileMenuOpen)}
                className="flex items-center gap-2.5 pl-2 pr-2.5 py-1 rounded-xl hover:bg-slate-100 transition-colors cursor-pointer"
              >
                {profileAvatar ? (
                  <img
                    src={profileAvatar}
                    alt={currentAdmin.siteName}
                    className="w-9 h-9 rounded-full object-cover border border-slate-200"
                  />
                ) : (
                  <div className="w-9 h-9 rounded-full bg-[#1d63dc] text-white flex items-center justify-center font-bold border border-slate-200">
                    {currentAdmin.siteName.charAt(0).toUpperCase()}
                  </div>
                )}

                <div className="text-left hidden sm:block">
                  <p className="text-xs font-bold text-slate-900 leading-tight">
                    {currentAdmin.siteName}
                  </p>

                  <p className="text-[11px] text-slate-500 leading-tight">
                    {currentAdmin.mineId}
                  </p>
                </div>

                <ChevronDown className="w-3.5 h-3.5 text-slate-400" />
              </button>

              {profileMenuOpen && (
                <div className="absolute right-0 mt-2 w-64 bg-white rounded-xl shadow-xl border border-slate-200 py-2 z-50 text-xs">
                  <div className="px-4 py-3 border-b border-slate-100">
                    <p className="font-bold text-slate-900">
                      {currentAdmin.siteName}
                    </p>

                    <p className="text-[11px] text-slate-500 mt-0.5">
                      Mine / Site ID: {currentAdmin.mineId}
                    </p>

                    {profileName && (
                      <p className="text-[11px] text-slate-500 mt-1">
                        Profile: {profileName}
                      </p>
                    )}
                  </div>

                  <div className="px-2 py-2">
                    <button
                      type="button"
                      onClick={() => {
                        setProfileDraftName(profileName);
                        setProfileDraftAvatar(profileAvatar);
                        setProfileMenuOpen(false);
                        setProfileModalOpen(true);
                      }}
                      className="w-full px-3 py-2 rounded-lg text-left text-slate-700 hover:bg-slate-50 font-semibold cursor-pointer"
                    >
                      Profile
                    </button>

                    <button
                      type="button"
                      onClick={() => {
                        setIsLoggedIn(false);
                        setProfileMenuOpen(false);
                      }}
                      className="w-full px-3 py-2 rounded-lg text-left text-rose-600 hover:bg-rose-50 flex items-center gap-2 font-semibold cursor-pointer"
                    >
                      <LogOut className="w-3.5 h-3.5" />
                      Sign Out
                    </button>
                  </div>
                </div>
              )}
            </div>
          </div>
        </header>

        {/* WORKSPACE AREA */}
        <main className="flex-1 p-4 sm:p-6 lg:p-8 max-w-7xl w-full mx-auto">
          {!initialDataLoaded ? (
            <SkeletonLoader variant={activeTab} />
          ) : <>
          {activeTab === 'dashboard' && (
            <DashboardView
              settings={settings}
              modules={modules}
              changeRequests={changeRequests}
              currentAdmin={currentAdmin}
              onSelectModuleDrilldown={(mod) => setSelectedModuleDrilldown(mod)}
              onOpenNewContentEdit={() => {
                setInspectedChangeRequest(null);
                setActiveTab('content-edit');
              }}
              onInspectChangeRequest={(req) => {
                setInspectedChangeRequest(req);
                setActiveTab('content-edit');
              }}
            />
          )}

          {activeTab === 'content-edit' && (
            <ContentGovernanceView
              modules={modules}
              changeRequests={changeRequests}
              versions={versions}
              currentAdmin={currentAdmin}
              onSelectAdmin={setCurrentAdmin}
              initialRequestToInspect={inspectedChangeRequest}
              requirePeerApprovalDefault={settings.requirePeerApprovalDefault}
              onBackToDashboard={() => {
                setInspectedChangeRequest(null);
                setActiveTab('dashboard');
              }}
            />
          )}

          {activeTab === 'workers' && (
            <WorkersView
              workers={workers}
              selectedWorker={selectedWorker}
              onSelectWorker={setSelectedWorker}
              siteName={currentAdmin.siteName}
              totalSiteWorkersCount={settings.totalWorkers}
            />
          )}

          {activeTab === 'training-analysis' && (
            <TrainingAnalysisView settings={settings} />
          )}

          {activeTab === 'notifications' && (
            <NotificationsView
              notifications={notifications}
              changeRequests={changeRequests}
              onInspectChangeRequest={(req) => {
                setInspectedChangeRequest(req);
                setActiveTab('content-edit');
              }}
              onOpenSettings={() => setActiveTab('settings')}
            />
          )}

          {activeTab === 'audit-logs' && (
            <AuditLogsView
              auditLogs={auditLogs}
              changeRequests={changeRequests}
              onInspectChangeRequest={(req) => {
                setInspectedChangeRequest(req);
                setActiveTab('content-edit');
              }}
            />
          )}

          {activeTab === 'settings' && (
            <SettingsView
              settings={settings}
              currentAdmin={currentAdmin}
              onSelectAdmin={setCurrentAdmin}
            />
          )}
          </>}
        </main>
      </div>
      {/* Mine / Site Profile Modal */}
      {profileModalOpen && (
        <div className="fixed inset-0 z-[70] flex items-center justify-center bg-slate-950/50 backdrop-blur-sm px-4">
          <div className="w-full max-w-md bg-white rounded-2xl shadow-2xl border border-slate-200 overflow-hidden">
            <div className="px-6 py-5 border-b border-slate-100">
              <h2 className="text-lg font-bold text-slate-900">
                Profile
              </h2>

              <p className="text-xs text-slate-500 mt-1">
                Optional personal profile for {currentAdmin.siteName}
              </p>
            </div>

            <div className="p-6 space-y-5">
              {/* Profile Picture */}
              <div>
                <label className="block text-xs font-semibold text-slate-700 mb-2">
                  Profile Picture
                </label>

                <div className="flex items-center gap-4">
                  {profileDraftAvatar ? (
                    <img
                      src={profileDraftAvatar}
                      alt="Profile preview"
                      className="w-16 h-16 rounded-full object-cover border border-slate-200"
                    />
                  ) : (
                    <div className="w-16 h-16 rounded-full bg-[#1d63dc] text-white flex items-center justify-center text-xl font-bold">
                      {currentAdmin.siteName.charAt(0).toUpperCase()}
                    </div>
                  )}

                  <div>
                    <label className="inline-flex items-center px-3 py-2 rounded-lg border border-slate-200 text-xs font-semibold text-slate-700 hover:bg-slate-50 cursor-pointer">
                      Upload Picture

                      <input
                        type="file"
                        accept="image/png,image/jpeg,image/webp"
                        className="hidden"
                        onChange={(event) => {
                          const file = event.target.files?.[0];

                          if (!file) {
                            return;
                          }

                          if (file.size > 2 * 1024 * 1024) {
                            alert('Profile picture must be 2MB or smaller.');
                            event.target.value = '';
                            return;
                          }

                          const reader = new FileReader();

                          reader.onload = () => {
                            if (typeof reader.result === 'string') {
                              setProfileDraftAvatar(reader.result);
                            }
                          };

                          reader.readAsDataURL(file);
                        }}
                      />
                    </label>

                    <p className="text-[10px] text-slate-400 mt-1.5">
                      PNG, JPG or WEBP · Maximum 2MB
                    </p>
                  </div>
                </div>
              </div>

              {/* Profile Name */}
              <div>
                <label
                  htmlFor="profile-name"
                  className="block text-xs font-semibold text-slate-700 mb-2"
                >
                  Name
                </label>

                <input
                  id="profile-name"
                  type="text"
                  value={profileDraftName}
                  onChange={(event) =>
                    setProfileDraftName(event.target.value)
                  }
                  placeholder="Enter your name"
                  className="w-full px-3 py-2.5 rounded-lg border border-slate-200 text-sm text-slate-900 outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500"
                />

                <p className="text-[10px] text-slate-400 mt-1.5">
                  Optional. Your Mine / Site identity remains unchanged.
                </p>
              </div>
            </div>

            <div className="px-6 py-4 bg-slate-50 border-t border-slate-100 flex items-center justify-end gap-2">
              <button
                type="button"
                onClick={() => {
                  setProfileModalOpen(false);
                }}
                className="px-4 py-2 rounded-lg border border-slate-200 bg-white text-xs font-semibold text-slate-700 hover:bg-slate-50 cursor-pointer"
              >
                Cancel
              </button>

              <button
                type="button"
                onClick={() => {
                  const savedProfile = {
                    name: profileDraftName.trim(),
                    avatarUrl: profileDraftAvatar,
                  };

                  localStorage.setItem(
                    `kaushal-profile-${currentAdmin.mineId}`,
                    JSON.stringify(savedProfile)
                  );

                  setProfileName(savedProfile.name);
                  setProfileAvatar(savedProfile.avatarUrl);
                  setProfileModalOpen(false);
                }}
                className="px-4 py-2 rounded-lg bg-[#1d63dc] text-white text-xs font-semibold hover:bg-blue-700 cursor-pointer"
              >
                Save Profile
              </button>
            </div>
          </div>
        </div>
      )}
      {/* Verify Certificate Modal (Panel 12 / TASK-ADM-012) */}
      <VerifyCertificateModal
        isOpen={isVerifyModalOpen}
        onClose={() => setIsVerifyModalOpen(false)}
        certificates={certificates}
        workers={workers}
      />

      {/* Site-Scoped Assessment Drilldown Modal (TASK-ADM-023) */}
      <AssessmentDrilldownModal
        module={selectedModuleDrilldown}
        siteName={currentAdmin.siteName}
        workers={workers}
        onClose={() => setSelectedModuleDrilldown(null)}
        onSelectWorker={(worker) => {
          setSelectedWorker(worker);
          setActiveTab('workers');
        }}
      />
    </div>
  );
}
