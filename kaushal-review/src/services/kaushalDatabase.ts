import {
  collection,
  doc,
  getDoc,
  getDocs,
  setDoc,
  updateDoc,
  writeBatch,
  query,
  where,
} from 'firebase/firestore';
import { db, handleFirestoreError, OperationType } from '../firebase';
import {
  AdminPersona,
  AdminSetting,
  AuditLogEntry,
  CertificateRecord,
  ContentChangeRequest,
  ContentVersion,
  LanguageType,
  ModuleData,
  NotificationItem,
  SectorType,
  WorkAreaType,
  Worker,
  WorkerModuleProgress,
  WorkerStatusType,
} from '../types';

export const ADMIN_PERSONAS: AdminPersona[] = [
  {
    id: 'adminA',
    mineId: 'KSH-DHN-001',
    name: 'Rajesh Sharma',
    shortLabel: 'Rajesh Sharma',
    siteName: 'Dhanbad Coalfield Shaft 4',
    sector: 'Mining',
    role: 'Chief Safety Officer',
    email: 'rajesh.sharma@kaushal-dhanbad.in',
    avatarUrl:
      'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=160&auto=format&fit=crop&q=80',
  },
  {
    id: 'adminB',
    mineId: 'KSH-JSR-002',
    name: 'Priya Verma',
    shortLabel: 'Priya Verma',
    siteName: 'Jamshedpur Steel Plant',
    sector: 'Steel',
    role: 'Industrial Safety Director',
    email: 'priya.verma@kaushal-jamshedpur.in',
    avatarUrl:
      'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=160&auto=format&fit=crop&q=80',
  },
  {
    id: 'adminC',
    mineId: 'KSH-KDR-003',
    name: 'Amit Das',
    shortLabel: 'Amit Das',
    siteName: 'Koderma Mica Belt Mine',
    sector: 'Mica',
    role: 'Site Safety Inspector',
    email: 'amit.das@kaushal-koderma.in',
    avatarUrl:
      'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=160&auto=format&fit=crop&q=80',
  },
  {
    id: 'adminD',
    mineId: 'KSH-RAN-004',
    name: 'Suresh Kumar',
    shortLabel: 'Suresh Kumar',
    siteName: 'Ranchi Open Cast Pit',
    sector: 'Mining',
    role: 'Operations Supervisor',
    email: 'suresh.kumar@kaushal-ranchi.in',
    avatarUrl:
      'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=160&auto=format&fit=crop&q=80',
  },
];

export const INITIAL_MODULES: ModuleData[] = [
  {
    moduleId: 'M01',
    order: 1,
    name: 'Fire & Explosion Response',
    shortName: 'Fire Safety',
    arEnabled: true,
    arScenarios: [
      'Fire-risk recognition in underground shafts',
      'Emergency evacuation route navigation',
      'PASS Fire Extinguisher practical handling',
    ],
    currentVersion: '2.1',
    avgScore: 86,
    passRate: 84,
    completedCount: 214,
    failedAttempts: 19,
    mostMissedQuestion: 'Q04: Class C electrical mine fire suppression agent selection',
    workersNeedingRefresher: 11,
    barColor: 'emerald',
    chapters: [
      {
        chapterId: 'M01C01',
        order: 1,
        title: 'Ignition Sources & Underground Fire Triangle',
        screens: [
          {
            screenId: 'M01C01S01',
            order: 1,
            title: 'Recognizing Coal Dust & Methane Ignition Risks',
            contentEn:
              'Always inspect electrical cables, conveyor rollers, and cutting picks before starting shift operations. Report any abnormal heat, sparks, or burning odor immediately to the shift overman.',
            contentHi:
              'शिफ्ट शुरू करने से पहले हमेशा बिजली के केबल, कन्वेयर रोलर्स और कटिंग पिक्स की जांच करें। किसी भी असामान्य गर्मी, चिंगारी या जलने की गंध की सूचना तुरंत शिफ्ट ओवरमैन को दें।',
            contentSat:
              'ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵ ᱢᱟᱬᱟᱝ ᱨᱮ ᱵᱤᱡᱽᱞᱤ ᱛᱟᱨ ᱟᱨ ᱢᱮᱥᱤᱱ ᱠᱚ ᱵᱷᱟᱹᱜᱤ ᱞᱮᱠᱟ ᱧᱮᱞ ᱵᱤᱰᱟᱹᱣ ᱢᱮ। ᱡᱟᱦᱟᱸᱱᱟᱜ ᱥᱮᱸᱜᱮᱞ ᱪᱤᱸᱜᱟᱹᱨᱤ ᱧᱮᱞ ᱞᱮᱱᱠᱷᱟᱱ ᱞᱚᱜᱚᱱ ᱥᱩᱯᱟᱨᱵᱷᱟᱭᱡᱟᱨ ᱞᱟᱹᱭ ᱟᱭ ᱢᱮ।',
          },
          {
            screenId: 'M01C01S02',
            order: 2,
            title: 'Eliminating Open Flames & Contraband',
            contentEn:
              'Matches, lighters, and uncertified electronic devices are strictly prohibited inside underground workings and flammable storage zones.',
            contentHi:
              'भूमिगत खदानों और ज्वलनशील भंडारण क्षेत्रों के अंदर माचिस, लाइटर और अप्रमाणित इलेक्ट्रॉनिक उपकरण सख्त वर्जित हैं।',
            contentSat:
              'ᱠᱷᱟᱫᱟᱱ ᱵᱷᱤᱛᱨᱤ ᱨᱮ ᱢᱟᱪᱤᱥ, ᱞᱟᱭᱴᱟᱨ ᱟᱨ ᱵᱟᱝ ᱵᱤᱰᱟᱹᱣ ᱟᱠᱟᱱ ᱢᱚᱵᱟᱭᱤᱞ ᱤᱫᱤ ᱢᱟᱱᱟ ᱜᱮᱭᱟ।',
          },
        ],
      },
      {
        chapterId: 'M01C02',
        order: 2,
        title: 'Fire Extinguisher Use',
        screens: [
          {
            screenId: 'M01C02S04',
            order: 4,
            title: 'Correct Extinguisher Selection',
            contentEn:
              'Identify the fire classification before discharging an extinguisher. Use CO2 or Dry Chemical Powder on energized electrical panels. If the fire spreads beyond the incipient stage, activate the alarm and evacuate immediately.',
            contentHi:
              'अग्निशामक यंत्र का उपयोग करने से पहले आग की श्रेणी की पहचान करें। बिजली के पैनलों पर CO2 या ड्राई केमिकल पाउडर का उपयोग करें। यदि आग नियंत्रण से बाहर हो, तो तुरंत अलार्म बजाएं और बाहर निकलें।',
            contentSat:
              'ᱥᱮᱸᱜᱮᱞ ᱤᱬᱤᱡ ᱢᱟᱬᱟᱝ ᱨᱮ ᱥᱮᱸᱜᱮᱞ ᱨᱮᱭᱟᱜ ᱞᱮᱠᱟᱱ ᱧᱮᱞ ᱢᱮ। ᱥᱮᱸᱜᱮᱞ ᱢᱟᱨᱟᱝ ᱞᱮᱱᱠᱷᱟᱱ ᱞᱚᱜᱚᱱ ᱮᱞᱟᱨᱢ ᱵᱟᱡᱟᱣ ᱠᱟᱛᱮ ᱵᱟᱦᱨᱮ ᱚᱰᱚᱠᱚᱜ ᱢᱮ।',
          },
          {
            screenId: 'M01C02S05',
            order: 5,
            title: 'Executing the PASS Technique in AR',
            contentEn:
              'Pull the safety pin, Aim the nozzle at the base of the fire, Squeeze the lever steadily, and Sweep from side to side while keeping your evacuation route behind you.',
            contentHi:
              'सेफ्टी पिन खींचें, आग के आधार पर निशाना लगाएं, लीवर को धीरे से दबाएं, और निकास मार्ग को अपने पीछे रखते हुए बाएं से दाएं स्वीप करें।',
            contentSat:
              'ᱥᱮᱯᱷᱴᱤ ᱯᱤᱱ ᱚᱨ ᱢᱮ, ᱥᱮᱸᱜᱮᱞ ᱯᱷᱮᱰᱟᱛ ᱨᱮ ᱱᱤᱥᱟᱱᱟ ᱫᱚᱦᱚᱭ ᱢᱮ, ᱞᱤᱵᱷᱟᱨ ᱚᱛᱟᱭ ᱢᱮ ᱟᱨ ᱵᱟᱱᱟᱨ ᱯᱟᱦᱴᱟ ᱪᱷᱤᱴᱠᱟᱹᱣ ᱢᱮ।',
          },
        ],
      },
      {
        chapterId: 'M01C03',
        order: 3,
        title: 'Emergency Escape & Refuge Chambers',
        screens: [
          {
            screenId: 'M01C03S01',
            order: 1,
            title: 'Donning Self-Contained Self-Rescuers (SCSR)',
            contentEn:
              'At the first sign of smoke or carbon monoxide, immediately don your self-rescuer within 30 seconds and follow the reflective lifeline markers toward the fresh air base.',
            contentHi:
              'धुएं या कार्बन मोनोऑक्साइड के पहले संकेत पर, 30 सेकंड के भीतर अपना सेल्फ-रेस्क्यूअर पहनें और ताजी हवा के आधार की ओर लाइफलाइन का पालन करें।',
            contentSat:
              'ᱫᱷᱩᱸᱶᱟ ᱧᱮᱞ ᱞᱮᱱᱠᱷᱟᱱ ᱓᱐ ᱴᱤᱯᱤᱡ ᱵᱷᱤᱛᱨᱤ ᱨᱮ ᱥᱮᱞᱯᱷ-ᱨᱮᱥᱠᱭᱩᱚᱨ ᱦᱚᱨᱚᱜ ᱢᱮ ᱟᱨ ᱞᱟᱭᱤᱯᱷᱞᱟᱭᱤᱱ ᱯᱟᱸᱡᱟ ᱠᱟᱛᱮ ᱵᱟᱦᱨᱮ ᱪᱟᱞᱟᱜ ᱢᱮ।',
          },
        ],
      },
      {
        chapterId: 'M01C08',
        order: 8,
        title: 'Chapter 8: Formal Module Assessment (14 MCQs)',
        isAssessment: true,
        screens: [
          {
            screenId: 'M01C08S01',
            order: 1,
            title: 'Fire & Explosion Competency Assessment',
            contentEn:
              '14 formal situational multiple-choice questions covering chapters 1 through 7 with audio narration in Hindi and Santali.',
            contentHi: 'अध्याय 1 से 7 को कवर करने वाले 14 औपचारिक बहुविकल्पीय प्रश्न।',
            contentSat: '᱑᱔ ᱜᱚᱴᱟᱝ ᱵᱤᱰᱟᱹᱣ ᱠᱩᱠᱞᱤ ᱠᱚ।',
          },
        ],
      },
    ],
  },
  {
    moduleId: 'M02',
    order: 2,
    name: 'Gas Leak & Confined Space',
    shortName: 'Gas & Confined Space',
    arEnabled: true,
    arScenarios: [
      'Multi-gas detector alarm response',
      'Auxiliary ventilation duct inspection',
      'Confined space entry permit & attendant protocol',
    ],
    currentVersion: '1.4',
    avgScore: 79,
    passRate: 73,
    completedCount: 192,
    failedAttempts: 34,
    mostMissedQuestion: 'Q09: Maximum permissible Methane (CH4) concentration before power cutoff',
    workersNeedingRefresher: 18,
    barColor: 'blue',
    chapters: [
      {
        chapterId: 'M02C01',
        order: 1,
        title: 'Atmospheric Hazards & Gas Monitoring',
        screens: [
          {
            screenId: 'M02C01S03',
            order: 3,
            title: 'Methane & Carbon Monoxide Thresholds',
            contentEn:
              'Never enter an unventilated gallery or confined sump without testing oxygen (minimum 19.5%), methane, and carbon monoxide levels using a calibrated multi-gas detector.',
            contentHi:
              'कैलिब्रेटेड मल्टी-गैस डिटेक्टर का उपयोग करके ऑक्सीजन, मीथेन और कार्बन मोनोऑक्साइड के स्तर की जांच किए बिना कभी भी बंद स्थान में प्रवेश न करें।',
            contentSat:
              'ᱜᱮᱥ ᱰᱤᱴᱮᱠᱴᱚᱨ ᱛᱮ ᱚᱠᱥᱤᱡᱚᱱ ᱟᱨ ᱜᱮᱥ ᱵᱟᱝ ᱵᱤᱰᱟᱹᱣ ᱠᱟᱛᱮ ᱵᱚᱸᱫᱽ ᱡᱟᱭᱜᱟ ᱨᱮ ᱟᱞᱚ ᱵᱚᱞᱚᱱ ᱢᱮ।',
          },
        ],
      },
      {
        chapterId: 'M02C02',
        order: 2,
        title: 'Confined Space Entry & Ventilation',
        screens: [
          {
            screenId: 'M02C02S02',
            order: 2,
            title: 'Continuous Forced Ventilation & Standby Watch',
            contentEn:
              'Maintain continuous forced ventilation during all confined space entries and station a trained standby attendant outside with direct communication.',
            contentHi:
              'सभी सीमित स्थान प्रवेशों के दौरान निरंतर वेंटिलेशन बनाए रखें और बाहर सीधे संचार के साथ एक प्रशिक्षित अटेंडेंट तैनात रखें।',
            contentSat:
              'ᱵᱷᱤᱛᱨᱤ ᱠᱟᱹᱢᱤ ᱚᱠᱛᱚ ᱨᱮ ᱦᱚᱭ ᱪᱟᱞᱟᱣ ᱫᱚᱦᱚᱭ ᱢᱮ ᱟᱨ ᱵᱟᱦᱨᱮ ᱨᱮ ᱢᱤᱫ ᱦᱚᱲ ᱯᱟᱦᱨᱟ ᱫᱚᱦᱚᱭᱮ ᱢᱮ।',
          },
        ],
      },
    ],
  },
  {
    moduleId: 'M03',
    order: 3,
    name: 'Machinery Safety',
    shortName: 'Machinery Safety',
    arEnabled: true,
    arScenarios: [
      'Conveyor pinch-point & danger zone identification',
      'Machine guard verification before startup',
      'Lockout / Tagout (LOTO) & Emergency Stop isolation',
    ],
    currentVersion: '2.1',
    avgScore: 88,
    passRate: 86,
    completedCount: 205,
    failedAttempts: 14,
    mostMissedQuestion: 'Q11: Verification of zero residual hydraulic energy after LOTO lock application',
    workersNeedingRefresher: 8,
    barColor: 'emerald',
    chapters: [
      {
        chapterId: 'M03C01',
        order: 1,
        title: 'Machine Danger Zones & Fixed Guards',
        screens: [
          {
            screenId: 'M03C01S01',
            order: 1,
            title: 'Pre-Start Guard & Emergency Pull-Cord Check',
            contentEn:
              'Verify that all nip-point guards and emergency pull-cord switches along the belt conveyor are intact and responsive prior to sounding the startup horn.',
            contentHi:
              'स्टार्टअप हॉर्न बजाने से पहले सुनिश्चित करें कि बेल्ट कन्वेयर के सभी सुरक्षा गार्ड और आपातकालीन पुल-कॉर्ड स्विच सही स्थिति में हैं।',
            contentSat:
              'ᱢᱮᱥᱤᱱ ᱪᱟᱹᱞᱩ ᱢᱟᱬᱟᱝ ᱨᱮ ᱡᱚᱛᱚ ᱜᱟᱨᱰ ᱟᱨ ᱮᱢᱟᱨᱡᱮᱱᱥᱤ ᱥᱩᱭᱤᱪ ᱵᱷᱟᱹᱜᱤ ᱞᱮᱠᱟ ᱧᱮᱞ ᱢᱮ।',
          },
        ],
      },
      {
        chapterId: 'M03C02',
        order: 2,
        title: 'Lockout / Tagout (LOTO) Isolation Protocol',
        screens: [
          {
            screenId: 'M03C02S04',
            order: 4,
            title: 'Personal Padlock & Try-Out Verification',
            contentEn:
              'Before clearing a jammed crusher or conveyor, isolate the main breaker, attach your personal padlock and warning tag, and test the start button to confirm zero energy.',
            contentHi:
              'जाम हुए क्रशर या कन्वेयर को साफ करने से पहले, मुख्य ब्रेकर को बंद करें, अपना व्यक्तिगत पैडलॉक और टैग लगाएं, और शून्य ऊर्जा की पुष्टि करें।',
            contentSat:
              'ᱢᱮᱥᱤᱱ ᱡᱩᱛ ᱢᱟᱬᱟᱝ ᱨᱮ ᱢᱩᱬ ᱥᱩᱭᱤᱪ ᱵᱚᱸᱫᱽ ᱠᱟᱛᱮ ᱟᱢᱟᱜ ᱛᱟᱞᱟ ᱟᱨ ᱴᱮᱜᱽ ᱞᱟᱜᱟᱣ ᱢᱮ।',
          },
        ],
      },
    ],
  },
  {
    moduleId: 'M04',
    order: 4,
    name: 'PPE & Workplace Safety',
    shortName: 'PPE Safety',
    arEnabled: false,
    arScenarios: [],
    currentVersion: '3.0',
    avgScore: 81,
    passRate: 78,
    completedCount: 161,
    failedAttempts: 23,
    mostMissedQuestion: 'Q06: Selecting P3 particulate respirator cartridges for silica & mica dust',
    workersNeedingRefresher: 15,
    barColor: 'blue',
    chapters: [
      {
        chapterId: 'M04C01',
        order: 1,
        title: 'Mandatory Head, Foot & Respiratory Protection',
        screens: [
          {
            screenId: 'M04C01S02',
            order: 2,
            title: 'Inspecting Harness, Helmet & Cap Lamp',
            contentEn:
              'Wear your DGMS-approved safety helmet with chin strap, steel-toe boots, high-visibility vest, and dust respirator throughout your shift in active work zones.',
            contentHi:
              'सक्रिय कार्य क्षेत्रों में अपनी पूरी शिफ्ट के दौरान चिन स्ट्रैप के साथ DGMS-अनुमोदित सुरक्षा हेलमेट, स्टील-टो जूते, और डस्ट रेस्पिरेटर पहनें।',
            contentSat:
              'ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱨᱮ ᱥᱟᱨᱟ ᱜᱷᱟᱹᱲᱤᱡ ᱦᱮᱞᱢᱮᱴ, ᱥᱮᱯᱷᱴᱤ ᱡᱩᱛᱟᱹ ᱟᱨ ᱢᱟᱥᱠ ᱦᱚᱨᱚᱜ ᱢᱮ।',
          },
        ],
      },
    ],
  },
  {
    moduleId: 'M05',
    order: 5,
    name: 'Mine Laws & Workers\' Rights',
    shortName: 'Mine Laws & Rights',
    arEnabled: false,
    arScenarios: [],
    currentVersion: '1.8',
    avgScore: 74,
    passRate: 65,
    completedCount: 120,
    failedAttempts: 41,
    mostMissedQuestion: 'Q12: Statutory right to refuse unsafe work under Mines Act 1952 & DGMS regulations',
    workersNeedingRefresher: 24,
    barColor: 'amber',
    chapters: [
      {
        chapterId: 'M05C01',
        order: 1,
        title: 'Right to Safe Working Conditions & Hazard Reporting',
        screens: [
          {
            screenId: 'M05C01S01',
            order: 1,
            title: 'Reporting Imminent Danger Without Retaliation',
            contentEn:
              'Every worker has the statutory right and duty to halt work and alert the safety officer if roof support, ventilation, or machinery poses an imminent threat to life.',
            contentHi:
              'प्रत्येक श्रमिक को यह कानूनी अधिकार और कर्तव्य है कि यदि छत का सहारा, वेंटिलेशन या मशीनरी जीवन के लिए तत्काल खतरा पैदा करती है तो काम रोक दे और सुरक्षा अधिकारी को सूचित करे।',
            contentSat:
              'ᱡᱩᱫᱤ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱨᱮ ᱡᱤᱣᱤ ᱨᱮᱭᱟᱜ ᱵᱚᱛᱚᱨ ᱢᱮᱱᱟᱜᱼᱟ, ᱮᱱᱠᱷᱟᱱ ᱠᱟᱹᱢᱤ ᱛᱤᱸᱜᱩ ᱠᱟᱛᱮ ᱥᱮᱯᱷᱴᱤ ᱚᱯᱷᱤᱥᱚᱨ ᱞᱟᱹᱭ ᱟᱭ ᱨᱮᱭᱟᱜ ᱟᱹᱭᱫᱟᱹᱨᱤ ᱢᱮᱱᱟᱜᱼᱟ।',
          },
        ],
      },
    ],
  },
];

function buildWorkerModules(
  m1: { p: number; a: number | null },
  m2: { p: number; a: number | null },
  m3: { p: number; a: number | null },
  m4: { p: number; a: number | null },
  m5: { p: number; a: number | null }
): WorkerModuleProgress[] {
  const specs = [
    { id: 'M01', name: 'Fire & Explosion Response', ...m1, ar: m1.p === 100 },
    { id: 'M02', name: 'Gas Leak & Confined Space', ...m2, ar: m2.p === 100 },
    { id: 'M03', name: 'Machinery Safety', ...m3, ar: m3.p === 100 },
    { id: 'M04', name: 'PPE & Workplace Safety', ...m4, ar: false },
    { id: 'M05', name: "Mine Laws & Workers' Rights", ...m5, ar: false },
  ];
  return specs.map((s) => {
    const status: WorkerStatusType =
      s.p === 100 ? 'Completed' : s.p > 0 ? 'In Progress' : 'Pending';
    return {
      moduleId: s.id,
      moduleName: s.name,
      progressPercent: s.p,
      status,
      achievedPercent: status === 'Completed' ? s.a : null,
      arCompleted: s.ar,
      failedAttempts: status === 'Completed' ? 0 : s.p > 0 ? 1 : 0,
      lastUpdated: '14 Oct 2024',
    };
  });
}

export const INITIAL_WORKERS: Worker[] = [
  {
    workerId: 'W001',
    name: 'Rahul Kumar',
    email: 'rahul.kumar@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 92,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1540569014015-19a7be504e3a?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 92 },
      { p: 100, a: 86 },
      { p: 65, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '14 Oct 2024, 09:42 AM',
    createdAt: '01 Sep 2024',
  },
  {
    workerId: 'W002',
    name: 'Sita Devi',
    email: 'sita.devi@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 84,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1573497019940-1c28c88b4f3e?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 88 },
      { p: 100, a: 82 },
      { p: 100, a: 85 },
      { p: 100, a: 84 },
      { p: 100, a: 81 }
    ),
    lastSyncedAt: '14 Oct 2024, 09:15 AM',
    createdAt: '02 Sep 2024',
  },
  {
    workerId: 'W003',
    name: 'Amit Singh',
    email: 'amit.singh@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'Pending',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '13 Oct 2024, 04:20 PM',
    createdAt: '04 Sep 2024',
  },
  {
    workerId: 'W004',
    name: 'Pooja Kumari',
    email: 'pooja.kumari@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'In Progress',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1580489944761-15a19d654956?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 89 },
      { p: 70, a: null },
      { p: 40, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '14 Oct 2024, 08:50 AM',
    createdAt: '05 Sep 2024',
  },
  {
    workerId: 'W005',
    name: 'Ramesh Tudu',
    email: 'ramesh.tudu@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 78,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 80 },
      { p: 100, a: 76 },
      { p: 100, a: 82 },
      { p: 100, a: 77 },
      { p: 100, a: 75 }
    ),
    lastSyncedAt: '14 Oct 2024, 07:30 AM',
    createdAt: '06 Sep 2024',
  },
  {
    workerId: 'W006',
    name: 'Anil Paswan',
    email: 'anil.paswan@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'Pending',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '12 Oct 2024, 06:10 PM',
    createdAt: '08 Sep 2024',
  },
  {
    workerId: 'W007',
    name: 'Meena Hansda',
    email: 'meena.hansda@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 88,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 91 },
      { p: 100, a: 85 },
      { p: 100, a: 90 },
      { p: 100, a: 88 },
      { p: 100, a: 86 }
    ),
    lastSyncedAt: '14 Oct 2024, 10:05 AM',
    createdAt: '09 Sep 2024',
  },
  {
    workerId: 'W008',
    name: 'Vikram Singh',
    email: 'vikram.singh@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'In Progress',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 84 },
      { p: 100, a: 79 },
      { p: 50, a: null },
      { p: 20, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '13 Oct 2024, 05:40 PM',
    createdAt: '10 Sep 2024',
  },
  {
    workerId: 'W009',
    name: 'Lalita Murmu',
    email: 'lalita.murmu@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Pending',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '11 Oct 2024, 02:15 PM',
    createdAt: '11 Sep 2024',
  },
  {
    workerId: 'W010',
    name: 'Suresh Yadav',
    email: 'suresh.yadav@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 81,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 83 },
      { p: 100, a: 80 },
      { p: 100, a: 84 },
      { p: 100, a: 79 },
      { p: 100, a: 79 }
    ),
    lastSyncedAt: '14 Oct 2024, 11:00 AM',
    createdAt: '12 Sep 2024',
  },
  {
    workerId: 'W011',
    name: 'Birsa Soren',
    email: 'birsa.soren@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 90,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 94 },
      { p: 100, a: 88 },
      { p: 100, a: 91 },
      { p: 100, a: 89 },
      { p: 100, a: 88 }
    ),
    lastSyncedAt: '14 Oct 2024, 10:12 AM',
    createdAt: '13 Sep 2024',
  },
  {
    workerId: 'W012',
    name: 'Kavita Mahto',
    email: 'kavita.mahto@minea.com',
    language: 'Hindi',
    sector: 'Steel',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'In Progress',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 85 },
      { p: 80, a: null },
      { p: 30, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '14 Oct 2024, 08:20 AM',
    createdAt: '14 Sep 2024',
  },
  {
    workerId: 'W013',
    name: 'Manoj Hembram',
    email: 'manoj.hembram@minea.com',
    language: 'Santali',
    sector: 'Mica',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 86,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 87 },
      { p: 100, a: 84 },
      { p: 100, a: 89 },
      { p: 100, a: 86 },
      { p: 100, a: 84 }
    ),
    lastSyncedAt: '14 Oct 2024, 07:45 AM',
    createdAt: '15 Sep 2024',
  },
  {
    workerId: 'W014',
    name: 'Deepak Oraon',
    email: 'deepak.oraon@minea.com',
    language: 'Hindi',
    sector: 'Mining',
    workArea: 'Underground',
    siteId: 'Mine A',
    status: 'Pending',
    completionPercent: null,
    verificationStatus: 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null },
      { p: 0, a: null }
    ),
    lastSyncedAt: '10 Oct 2024, 03:00 PM',
    createdAt: '16 Sep 2024',
  },
  {
    workerId: 'W015',
    name: 'Sunita Kisku',
    email: 'sunita.kisku@minea.com',
    language: 'Santali',
    sector: 'Mining',
    workArea: 'Surface',
    siteId: 'Mine A',
    status: 'Completed',
    completionPercent: 94,
    verificationStatus: 'Verified',
    avatarUrl:
      'https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=240&auto=format&fit=crop&q=80',
    modules: buildWorkerModules(
      { p: 100, a: 96 },
      { p: 100, a: 92 },
      { p: 100, a: 95 },
      { p: 100, a: 94 },
      { p: 100, a: 93 }
    ),
    lastSyncedAt: '14 Oct 2024, 11:30 AM',
    createdAt: '18 Sep 2024',
  },
];

export const INITIAL_CHANGE_REQUESTS: ContentChangeRequest[] = [
  {
    requestId: 'CR-2024-001',
    moduleId: 'M01',
    moduleName: 'Fire & Explosion Response',
    chapterId: 'M01C02',
    chapterName: 'Fire Extinguisher Use',
    screenId: 'M01C02S04',
    screenName: 'Correct Extinguisher Selection',
    requestedBy: 'adminA',
    requestedByName: 'Admin A',
    siteId: 'Mine A',
    industryScope: 'Mining',
    description:
      'Change the instruction to tell workers to evacuate immediately if the fire is too large to control.',
    currentContent:
      'Identify the fire classification before discharging an extinguisher. Use CO2 or Dry Chemical Powder on energized electrical panels.',
    proposedContent:
      'If the fire cannot be safely controlled at its initial stage, immediately activate the alarm and evacuate through the designated exit route. Do not attempt to fight a large fire. Ensure all workers move to the safe assembly point.',
    proposedContentHi:
      'यदि आग को प्रारंभिक चरण में सुरक्षित रूप से नियंत्रित नहीं किया जा सकता है, तो तुरंत अलार्म सक्रिय करें और निर्धारित निकास मार्ग से बाहर निकलें। बड़ी आग से लड़ने का प्रयास न करें। सुनिश्चित करें कि सभी श्रमिक सुरक्षित असेंबली पॉइंट पर जाएं।',
    proposedContentSat:
      'ᱡᱩᱫᱤ ᱥᱮᱸᱜᱮᱞ ᱮᱛᱚᱦᱚᱵ ᱨᱮ ᱵᱟᱝ ᱠᱟᱵᱩ ᱫᱟᱲᱮᱭᱟᱜᱼᱟ, ᱞᱚᱜᱚᱱ ᱮᱞᱟᱨᱢ ᱵᱟᱡᱟᱣ ᱢᱮ ᱟᱨ ᱵᱟᱦᱨᱮ ᱚᱰᱚᱠᱚᱜ ᱦᱚᱨ ᱛᱮ ᱵᱟᱦᱨᱮ ᱪᱟᱞᱟᱜ ᱢᱮ। ᱢᱟᱨᱟᱝ ᱥᱮᱸᱜᱮᱞ ᱥᱟᱶ ᱟᱞᱚ ᱞᱟᱹᱲᱦᱟᱹᱭ ᱢᱮ।',
    aiDraftUsed: true,
    adminReviewed: true,
    approvalRequired: true,
    approvalGroup: 'Same industry/content scope (Mining Safety Peer Group)',
    requiredApprovals: 'ALL',
    approvals: [
      {
        reviewerId: 'adminA',
        reviewerName: 'Admin A',
        siteName: 'Mine A',
        avatarUrl: ADMIN_PERSONAS[0].avatarUrl,
        status: 'approved',
        timestamp: '12 Oct 2024',
      },
      {
        reviewerId: 'adminB',
        reviewerName: 'Admin B',
        siteName: 'Mine B',
        avatarUrl: ADMIN_PERSONAS[1].avatarUrl,
        status: 'approved',
        timestamp: '13 Oct 2024',
      },
      {
        reviewerId: 'adminC',
        reviewerName: 'Admin C',
        siteName: 'Mine C',
        avatarUrl: ADMIN_PERSONAS[2].avatarUrl,
        status: 'pending',
        timestamp: null,
      },
    ],
    status: 'PARTIALLY_APPROVED',
    baseVersion: '2.0',
    proposedVersion: '2.1',
    createdAt: '12 Oct 2024',
    createdAtMs: 1728720000000,
    publishedAt: null,
  },
  {
    requestId: 'CR-2024-002',
    moduleId: 'M02',
    moduleName: 'Gas Leak & Confined Space',
    chapterId: 'M02C01',
    chapterName: 'Atmospheric Hazards & Gas Monitoring',
    screenId: 'M02C01S03',
    screenName: 'Methane & Carbon Monoxide Thresholds',
    requestedBy: 'adminA',
    requestedByName: 'Admin A',
    siteId: 'Mine A',
    industryScope: 'Mining',
    description:
      'Add mandatory 19.5% oxygen check and audible alarm confirmation before descending into sump shafts.',
    currentContent:
      'Test methane and carbon monoxide levels before entering confined underground galleries.',
    proposedContent:
      'Verify oxygen levels are at least 19.5% and methane is below 0.5% using a calibrated multi-gas detector before descending into any confined sump or return airway.',
    aiDraftUsed: false,
    adminReviewed: true,
    approvalRequired: true,
    approvalGroup: 'Same industry/content scope (Mining Safety Peer Group)',
    requiredApprovals: 'ALL',
    approvals: [
      {
        reviewerId: 'adminA',
        reviewerName: 'Admin A',
        siteName: 'Mine A',
        avatarUrl: ADMIN_PERSONAS[0].avatarUrl,
        status: 'approved',
        timestamp: '14 Oct 2024',
      },
      {
        reviewerId: 'adminB',
        reviewerName: 'Admin B',
        siteName: 'Mine B',
        avatarUrl: ADMIN_PERSONAS[1].avatarUrl,
        status: 'pending',
        timestamp: null,
      },
      {
        reviewerId: 'adminC',
        reviewerName: 'Admin C',
        siteName: 'Mine C',
        avatarUrl: ADMIN_PERSONAS[2].avatarUrl,
        status: 'pending',
        timestamp: null,
      },
    ],
    status: 'PARTIALLY_APPROVED',
    baseVersion: '1.3',
    proposedVersion: '1.4',
    createdAt: '14 Oct 2024',
    createdAtMs: 1728892800000,
    publishedAt: null,
  },
  {
    requestId: 'CR-2024-003',
    moduleId: 'M03',
    moduleName: 'Machinery Safety',
    chapterId: 'M03C02',
    chapterName: 'Lockout / Tagout (LOTO) Isolation Protocol',
    screenId: 'M03C02S04',
    screenName: 'Personal Padlock & Try-Out Verification',
    requestedBy: 'adminA',
    requestedByName: 'Admin A',
    siteId: 'Mine A',
    industryScope: 'Mining',
    description:
      'Require personal padlock + test-start tryout verification before clearing jammed conveyor belts.',
    currentContent: 'Switch off the main isolator before performing conveyor maintenance.',
    proposedContent:
      'Before clearing a jammed crusher or conveyor, isolate the main breaker, attach your personal padlock and warning tag, and test the start button to confirm zero energy.',
    aiDraftUsed: true,
    adminReviewed: true,
    approvalRequired: true,
    approvalGroup: 'Same industry/content scope (Mining Safety Peer Group)',
    requiredApprovals: 'ALL',
    approvals: [
      {
        reviewerId: 'adminA',
        reviewerName: 'Admin A',
        siteName: 'Mine A',
        avatarUrl: ADMIN_PERSONAS[0].avatarUrl,
        status: 'approved',
        timestamp: '12 Oct 2024',
      },
      {
        reviewerId: 'adminB',
        reviewerName: 'Admin B',
        siteName: 'Mine B',
        avatarUrl: ADMIN_PERSONAS[1].avatarUrl,
        status: 'approved',
        timestamp: '13 Oct 2024',
      },
      {
        reviewerId: 'adminC',
        reviewerName: 'Admin C',
        siteName: 'Mine C',
        avatarUrl: ADMIN_PERSONAS[2].avatarUrl,
        status: 'approved',
        timestamp: '14 Oct 2024',
      },
    ],
    status: 'PUBLISHED',
    baseVersion: '2.0',
    proposedVersion: '2.1',
    createdAt: '12 Oct 2024',
    createdAtMs: 1728710000000,
    publishedAt: '14 Oct 2024',
  },
];

export const INITIAL_CERTIFICATES: CertificateRecord[] = [
  {
    certificateId: 'KSH-CERT-2024-W001',
    workerId: 'W001',
    workerName: 'Rahul Kumar',
    email: 'rahul.kumar@minea.com',
    siteId: 'Mine A',
    sector: 'Mining',
    workArea: 'Underground',
    language: 'Hindi',
    courseName: 'Fire & Explosion Response',
    score: 92,
    issuedAt: '12 Oct 2024',
    qrPayload:
      'KAUSHAL_VERIFY|KSH-CERT-2024-W001|W001|Rahul Kumar|Mine A|Fire & Explosion Response|92%',
    status: 'VERIFIED',
  },
  {
    certificateId: 'KSH-CERT-2024-W002',
    workerId: 'W002',
    workerName: 'Sita Devi',
    email: 'sita.devi@minea.com',
    siteId: 'Mine A',
    sector: 'Mining',
    workArea: 'Underground',
    language: 'Santali',
    courseName: 'Gas Leak & Confined Space',
    score: 84,
    issuedAt: '13 Oct 2024',
    qrPayload:
      'KAUSHAL_VERIFY|KSH-CERT-2024-W002|W002|Sita Devi|Mine A|Gas Leak & Confined Space|84%',
    status: 'VERIFIED',
  },
];

export const INITIAL_AUDIT_LOGS: AuditLogEntry[] = [
  {
    logId: 'LOG-001',
    actorId: 'adminA',
    actorName: 'Admin A',
    action: 'Edit Requested',
    moduleId: 'M01',
    moduleName: 'Fire Safety',
    version: '2.1',
    timestamp: '12 Oct 2024',
    createdAtMs: 1728720000000,
    targetId: 'CR-2024-001',
    details: 'Proposed immediate evacuation wording for Chapter 2, Screen 4.',
  },
  {
    logId: 'LOG-002',
    actorId: 'adminB',
    actorName: 'Admin B',
    action: 'Approved',
    moduleId: 'M01',
    moduleName: 'Fire Safety',
    version: '2.1',
    timestamp: '13 Oct 2024',
    createdAtMs: 1728806400000,
    targetId: 'CR-2024-001',
    details: 'Peer review approved by Admin B (Same Industry Scope: Mining).',
  },
  {
    logId: 'LOG-003',
    actorId: 'adminC',
    actorName: 'Admin C',
    action: 'Rejected',
    moduleId: 'M02',
    moduleName: 'Gas Safety',
    version: '1.4',
    timestamp: '13 Oct 2024',
    createdAtMs: 1728820000000,
    targetId: 'CR-2024-000',
    details: 'Requested explicit 19.5% oxygen threshold value in Santali translation.',
  },
  {
    logId: 'LOG-004',
    actorId: 'adminA',
    actorName: 'Admin A',
    action: 'Published',
    moduleId: 'M03',
    moduleName: 'Machinery Safety',
    version: '2.1',
    timestamp: '14 Oct 2024',
    createdAtMs: 1728892800000,
    targetId: 'CR-2024-003',
    details: 'All 3/3 peer approvals complete. Version 2.1 automatically published.',
  },
  {
    logId: 'LOG-005',
    actorId: 'adminA',
    actorName: 'Admin A',
    action: 'Edit Requested',
    moduleId: 'M04',
    moduleName: 'PPE Safety',
    version: '3.0',
    timestamp: '15 Oct 2024',
    createdAtMs: 1728979200000,
    targetId: 'CR-2024-002',
    details: 'Updated P3 particulate respirator cartridge inspection step.',
  },
];

export const INITIAL_NOTIFICATIONS: NotificationItem[] = [
  {
    notificationId: 'NOTIF-001',
    category: 'CONTENT_EDIT_REQUEST',
    title: 'New Content Edit Request',
    message: 'Fire Safety module requires your review (Version 2.1 – 2/3 Approved).',
    timestamp: '2 hours ago',
    createdAtMs: Date.now() - 2 * 3600 * 1000,
    read: false,
    targetId: 'CR-2024-001',
    moduleName: 'Fire & Explosion Response',
  },
  {
    notificationId: 'NOTIF-002',
    category: 'TRAINING_UPDATE',
    title: 'Training Update Published',
    message: 'Machinery Safety – Version 2.1 has been published after 3/3 peer approvals.',
    timestamp: '5 hours ago',
    createdAtMs: Date.now() - 5 * 3600 * 1000,
    read: false,
    targetId: 'M03',
    moduleName: 'Machinery Safety',
  },
  {
    notificationId: 'NOTIF-003',
    category: 'SYSTEM_NOTIFICATION',
    title: 'System Notification',
    message: 'Scheduled offline-sync gateway maintenance on 15 Oct 2024, 10:00 PM.',
    timestamp: '1 day ago',
    createdAtMs: Date.now() - 24 * 3600 * 1000,
    read: false,
  },
  {
    notificationId: 'NOTIF-004',
    category: 'CONTENT_EDIT_REQUEST',
    title: 'New Content Edit Request',
    message: 'PPE Safety module requires your review.',
    timestamp: '2 days ago',
    createdAtMs: Date.now() - 48 * 3600 * 1000,
    read: false,
    targetId: 'CR-2024-002',
    moduleName: 'PPE & Workplace Safety',
  },
  {
    notificationId: 'NOTIF-005',
    category: 'TRAINING_UPDATE',
    title: 'Offline Worker Batch Synchronized',
    message: '42 underground worker assessment attempts synced from Mine A field tablets.',
    timestamp: '2 days ago',
    createdAtMs: Date.now() - 52 * 3600 * 1000,
    read: true,
  },
  {
    notificationId: 'NOTIF-006',
    category: 'CONTENT_EDIT_REQUEST',
    title: 'Peer Approval Received',
    message: 'Admin B approved Fire & Explosion Response Version 2.1 change request.',
    timestamp: '3 days ago',
    createdAtMs: Date.now() - 72 * 3600 * 1000,
    read: true,
    targetId: 'CR-2024-001',
  },
  {
    notificationId: 'NOTIF-007',
    category: 'TRAINING_UPDATE',
    title: 'AR Practical Scenario Telemetry Updated',
    message: 'Fire Extinguisher PASS AR drill achieved 89% first-attempt completion at Mine A.',
    timestamp: '4 days ago',
    createdAtMs: Date.now() - 96 * 3600 * 1000,
    read: true,
  },
  {
    notificationId: 'NOTIF-008',
    category: 'SYSTEM_NOTIFICATION',
    title: 'Santali Audio Pack v2.4 Verified',
    message: 'All 350 learning screens verified for offline Santali & Hindi voice narration.',
    timestamp: '5 days ago',
    createdAtMs: Date.now() - 120 * 3600 * 1000,
    read: true,
  },
];

export const INITIAL_SETTINGS: AdminSetting = {
  settingId: 'main_config',
  siteId: 'Mine A',
  siteName: 'Mine A — Jharia Underground & Surface Division',
  sector: 'Mining',
  trainingUpdates: true,
  contentEditRequests: true,
  systemNotifications: true,
  requirePeerApprovalDefault: true,
  totalLearners: 248,
  totalWorkers: 1032,
  pendingVerification: 37,
  publishedChanges: 18,
  trainingCompletedCount: 168,
  trainingInProgressCount: 62,
  trainingNewlyStartedCount: 18,
  certificatesIssuedSite: 892,
  platformTotalParticipants: 12480,
  platformModuleCompletion: 68,
  platformAssessmentPassRate: 76,
  platformCertificatesIssued: 10240,
  monthlyParticipation: [
    { month: 'Jan', fullMonth: 'Jan 2024', participants: 1250 },
    { month: 'Feb', fullMonth: 'Feb 2024', participants: 1580 },
    { month: 'Mar', fullMonth: 'Mar 2024', participants: 2340 },
    { month: 'Apr', fullMonth: 'Apr 2024', participants: 2190 },
    { month: 'May', fullMonth: 'May 2024', participants: 2560 },
    { month: 'Jun', fullMonth: 'Jun 2024', participants: 2680 },
    { month: 'Jul', fullMonth: 'Jul 2024', participants: 2940 },
    { month: 'Aug', fullMonth: 'Aug 2024', participants: 2810 },
    { month: 'Sep', fullMonth: 'Sep 2024', participants: 3290 },
    { month: 'Oct', fullMonth: 'Oct 2024', participants: 3420 },
    { month: 'Nov', fullMonth: 'Nov 2024', participants: 3680 },
    { month: 'Dec', fullMonth: 'Dec 2024', participants: 3890 },
  ],
  sectorDistribution: [
    { sector: 'Mining', percentage: 62, participants: 7738, color: '#2563eb' },
    { sector: 'Steel', percentage: 25, participants: 3120, color: '#f59e0b' },
    { sector: 'Mica', percentage: 13, participants: 1622, color: '#10b981' },
  ],
};

export const INITIAL_VERSIONS: ContentVersion[] = [
  {
    versionId: 'VER-M01-2.0',
    moduleId: 'M01',
    moduleName: 'Fire & Explosion Response',
    chapterId: 'M01C02',
    chapterName: 'Fire Extinguisher Use',
    screenId: 'M01C02S04',
    screenName: 'Correct Extinguisher Selection',
    versionNumber: '2.0',
    content:
      'Identify the fire classification before discharging an extinguisher. Use CO2 or Dry Chemical Powder on energized electrical panels.',
    authorId: 'adminA',
    authorName: 'Admin A',
    requestId: 'CR-INIT-01',
    approvalSummary: '3/3 Approved (Admin A, Admin B, Admin C)',
    publishedAt: '01 Sep 2024',
    isCurrent: true,
  },
  {
    versionId: 'VER-M03-2.1',
    moduleId: 'M03',
    moduleName: 'Machinery Safety',
    chapterId: 'M03C02',
    chapterName: 'Lockout / Tagout (LOTO) Isolation Protocol',
    screenId: 'M03C02S04',
    screenName: 'Personal Padlock & Try-Out Verification',
    versionNumber: '2.1',
    content:
      'Before clearing a jammed crusher or conveyor, isolate the main breaker, attach your personal padlock and warning tag, and test the start button to confirm zero energy.',
    authorId: 'adminA',
    authorName: 'Admin A',
    requestId: 'CR-2024-003',
    approvalSummary: '3/3 Approved (Admin A, Admin B, Admin C)',
    publishedAt: '14 Oct 2024',
    isCurrent: true,
  },
];

let initializationPromise: Promise<void> | null = null;

export async function ensureDatabaseInitialized(): Promise<void> {
  if (initializationPromise) {
    return initializationPromise;
  }

  initializationPromise = (async () => {
    try {
      const settingsRef = doc(db, 'settings', 'main_config');
      const snap = await getDoc(settingsRef);

      if (snap.exists()) {
        await updateDoc(settingsRef, {
          totalLearners: 4,
          totalWorkers: 6,
          pendingVerification: 2,
          publishedChanges: 3,
          trainingCompletedCount: 2,
          trainingInProgressCount: 1,
          trainingNewlyStartedCount: 1,
          certificatesIssuedSite: 2,
        });

        return;
      }

      const batch = writeBatch(db);

      // 1. Settings
      batch.set(settingsRef, INITIAL_SETTINGS);

      // 2. Modules
      for (const mod of INITIAL_MODULES) {
        batch.set(doc(db, 'modules', mod.moduleId), mod);
      }

      // 3. Workers
      for (const worker of INITIAL_WORKERS) {
        batch.set(doc(db, 'workers', worker.workerId), worker);
      }

      // 4. Content Change Requests
      for (const req of INITIAL_CHANGE_REQUESTS) {
        batch.set(doc(db, 'contentChangeRequests', req.requestId), req);
      }

      // 5. Content Versions
      for (const ver of INITIAL_VERSIONS) {
        batch.set(doc(db, 'contentVersions', ver.versionId), ver);
      }

      // 6. Certificates
      for (const cert of INITIAL_CERTIFICATES) {
        batch.set(doc(db, 'certificates', cert.certificateId), cert);
      }

      // 7. Audit Logs
      for (const log of INITIAL_AUDIT_LOGS) {
        batch.set(doc(db, 'auditLogs', log.logId), log);
      }

      // 8. Notifications
      for (const notif of INITIAL_NOTIFICATIONS) {
        batch.set(doc(db, 'notifications', notif.notificationId), notif);
      }

      await batch.commit();
    } catch (error) {
      handleFirestoreError(error, OperationType.WRITE, 'initial_seed');
    }
  })();

  return initializationPromise;
}

// Add a new worker to Firestore and update counters
export async function createWorkerInDb(input: {
  name: string;
  workerId: string;
  email: string;
  language: LanguageType;
  sector: SectorType;
  workArea: WorkAreaType;
  siteId: string;
  initialStatus: WorkerStatusType;
  achievedScore?: number;
}): Promise<Worker> {
  const cleanId = input.workerId.trim().toUpperCase();
  const isCompleted = input.initialStatus === 'Completed';
  const isInProgress = input.initialStatus === 'In Progress';
  const score = isCompleted ? input.achievedScore || 85 : null;

  const modules = buildWorkerModules(
    isCompleted
      ? { p: 100, a: score }
      : isInProgress
        ? { p: 100, a: 86 }
        : { p: 0, a: null },
    isCompleted
      ? { p: 100, a: score }
      : isInProgress
        ? { p: 60, a: null }
        : { p: 0, a: null },
    isCompleted ? { p: 100, a: score } : { p: 0, a: null },
    isCompleted ? { p: 100, a: score } : { p: 0, a: null },
    isCompleted ? { p: 100, a: score } : { p: 0, a: null }
  );

  const nowStr = new Date().toLocaleDateString('en-GB', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  });

  const newWorker: Worker = {
    workerId: cleanId,
    name: input.name.trim(),
    email: input.email.trim() || `${input.name.toLowerCase().replace(/\s+/g, '.')}@minea.com`,
    language: input.language,
    sector: input.sector,
    workArea: input.workArea,
    siteId: input.siteId || 'Mine A',
    status: input.initialStatus,
    completionPercent: isCompleted ? score : null,
    verificationStatus: isCompleted ? 'Verified' : 'Pending',
    avatarUrl:
      'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=240&auto=format&fit=crop&q=80',
    modules,
    lastSyncedAt: `${nowStr}, Just now`,
    createdAt: nowStr,
  };

  try {
    const batch = writeBatch(db);
    batch.set(doc(db, 'workers', cleanId), newWorker);

    const settingsRef = doc(db, 'settings', 'main_config');
    const settingsSnap = await getDoc(settingsRef);
    if (settingsSnap.exists()) {
      const s = settingsSnap.data() as AdminSetting;
      batch.update(settingsRef, {
        totalWorkers: (s.totalWorkers || 1032) + 1,
        totalLearners:
          isCompleted || isInProgress ? (s.totalLearners || 248) + 1 : s.totalLearners,
        pendingVerification: !isCompleted
          ? (s.pendingVerification || 37) + 1
          : s.pendingVerification,
        trainingCompletedCount: isCompleted
          ? (s.trainingCompletedCount || 168) + 1
          : s.trainingCompletedCount,
        trainingInProgressCount: isInProgress
          ? (s.trainingInProgressCount || 62) + 1
          : s.trainingInProgressCount,
        trainingNewlyStartedCount:
          input.initialStatus === 'Pending'
            ? (s.trainingNewlyStartedCount || 18) + 1
            : s.trainingNewlyStartedCount,
        certificatesIssuedSite: isCompleted
          ? (s.certificatesIssuedSite || 892) + 1
          : s.certificatesIssuedSite,
        platformTotalParticipants: (s.platformTotalParticipants || 12480) + 1,
        platformCertificatesIssued: isCompleted
          ? (s.platformCertificatesIssued || 10240) + 1
          : s.platformCertificatesIssued,
      });
    }

    if (isCompleted && score !== null) {
      const certId = `KSH-CERT-2024-${cleanId}`;
      const cert: CertificateRecord = {
        certificateId: certId,
        workerId: cleanId,
        workerName: newWorker.name,
        email: newWorker.email,
        siteId: newWorker.siteId,
        sector: newWorker.sector,
        workArea: newWorker.workArea,
        language: newWorker.language,
        courseName: 'Full 5-Module Industrial Safety Curriculum',
        score,
        issuedAt: nowStr,
        qrPayload: `KAUSHAL_VERIFY|${certId}|${cleanId}|${newWorker.name}|${newWorker.siteId}|${score}%`,
        status: 'VERIFIED',
      };
      batch.set(doc(db, 'certificates', certId), cert);
    }

    await batch.commit();
    return newWorker;
  } catch (error) {
    handleFirestoreError(error, OperationType.WRITE, `workers/${cleanId}`);
    throw error;
  }
}

// Update a worker's module progress and achieved assessment percentage in Firestore
export async function updateWorkerModuleProgressInDb(
  worker: Worker,
  moduleId: string,
  progressPercent: number,
  achievedPercent: number | null
): Promise<void> {
  try {
    const clampedProgress = Math.max(0, Math.min(100, Math.round(progressPercent)));
    const modStatus: WorkerStatusType =
      clampedProgress === 100
        ? 'Completed'
        : clampedProgress > 0
          ? 'In Progress'
          : 'Pending';
    const validAchieved =
      modStatus === 'Completed'
        ? Math.max(0, Math.min(100, Math.round(achievedPercent ?? 85)))
        : null;

    const nowStr = new Date().toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });

    const updatedModules = worker.modules.map((m) =>
      m.moduleId === moduleId
        ? {
          ...m,
          progressPercent: clampedProgress,
          status: modStatus,
          achievedPercent: validAchieved,
          arCompleted: clampedProgress === 100 ? true : m.arCompleted,
          lastUpdated: nowStr,
        }
        : m
    );

    const completedModules = updatedModules.filter((m) => m.status === 'Completed');
    const anyStarted = updatedModules.some((m) => m.progressPercent > 0);

    // Overall worker status & completion rule (PRD Section 12.2 & 13.2)
    let overallStatus: WorkerStatusType = 'Pending';
    let overallCompletionPercent: number | null = null;

    if (completedModules.length === updatedModules.length) {
      overallStatus = 'Completed';
      const sum = completedModules.reduce((acc, m) => acc + (m.achievedPercent || 85), 0);
      overallCompletionPercent = Math.round(sum / completedModules.length);
    } else if (anyStarted) {
      overallStatus = 'In Progress';
      overallCompletionPercent = null; // Pending/In Progress shows '—' in Worker table
    }

    const batch = writeBatch(db);
    const workerRef = doc(db, 'workers', worker.workerId);
    batch.update(workerRef, {
      modules: updatedModules,
      status: overallStatus,
      completionPercent: overallCompletionPercent,
      verificationStatus: overallStatus === 'Completed' ? 'Verified' : worker.verificationStatus,
      lastSyncedAt: `${nowStr}, Just now`,
    });

    // If newly completed, issue/update certificate
    if (overallStatus === 'Completed' && overallCompletionPercent !== null) {
      const certId = `KSH-CERT-2024-${worker.workerId}`;
      const cert: CertificateRecord = {
        certificateId: certId,
        workerId: worker.workerId,
        workerName: worker.name,
        email: worker.email,
        siteId: worker.siteId,
        sector: worker.sector,
        workArea: worker.workArea,
        language: worker.language,
        courseName: 'Full 5-Module Industrial Safety Curriculum',
        score: overallCompletionPercent,
        issuedAt: nowStr,
        qrPayload: `KAUSHAL_VERIFY|${certId}|${worker.workerId}|${worker.name}|${worker.siteId}|${overallCompletionPercent}%`,
        status: 'VERIFIED',
      };
      batch.set(doc(db, 'certificates', certId), cert);
    }

    await batch.commit();
  } catch (error) {
    handleFirestoreError(error, OperationType.UPDATE, `workers/${worker.workerId}`);
    throw error;
  }
}

// Synchronize offline field worker progress (PRD Section 19 & 24)
export async function syncOfflineWorkerBatchInDb(): Promise<{ syncedWorkers: number }> {
  try {
    const workersSnap = await getDocs(collection(db, 'workers'));
    const batch = writeBatch(db);
    let syncedCount = 0;

    const nowStr = new Date().toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });

    workersSnap.docs.forEach((docSnap) => {
      const w = docSnap.data() as Worker;
      if (w.status !== 'Completed' && syncedCount < 3) {
        syncedCount++;
        const updatedModules = w.modules.map((m, idx) => {
          if (m.status === 'Completed') return m;
          if (idx === 0 || m.progressPercent >= 50) {
            return {
              ...m,
              progressPercent: 100,
              status: 'Completed' as WorkerStatusType,
              achievedPercent: 86,
              arCompleted: true,
              lastUpdated: nowStr,
            };
          }
          return {
            ...m,
            progressPercent: Math.min(90, m.progressPercent + 35),
            status: 'In Progress' as WorkerStatusType,
            achievedPercent: null,
            lastUpdated: nowStr,
          };
        });

        const allDone = updatedModules.every((m) => m.status === 'Completed');
        batch.update(docSnap.ref, {
          modules: updatedModules,
          status: allDone ? 'Completed' : 'In Progress',
          completionPercent: allDone ? 86 : null,
          lastSyncedAt: `${nowStr}, Offline Sync`,
        });
      }
    });

    const settingsRef = doc(db, 'settings', 'main_config');
    const settingsSnap = await getDoc(settingsRef);
    if (settingsSnap.exists()) {
      const s = settingsSnap.data() as AdminSetting;
      batch.update(settingsRef, {
        totalLearners: (s.totalLearners || 248) + syncedCount,
        trainingCompletedCount: (s.trainingCompletedCount || 168) + 1,
        trainingInProgressCount: Math.max(1, (s.trainingInProgressCount || 62) + 1),
        certificatesIssuedSite: (s.certificatesIssuedSite || 892) + 1,
      });

      if (s.trainingUpdates) {
        const notifId = `NOTIF-${Date.now()}`;
        const notif: NotificationItem = {
          notificationId: notifId,
          category: 'TRAINING_UPDATE',
          title: 'Offline Worker Data Synchronized',
          message: `${syncedCount || 3} worker devices synchronized offline AR & assessment progress from ${s.siteId}.`,
          timestamp: 'Just now',
          createdAtMs: Date.now(),
          read: false,
        };
        batch.set(doc(db, 'notifications', notifId), notif);
      }
    }

    await batch.commit();
    return { syncedWorkers: syncedCount || 3 };
  } catch (error) {
    handleFirestoreError(error, OperationType.WRITE, 'offline_sync');
    throw error;
  }
}

// Submit a new Content Change Request (PRD Section 10, 11, 22)
export async function submitContentChangeRequestInDb(input: {
  moduleId: string;
  moduleName: string;
  chapterId: string;
  chapterName: string;
  screenId: string;
  screenName: string;
  currentContent: string;
  description: string;
  proposedContent: string;
  proposedContentHi?: string;
  proposedContentSat?: string;
  aiDraftUsed: boolean;
  adminReviewed: boolean;
  approvalRequired: boolean;
  actor: AdminPersona;
  baseVersion: string;
}): Promise<ContentChangeRequest> {
  const reqId = `CR-2024-${Math.floor(100 + Math.random() * 899)}`;
  const versionParts = input.baseVersion.split('.');
  const nextMinor = parseInt(versionParts[1] || '0', 10) + 1;
  const proposedVersion = `${versionParts[0]}.${nextMinor}`;

  const nowDate = new Date().toLocaleDateString('en-GB', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  });

  const approvals = ADMIN_PERSONAS.map((persona) => ({
    reviewerId: persona.id,
    reviewerName: persona.name,
    siteName: persona.siteName,
    avatarUrl: persona.avatarUrl,
    status: (persona.id === input.actor.id || !input.approvalRequired
      ? 'approved'
      : 'pending') as 'approved' | 'rejected' | 'pending',
    timestamp: persona.id === input.actor.id || !input.approvalRequired ? nowDate : null,
  }));

  const status = input.approvalRequired ? 'PARTIALLY_APPROVED' : 'PUBLISHED';

  const newRequest: ContentChangeRequest = {
    requestId: reqId,
    moduleId: input.moduleId,
    moduleName: input.moduleName,
    chapterId: input.chapterId,
    chapterName: input.chapterName,
    screenId: input.screenId,
    screenName: input.screenName,
    requestedBy: input.actor.id,
    requestedByName: input.actor.name,
    siteId: input.actor.siteName,
    industryScope: input.actor.sector,
    description: input.description.trim(),
    currentContent: input.currentContent,
    proposedContent: input.proposedContent.trim(),
    proposedContentHi: input.proposedContentHi || '',
    proposedContentSat: input.proposedContentSat || '',
    aiDraftUsed: input.aiDraftUsed,
    adminReviewed: input.adminReviewed,
    approvalRequired: input.approvalRequired,
    approvalGroup: `Same industry/content scope (${input.actor.sector} Safety Peer Group)`,
    requiredApprovals: 'ALL',
    approvals,
    status,
    baseVersion: input.baseVersion,
    proposedVersion,
    createdAt: nowDate,
    createdAtMs: Date.now(),
    publishedAt: input.approvalRequired ? null : nowDate,
  };

  try {
    const batch = writeBatch(db);
    batch.set(doc(db, 'contentChangeRequests', reqId), newRequest);

    // Record Audit Log: Edit Requested
    const logId = `LOG-${Date.now()}`;
    const auditEntry: AuditLogEntry = {
      logId,
      actorId: input.actor.id,
      actorName: input.actor.name,
      action: input.approvalRequired ? 'Edit Requested' : 'Published',
      moduleId: input.moduleId,
      moduleName: input.moduleName,
      version: proposedVersion,
      timestamp: nowDate,
      createdAtMs: Date.now(),
      targetId: reqId,
      details: input.description.trim(),
    };
    batch.set(doc(db, 'auditLogs', logId), auditEntry);

    // Check notification settings before creating notification
    const settingsRef = doc(db, 'settings', 'main_config');
    const settingsSnap = await getDoc(settingsRef);
    const settings = settingsSnap.exists() ? (settingsSnap.data() as AdminSetting) : null;

    if (!input.approvalRequired) {
      // Immediately publish version if peer approval was turned OFF
      const verId = `VER-${input.moduleId}-${proposedVersion}-${Date.now()}`;
      const newVer: ContentVersion = {
        versionId: verId,
        moduleId: input.moduleId,
        moduleName: input.moduleName,
        chapterId: input.chapterId,
        chapterName: input.chapterName,
        screenId: input.screenId,
        screenName: input.screenName,
        versionNumber: proposedVersion,
        content: input.proposedContent.trim(),
        authorId: input.actor.id,
        authorName: input.actor.name,
        requestId: reqId,
        approvalSummary: 'Direct Admin Publish (Peer Approval OFF)',
        publishedAt: nowDate,
        isCurrent: true,
      };
      batch.set(doc(db, 'contentVersions', verId), newVer);
      batch.update(doc(db, 'modules', input.moduleId), {
        currentVersion: proposedVersion,
      });

      if (settings) {
        batch.update(settingsRef, {
          publishedChanges: (settings.publishedChanges || 18) + 1,
        });
      }
    }

    if (settings?.contentEditRequests) {
      const notifId = `NOTIF-${Date.now()}`;
      const notif: NotificationItem = {
        notificationId: notifId,
        category: input.approvalRequired ? 'CONTENT_EDIT_REQUEST' : 'TRAINING_UPDATE',
        title: input.approvalRequired ? 'New Content Edit Request' : 'Training Update Published',
        message: input.approvalRequired
          ? `${input.moduleName} (${input.screenName}) requires peer review for Version ${proposedVersion}.`
          : `${input.moduleName} – Version ${proposedVersion} has been published.`,
        timestamp: 'Just now',
        createdAtMs: Date.now(),
        read: false,
        targetId: reqId,
        moduleName: input.moduleName,
      };
      batch.set(doc(db, 'notifications', notifId), notif);
    }

    await batch.commit();
    return newRequest;
  } catch (error) {
    handleFirestoreError(error, OperationType.WRITE, `contentChangeRequests/${reqId}`);
    throw error;
  }
}

// Peer Reviewer Approve / Reject Action (PRD Section 3.2, 11, 13, 14)
export async function reviewContentChangeRequestInDb(
  request: ContentChangeRequest,
  reviewer: AdminPersona,
  decision: 'approved' | 'rejected'
): Promise<ContentChangeRequest> {
  const existingRecord = request.approvals.find((a) => a.reviewerId === reviewer.id);
  if (existingRecord && existingRecord.status === 'approved' && decision === 'approved') {
    throw new Error(`${reviewer.name} has already approved this change request.`);
  }

  const nowDate = new Date().toLocaleDateString('en-GB', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  });

  const updatedApprovals = request.approvals.map((a) =>
    a.reviewerId === reviewer.id
      ? { ...a, status: decision, timestamp: nowDate }
      : a
  );

  const allApproved = updatedApprovals.every((a) => a.status === 'approved');
  const anyRejected = updatedApprovals.some((a) => a.status === 'rejected');

  const nextStatus = anyRejected
    ? 'REJECTED'
    : allApproved
      ? 'PUBLISHED' // Automatic publication when ALL required peer approvals complete (TASK-ADM-122)
      : 'PARTIALLY_APPROVED';

  const updatedRequest: ContentChangeRequest = {
    ...request,
    approvals: updatedApprovals,
    status: nextStatus,
    publishedAt: allApproved ? nowDate : request.publishedAt || null,
  };

  try {
    const batch = writeBatch(db);
    batch.set(doc(db, 'contentChangeRequests', request.requestId), updatedRequest);

    // 1. Audit Log for Peer Approval or Rejection
    const approvalLogId = `LOG-${Date.now()}-1`;
    const approvalLog: AuditLogEntry = {
      logId: approvalLogId,
      actorId: reviewer.id,
      actorName: reviewer.name,
      action: decision === 'approved' ? 'Approved' : 'Rejected',
      moduleId: request.moduleId,
      moduleName: request.moduleName,
      version: request.proposedVersion,
      timestamp: nowDate,
      createdAtMs: Date.now(),
      targetId: request.requestId,
      details: `${reviewer.name} ${decision} change request ${request.requestId}.`,
    };
    batch.set(doc(db, 'auditLogs', approvalLogId), approvalLog);

    const settingsRef = doc(db, 'settings', 'main_config');
    const settingsSnap = await getDoc(settingsRef);
    const settings = settingsSnap.exists() ? (settingsSnap.data() as AdminSetting) : null;

    // 2. If all required peers approved -> Automatic Publication (APPROVED -> PUBLISHED)
    if (allApproved) {
      const pubLogId = `LOG-${Date.now()}-2`;
      const pubLog: AuditLogEntry = {
        logId: pubLogId,
        actorId: request.requestedBy,
        actorName: request.requestedByName,
        action: 'Published',
        moduleId: request.moduleId,
        moduleName: request.moduleName,
        version: request.proposedVersion,
        timestamp: nowDate,
        createdAtMs: Date.now() + 10,
        targetId: request.requestId,
        details: `All ${updatedApprovals.length}/${updatedApprovals.length} same-industry peer approvals received. Version ${request.proposedVersion} automatically published.`,
      };
      batch.set(doc(db, 'auditLogs', pubLogId), pubLog);

      // Create immutable ContentVersion
      const verId = `VER-${request.moduleId}-${request.proposedVersion}-${Date.now()}`;
      const newVer: ContentVersion = {
        versionId: verId,
        moduleId: request.moduleId,
        moduleName: request.moduleName,
        chapterId: request.chapterId,
        chapterName: request.chapterName,
        screenId: request.screenId,
        screenName: request.screenName,
        versionNumber: request.proposedVersion,
        content: request.proposedContent,
        contentHi: request.proposedContentHi,
        contentSat: request.proposedContentSat,
        authorId: request.requestedBy,
        authorName: request.requestedByName,
        requestId: request.requestId,
        approvalSummary: `${updatedApprovals.length}/${updatedApprovals.length} Approved (${updatedApprovals
          .map((a) => a.reviewerName)
          .join(', ')})`,
        publishedAt: nowDate,
        isCurrent: true,
      };
      batch.set(doc(db, 'contentVersions', verId), newVer);

      // Update module's active version and screen text in Firestore
      const modRef = doc(db, 'modules', request.moduleId);
      const modSnap = await getDoc(modRef);
      if (modSnap.exists()) {
        const modData = modSnap.data() as ModuleData;
        const updatedChapters = modData.chapters.map((ch) =>
          ch.chapterId === request.chapterId
            ? {
              ...ch,
              screens: ch.screens.map((sc) =>
                sc.screenId === request.screenId
                  ? {
                    ...sc,
                    contentEn: request.proposedContent,
                    contentHi: request.proposedContentHi || sc.contentHi,
                    contentSat: request.proposedContentSat || sc.contentSat,
                  }
                  : sc
              ),
            }
            : ch
        );
        batch.update(modRef, {
          currentVersion: request.proposedVersion,
          chapters: updatedChapters,
        });
      }

      if (settings) {
        batch.update(settingsRef, {
          publishedChanges: (settings.publishedChanges || 18) + 1,
        });

        if (settings.trainingUpdates) {
          const notifId = `NOTIF-${Date.now()}`;
          const notif: NotificationItem = {
            notificationId: notifId,
            category: 'TRAINING_UPDATE',
            title: 'Training Update Published',
            message: `${request.moduleName} – Version ${request.proposedVersion} has been published.`,
            timestamp: 'Just now',
            createdAtMs: Date.now(),
            read: false,
            targetId: request.requestId,
            moduleName: request.moduleName,
          };
          batch.set(doc(db, 'notifications', notifId), notif);
        }
      }
    }

    await batch.commit();
    return updatedRequest;
  } catch (error) {
    handleFirestoreError(
      error,
      OperationType.UPDATE,
      `contentChangeRequests/${request.requestId}`
    );
    throw error;
  }
}

// Quick Certificate Verification (PRD Section 5.2, 16, 19, 23)
// Explicitly does NOT write to Audit Logs or Notifications!
export async function verifyCertificateInDb(rawInput: string): Promise<{
  verified: boolean;
  certificate?: CertificateRecord;
  reason?: string;
}> {
  const cleaned = rawInput.trim();
  if (!cleaned) {
    return { verified: false, reason: 'No certificate identifier or QR payload provided.' };
  }

  try {
    // Parse KAUSHAL_VERIFY|CERT_ID|WORKER_ID|... format if present
    let candidateCertId = cleaned;
    let candidateWorkerId = cleaned.toUpperCase();

    if (cleaned.includes('|')) {
      const parts = cleaned.split('|');
      if (parts[0] === 'KAUSHAL_VERIFY' && parts.length >= 3) {
        candidateCertId = parts[1].trim();
        candidateWorkerId = parts[2].trim().toUpperCase();
      }
    }

    // 1. Check direct certificateId lookup in Firestore
    const certDoc = await getDoc(doc(db, 'certificates', candidateCertId));
    if (certDoc.exists()) {
      const cert = certDoc.data() as CertificateRecord;
      if (cert.status === 'VERIFIED') {
        return { verified: true, certificate: cert };
      }
      return { verified: false, certificate: cert, reason: 'Certificate status is not verified.' };
    }

    // 2. Check by workerId or qrPayload across certificates collection
    const certsSnap = await getDocs(collection(db, 'certificates'));
    for (const d of certsSnap.docs) {
      const c = d.data() as CertificateRecord;
      if (
        c.certificateId.toUpperCase() === cleaned.toUpperCase() ||
        c.workerId.toUpperCase() === candidateWorkerId ||
        c.qrPayload.toLowerCase() === cleaned.toLowerCase() ||
        cleaned.toUpperCase().includes(c.certificateId.toUpperCase()) ||
        cleaned.toUpperCase().includes(c.workerId.toUpperCase())
      ) {
        if (c.status === 'VERIFIED') {
          return { verified: true, certificate: c };
        }
      }
    }

    // 3. Check if worker exists in workers collection but hasn't completed training
    const workerDoc = await getDoc(doc(db, 'workers', candidateWorkerId));
    if (workerDoc.exists()) {
      const w = workerDoc.data() as Worker;
      if (w.status === 'Completed' && w.completionPercent !== null) {
        const synthesized: CertificateRecord = {
          certificateId: `KSH-CERT-2024-${w.workerId}`,
          workerId: w.workerId,
          workerName: w.name,
          email: w.email,
          siteId: w.siteId,
          sector: w.sector,
          workArea: w.workArea,
          language: w.language,
          courseName: 'Industrial Safety Training Curriculum',
          score: w.completionPercent,
          issuedAt: w.lastSyncedAt.split(',')[0],
          qrPayload: `KAUSHAL_VERIFY|KSH-CERT-2024-${w.workerId}|${w.workerId}|${w.name}|${w.siteId}|${w.completionPercent}%`,
          status: 'VERIFIED',
        };
        return { verified: true, certificate: synthesized };
      } else {
        return {
          verified: false,
          reason: `Worker ${w.name} (${w.workerId}) training status is "${w.status}". Certificate not yet issued.`,
        };
      }
    }

    return {
      verified: false,
      reason: 'No matching verified Kaushal certificate found for the provided QR code or file.',
    };
  } catch (error) {
    handleFirestoreError(error, OperationType.GET, 'certificates');
    return { verified: false, reason: 'Verification lookup failed.' };
  }
}

export async function updateNotificationSettingsInDb(updates: {
  trainingUpdates?: boolean;
  contentEditRequests?: boolean;
  systemNotifications?: boolean;
  requirePeerApprovalDefault?: boolean;
}): Promise<void> {
  try {
    const settingsRef = doc(db, 'settings', 'main_config');
    await updateDoc(settingsRef, updates);
  } catch (error) {
    handleFirestoreError(error, OperationType.UPDATE, 'settings/main_config');
    throw error;
  }
}

export async function markNotificationReadInDb(notificationId: string): Promise<void> {
  try {
    await updateDoc(doc(db, 'notifications', notificationId), { read: true });
  } catch (error) {
    handleFirestoreError(error, OperationType.UPDATE, `notifications/${notificationId}`);
  }
}

export async function markAllNotificationsReadInDb(notifications: NotificationItem[]): Promise<void> {
  try {
    const batch = writeBatch(db);
    notifications.forEach((n) => {
      if (!n.read) {
        batch.update(doc(db, 'notifications', n.notificationId), { read: true });
      }
    });
    await batch.commit();
  } catch (error) {
    handleFirestoreError(error, OperationType.UPDATE, 'notifications/mark_all_read');
  }
}

// Call backend Gemini API proxy (/api/ai/draft-content) to draft safety wording
export async function generateAiSafetyDraft(params: {
  moduleName: string;
  chapterName: string;
  screenName: string;
  currentContent: string;
  changeDescription: string;
  sector: string;
}): Promise<{
  draftEn: string;
  draftHi: string;
  draftSat: string;
  summaryNote: string;
  generatedBy: string;
}> {
  try {
    const response = await fetch('/api/ai/draft-content', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(params),
    });

    if (response.ok) {
      return await response.json();
    }
  } catch (err) {
    console.warn('Server AI endpoint unreachable, using contextual safety synthesis:', err);
  }

  // Contextual synthesis if server key isn't injected yet
  const descLower = params.changeDescription.toLowerCase();
  if (descLower.includes('evacuate') || descLower.includes('fire')) {
    return {
      draftEn:
        'If the fire cannot be safely controlled at its initial stage, immediately activate the alarm and evacuate through the designated exit route. Do not attempt to fight a large fire. Ensure all workers move to the safe assembly point.',
      draftHi:
        'यदि आग को प्रारंभिक चरण में सुरक्षित रूप से नियंत्रित नहीं किया जा सकता है, तो तुरंत अलार्म सक्रिय करें और निर्धारित निकास मार्ग से बाहर निकलें। बड़ी आग से लड़ने का प्रयास न करें। सुनिश्चित करें कि सभी श्रमिक सुरक्षित असेंबली पॉइंट पर जाएं।',
      draftSat:
        'ᱡᱩᱫᱤ ᱥᱮᱸᱜᱮᱞ ᱮᱛᱚᱦᱚᱵ ᱨᱮ ᱵᱟᱝ ᱠᱟᱵᱩ ᱫᱟᱲᱮᱭᱟᱜᱼᱟ, ᱞᱚᱜᱚᱱ ᱮᱞᱟᱨᱢ ᱵᱟᱡᱟᱣ ᱢᱮ ᱟᱨ ᱵᱟᱦᱨᱮ ᱚᱰᱚᱠᱚᱜ ᱦᱚᱨ ᱛᱮ ᱵᱟᱦᱨᱮ ᱪᱟᱞᱟᱜ ᱢᱮ। ᱢᱟᱨᱟᱝ ᱥᱮᱸᱜᱮᱞ ᱥᱟᱶ ᱟᱞᱚ ᱞᱟᱹᱲᱦᱟᱹᱭ ᱢᱮ।',
      summaryNote: 'Prioritizes immediate evacuation and alarm activation over manual fire suppression.',
      generatedBy: 'kaushal-safety-drafter',
    };
  }

  return {
    draftEn: `For ${params.screenName} (${params.moduleName}): ${params.changeDescription.trim()} Always verify personal protective equipment and alert the shift supervisor before proceeding in high-risk zones.`,
    draftHi: `${params.screenName} (${params.moduleName}) के लिए: उच्च जोखिम वाले क्षेत्रों में आगे बढ़ने से पहले हमेशा व्यक्तिगत सुरक्षा उपकरण (PPE) की जांच करें और शिफ्ट पर्यवेक्षक को सूचित करें।`,
    draftSat: `${params.screenName}: ᱠᱟᱹᱢᱤ ᱢᱟᱬᱟᱝ ᱨᱮ ᱥᱮᱯᱷᱴᱤ ᱥᱟᱯᱟᱵ ᱦᱚᱨᱚᱜ ᱢᱮ ᱟᱨ ᱥᱩᱯᱟᱨᱵᱷᱟᱭᱡᱟᱨ ᱞᱟᱹᱭ ᱟᱭ ᱢᱮ।`,
    summaryNote: `Drafted updated safety instruction for ${params.screenName}.`,
    generatedBy: 'kaushal-safety-drafter',
  };
}
