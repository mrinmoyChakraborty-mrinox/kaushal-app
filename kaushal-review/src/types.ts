export type LanguageType = 'Hindi' | 'Santali';
export type SectorType = 'Mining' | 'Steel' | 'Mica';
export type WorkAreaType = 'Underground' | 'Surface';
export type WorkerStatusType = 'Completed' | 'In Progress' | 'Pending';

export interface WorkerModuleProgress {
  moduleId: string;
  moduleName: string;
  progressPercent: number; // 0 to 100
  status: WorkerStatusType;
  achievedPercent: number | null; // null when Pending or In Progress; number when Completed
  arCompleted?: boolean;
  failedAttempts?: number;
  lastUpdated?: string;
}

export interface Worker {
  workerId: string;
  name: string;
  email: string;
  language: LanguageType;
  sector: SectorType;
  workArea: WorkAreaType;
  siteId: string;
  status: WorkerStatusType;
  completionPercent: number | null; // Shown ONLY when status is 'Completed'
  verificationStatus: 'Verified' | 'Pending';
  avatarUrl: string;
  modules: WorkerModuleProgress[];
  lastSyncedAt: string;
  createdAt: string;
}

export interface ModuleScreen {
  screenId: string;
  order: number;
  title: string;
  contentEn: string;
  contentHi: string;
  contentSat: string;
}

export interface ModuleChapter {
  chapterId: string;
  order: number;
  title: string;
  isAssessment?: boolean;
  screens: ModuleScreen[];
}

export interface ModuleData {
  moduleId: string;
  order: number;
  name: string;
  shortName: string;
  arEnabled: boolean;
  arScenarios: string[];
  currentVersion: string;
  avgScore: number;
  passRate: number;
  completedCount: number;
  failedAttempts: number;
  mostMissedQuestion: string;
  workersNeedingRefresher: number;
  barColor: 'emerald' | 'blue' | 'amber';
  chapters: ModuleChapter[];
}

export type ContentRequestStatus =
  | 'DRAFT'
  | 'PENDING_REVIEW'
  | 'PARTIALLY_APPROVED'
  | 'REJECTED'
  | 'APPROVED'
  | 'PUBLISHED';

export interface PeerApprovalRecord {
  reviewerId: string;
  reviewerName: string;
  siteName: string;
  avatarUrl: string;
  status: 'approved' | 'rejected' | 'pending';
  timestamp: string | null;
}

export interface ContentChangeRequest {
  requestId: string;
  moduleId: string;
  moduleName: string;
  chapterId: string;
  chapterName: string;
  screenId: string;
  screenName: string;
  requestedBy: string;
  requestedByName: string;
  siteId: string;
  industryScope: SectorType;
  description: string;
  currentContent: string;
  proposedContent: string;
  proposedContentHi?: string;
  proposedContentSat?: string;
  aiDraftUsed: boolean;
  adminReviewed: boolean;
  approvalRequired: boolean;
  approvalGroup: string;
  requiredApprovals: 'ALL';
  approvals: PeerApprovalRecord[];
  status: ContentRequestStatus;
  baseVersion: string;
  proposedVersion: string;
  createdAt: string;
  createdAtMs: number;
  publishedAt?: string | null;
}

export interface ContentVersion {
  versionId: string;
  moduleId: string;
  moduleName: string;
  chapterId: string;
  chapterName: string;
  screenId: string;
  screenName: string;
  versionNumber: string;
  content: string;
  contentHi?: string;
  contentSat?: string;
  authorId: string;
  authorName: string;
  requestId: string;
  approvalSummary: string;
  publishedAt: string;
  isCurrent: boolean;
}

export interface CertificateRecord {
  certificateId: string;
  workerId: string;
  workerName: string;
  email: string;
  siteId: string;
  sector: SectorType;
  workArea: WorkAreaType;
  language: LanguageType;
  courseName: string;
  score: number;
  issuedAt: string;
  qrPayload: string;
  status: 'VERIFIED' | 'NOT_VERIFIED';
}

export interface AuditLogEntry {
  logId: string;
  actorId: string;
  actorName: string;
  action: 'Edit Requested' | 'Approved' | 'Rejected' | 'Published' | 'Module Updated';
  moduleId: string;
  moduleName: string;
  version: string;
  timestamp: string;
  createdAtMs: number;
  targetId: string;
  details?: string;
}

export type NotificationCategory =
  | 'TRAINING_UPDATE'
  | 'CONTENT_EDIT_REQUEST'
  | 'SYSTEM_NOTIFICATION';

export interface NotificationItem {
  notificationId: string;
  category: NotificationCategory;
  title: string;
  message: string;
  timestamp: string;
  createdAtMs: number;
  read: boolean;
  targetId?: string;
  moduleName?: string;
}

export interface MonthlyParticipationPoint {
  month: string;
  fullMonth: string;
  participants: number;
}

export interface SectorDistributionItem {
  sector: SectorType;
  percentage: number;
  participants: number;
  color: string;
}

export interface AdminSetting {
  settingId: string;
  siteId: string;
  siteName: string;
  sector: SectorType;
  trainingUpdates: boolean;
  contentEditRequests: boolean;
  systemNotifications: boolean;
  requirePeerApprovalDefault: boolean;
  // Site-level & Platform-level real tracked metrics
  totalLearners: number;
  totalWorkers: number;
  pendingVerification: number;
  publishedChanges: number;
  trainingCompletedCount: number;
  trainingInProgressCount: number;
  trainingNewlyStartedCount: number;
  certificatesIssuedSite: number;
  platformTotalParticipants: number;
  platformModuleCompletion: number;
  platformAssessmentPassRate: number;
  platformCertificatesIssued: number;
  monthlyParticipation: MonthlyParticipationPoint[];
  sectorDistribution: SectorDistributionItem[];
}

export interface AdminPersona {
  id: string;
  mineId: string;
  name: string;
  shortLabel: string;
  siteName: string;
  sector: SectorType;
  email: string;
  avatarUrl: string;
  role?: string;
}
