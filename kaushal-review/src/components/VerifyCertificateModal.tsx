import React, { useState, useRef, useEffect } from 'react';
import {
  X,
  UploadCloud,
  QrCode,
  CheckCircle2,
  XCircle,
  Camera,
  Download,
  FileCheck,
  ShieldCheck,
  Loader2,
} from 'lucide-react';
import jsQR from 'jsqr';
import QRCode from 'qrcode';
import { CertificateRecord, Worker } from '../types';
import { verifyCertificateInDb } from '../services/kaushalDatabase';

interface VerifyCertificateModalProps {
  isOpen: boolean;
  onClose: () => void;
  certificates: CertificateRecord[];
  workers: Worker[];
}

export const VerifyCertificateModal: React.FC<VerifyCertificateModalProps> = ({
  isOpen,
  onClose,
  certificates,
  workers,
}) => {
  const [inputCode, setInputCode] = useState('');
  const [selectedFileName, setSelectedFileName] = useState<string | null>(null);
  const [isVerifying, setIsVerifying] = useState(false);
  const [isScanningCamera, setIsScanningCamera] = useState(false);
  const [cameraError, setCameraError] = useState<string | null>(null);
  const [verificationResult, setVerificationResult] = useState<{
    status: 'VERIFIED' | 'NOT_VERIFIED';
    certificate?: CertificateRecord;
    reason?: string;
  } | null>(null);

  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const videoRef = useRef<HTMLVideoElement | null>(null);
  const streamRef = useRef<MediaStream | null>(null);
  const scanIntervalRef = useRef<number | null>(null);

  useEffect(() => {
    if (!isOpen) {
      stopCamera();
      setVerificationResult(null);
      setInputCode('');
      setSelectedFileName(null);
      setCameraError(null);
    }
    return () => {
      stopCamera();
    };
  }, [isOpen]);

  const stopCamera = () => {
    if (scanIntervalRef.current) {
      window.clearInterval(scanIntervalRef.current);
      scanIntervalRef.current = null;
    }
    if (streamRef.current) {
      streamRef.current.getTracks().forEach((track) => track.stop());
      streamRef.current = null;
    }
    setIsScanningCamera(false);
  };

  const startCameraScan = async () => {
    setCameraError(null);
    setVerificationResult(null);
    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: { facingMode: 'environment' },
      });
      streamRef.current = stream;
      setIsScanningCamera(true);

      setTimeout(() => {
        if (videoRef.current) {
          videoRef.current.srcObject = stream;
          videoRef.current.play().catch(() => {});

          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');

          scanIntervalRef.current = window.setInterval(async () => {
            if (
              videoRef.current &&
              videoRef.current.readyState === videoRef.current.HAVE_ENOUGH_DATA &&
              ctx
            ) {
              canvas.width = videoRef.current.videoWidth;
              canvas.height = videoRef.current.videoHeight;
              ctx.drawImage(videoRef.current, 0, 0, canvas.width, canvas.height);
              const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
              const code = jsQR(imageData.data, imageData.width, imageData.height);
              if (code && code.data) {
                stopCamera();
                setInputCode(code.data);
                await runVerification(code.data);
              }
            }
          }, 350);
        }
      }, 150);
    } catch (err) {
      setCameraError(
        'Camera access unavailable in this browser frame. Upload a QR Certificate image or select a certificate below.'
      );
      setIsScanningCamera(false);
    }
  };

  const handleFileUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setSelectedFileName(file.name);
    setVerificationResult(null);

    if (file.type.startsWith('image/')) {
      const img = new Image();
      const objectUrl = URL.createObjectURL(file);
      img.onload = async () => {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = img.width;
        canvas.height = img.height;
        if (ctx) {
          ctx.drawImage(img, 0, 0);
          const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
          const qr = jsQR(imageData.data, imageData.width, imageData.height);
          if (qr && qr.data) {
            setInputCode(qr.data);
            URL.revokeObjectURL(objectUrl);
            await runVerification(qr.data);
            return;
          }
        }
        URL.revokeObjectURL(objectUrl);
        // Fallback: check filename for worker ID or certificate ID
        setInputCode(file.name.replace(/\.[^/.]+$/, ''));
      };
      img.src = objectUrl;
    } else {
      // PDF or text certificate
      const baseName = file.name.replace(/\.[^/.]+$/, '');
      setInputCode(baseName);
    }
  };

  const runVerification = async (overridePayload?: string) => {
    const queryPayload = (overridePayload ?? inputCode).trim();
    if (!queryPayload) {
      setVerificationResult({
        status: 'NOT_VERIFIED',
        reason: 'Please upload a certificate file, scan a QR code, or enter a Certificate/Worker ID.',
      });
      return;
    }

    setIsVerifying(true);
    try {
      const res = await verifyCertificateInDb(queryPayload);
      setVerificationResult({
        status: res.verified ? 'VERIFIED' : 'NOT_VERIFIED',
        certificate: res.certificate,
        reason: res.reason,
      });
    } finally {
      setIsVerifying(false);
    }
  };

  const downloadSampleQrImage = async (valid: boolean) => {
    const payload = valid
      ? certificates[0]?.qrPayload ||
        'KAUSHAL_VERIFY|KSH-CERT-2024-W001|W001|Rahul Kumar|Mine A|92%'
      : 'KAUSHAL_VERIFY|INVALID-CERT-999|W003|Amit Singh|Mine A|0%';
    const dataUrl = await QRCode.toDataURL(payload, {
      width: 320,
      margin: 2,
      color: { dark: '#0f1e36', light: '#ffffff' },
    });
    const a = document.createElement('a');
    a.href = dataUrl;
    a.download = valid
      ? 'Kaushal_Certificate_W001_Rahul_Kumar.png'
      : 'Unverified_Certificate_W003.png';
    a.click();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-xs p-4">
      <div className="bg-white rounded-xl shadow-2xl border border-slate-200 w-full max-w-2xl overflow-hidden animate-in fade-in zoom-in-95 duration-150">
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-4 border-b border-slate-200 bg-slate-50/70">
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 rounded-lg bg-[#1d63dc]/10 text-[#1d63dc] flex items-center justify-center">
              <ShieldCheck className="w-5 h-5" />
            </div>
            <div>
              <h3 className="text-base font-bold text-slate-900">Verify Certificate</h3>
              <p className="text-xs text-slate-500">
                Immediate QR or file check against Kaushal workforce records
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-100 transition-colors"
            aria-label="Close modal"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Body */}
        <div className="p-6">
          <div className="grid grid-cols-1 md:grid-cols-12 gap-6 items-stretch">
            {/* Left column: Upload / Scan QR dropzone matching Panel 12 */}
            <div className="md:col-span-7 flex flex-col justify-between space-y-4">
              {isScanningCamera ? (
                <div className="border-2 border-dashed border-[#1d63dc] rounded-xl p-3 bg-slate-950 text-white flex flex-col items-center justify-center relative min-h-[210px]">
                  <video
                    ref={videoRef}
                    className="w-full h-44 object-cover rounded-lg"
                    muted
                    playsInline
                  />
                  <div className="mt-2 flex items-center justify-between w-full px-1">
                    <span className="text-xs text-blue-300 flex items-center gap-1.5">
                      <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping" />
                      Scanning for Kaushal QR Code...
                    </span>
                    <button
                      type="button"
                      onClick={stopCamera}
                      className="text-xs font-medium text-rose-300 hover:text-rose-200"
                    >
                      Cancel Scan
                    </button>
                  </div>
                </div>
              ) : (
                <div
                  onClick={() => fileInputRef.current?.click()}
                  className="border-2 border-dashed border-slate-300 hover:border-[#1d63dc] bg-slate-50/70 hover:bg-blue-50/30 transition-all rounded-xl p-6 text-center cursor-pointer flex flex-col items-center justify-center min-h-[190px]"
                >
                  <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/*,.pdf"
                    onChange={handleFileUpload}
                    className="hidden"
                  />
                  <div className="w-12 h-12 rounded-full bg-blue-100 text-[#1d63dc] flex items-center justify-center mb-3">
                    <UploadCloud className="w-6 h-6" />
                  </div>
                  <p className="text-sm font-semibold text-slate-800">
                    Upload Certificate (PDF/Image)
                  </p>
                  <p className="text-xs text-slate-400 my-1">or</p>
                  <p className="text-sm font-semibold text-[#1d63dc] flex items-center gap-1.5">
                    <QrCode className="w-4 h-4" />
                    Scan QR Code
                  </p>
                  {selectedFileName && (
                    <span className="mt-2.5 inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-blue-50 text-xs font-medium text-[#1d63dc] border border-blue-200">
                      <FileCheck className="w-3.5 h-3.5" />
                      {selectedFileName}
                    </span>
                  )}
                </div>
              )}

              {cameraError && (
                <p className="text-xs text-amber-700 bg-amber-50 border border-amber-200 rounded-lg px-3 py-2">
                  {cameraError}
                </p>
              )}

              {/* Direct Certificate ID / QR Payload Input + Camera Trigger */}
              <div className="space-y-2">
                <div className="flex gap-2">
                  <input
                    type="text"
                    value={inputCode}
                    onChange={(e) => setInputCode(e.target.value)}
                    placeholder="Enter Certificate ID (e.g. KSH-CERT-2024-W001 or W001)..."
                    className="flex-1 px-3 py-2 text-xs rounded-lg border border-slate-300 focus:outline-none focus:ring-2 focus:ring-[#1d63dc] font-mono"
                  />
                  <button
                    type="button"
                    onClick={startCameraScan}
                    title="Scan QR with Camera"
                    className="px-3 py-2 rounded-lg border border-slate-300 bg-white hover:bg-slate-50 text-slate-700 text-xs font-medium flex items-center gap-1.5 shrink-0"
                  >
                    <Camera className="w-3.5 h-3.5 text-[#1d63dc]" />
                    Camera QR
                  </button>
                </div>

                {/* Quick-fill pills from real database records + downloadable QR images */}
                <div className="flex flex-wrap items-center gap-1.5 pt-1">
                  <span className="text-[11px] text-slate-400 mr-1">Test Records:</span>
                  {certificates.slice(0, 2).map((c) => (
                    <button
                      key={c.certificateId}
                      type="button"
                      onClick={() => {
                        setInputCode(c.certificateId);
                        runVerification(c.certificateId);
                      }}
                      className="text-[11px] px-2 py-0.5 rounded bg-emerald-50 text-emerald-700 border border-emerald-200 hover:bg-emerald-100 font-medium"
                    >
                      ✓ {c.workerId} ({c.workerName})
                    </button>
                  ))}
                  {workers
                    .filter((w) => w.status !== 'Completed')
                    .slice(0, 1)
                    .map((w) => (
                      <button
                        key={w.workerId}
                        type="button"
                        onClick={() => {
                          setInputCode(w.workerId);
                          runVerification(w.workerId);
                        }}
                        className="text-[11px] px-2 py-0.5 rounded bg-rose-50 text-rose-700 border border-rose-200 hover:bg-rose-100 font-medium"
                      >
                        ✕ {w.workerId} (Pending)
                      </button>
                    ))}
                  <button
                    type="button"
                    onClick={() => downloadSampleQrImage(true)}
                    className="text-[11px] px-2 py-0.5 rounded bg-slate-100 text-slate-600 hover:bg-slate-200 flex items-center gap-1 ml-auto"
                    title="Download a real QR code PNG to test file upload"
                  >
                    <Download className="w-3 h-3" />
                    Sample QR PNG
                  </button>
                </div>
              </div>

              <button
                type="button"
                onClick={() => runVerification()}
                disabled={isVerifying}
                className="w-full py-2.5 px-4 rounded-lg bg-[#1d63dc] hover:bg-[#1652ba] text-white font-semibold text-sm shadow-xs transition-colors flex items-center justify-center gap-2 cursor-pointer"
              >
                {isVerifying ? (
                  <>
                    <Loader2 className="w-4 h-4 animate-spin" />
                    Checking Certificate...
                  </>
                ) : (
                  'Verify Certificate'
                )}
              </button>
            </div>

            {/* Right column: Immediate Result Cards (Certificate Verified OR Certificate Not Verified) */}
            <div className="md:col-span-5 flex flex-col justify-center">
              {!verificationResult ? (
                <div className="h-full rounded-xl border border-slate-200 bg-slate-50/60 p-5 flex flex-col items-center justify-center text-center">
                  <div className="w-12 h-12 rounded-full bg-slate-200/70 text-slate-400 flex items-center justify-center mb-3">
                    <QrCode className="w-6 h-6" />
                  </div>
                  <p className="text-sm font-semibold text-slate-700">Ready for Quick Check</p>
                  <p className="text-xs text-slate-500 mt-1 leading-relaxed">
                    Upload a worker certificate image/PDF or scan the QR code to immediately verify authenticity.
                  </p>
                  <p className="text-[11px] text-slate-400 mt-3 italic">
                    Note: Kaushal certificates verify vocational safety module completion and are not statutory licences.
                  </p>
                </div>
              ) : verificationResult.status === 'VERIFIED' ? (
                <div className="h-full rounded-xl border border-emerald-200 bg-emerald-50/70 p-5 flex flex-col items-center justify-between text-center animate-in fade-in duration-150">
                  <div className="w-14 h-14 rounded-full bg-emerald-500 text-white flex items-center justify-center shadow-sm my-2">
                    <CheckCircle2 className="w-8 h-8" />
                  </div>
                  <div>
                    <h4 className="text-base font-bold text-emerald-800">Certificate Verified</h4>
                    {verificationResult.certificate && (
                      <div className="mt-3 text-left bg-white/90 rounded-lg p-3 border border-emerald-200/80 space-y-1 text-xs">
                        <div className="flex justify-between">
                          <span className="text-slate-500">Worker:</span>
                          <span className="font-semibold text-slate-900">
                            {verificationResult.certificate.workerName} (
                            {verificationResult.certificate.workerId})
                          </span>
                        </div>
                        <div className="flex justify-between">
                          <span className="text-slate-500">Certificate ID:</span>
                          <span className="font-mono font-medium text-slate-800">
                            {verificationResult.certificate.certificateId}
                          </span>
                        </div>
                        <div className="flex justify-between">
                          <span className="text-slate-500">Site / Sector:</span>
                          <span className="font-medium text-slate-800">
                            {verificationResult.certificate.siteId} •{' '}
                            {verificationResult.certificate.sector}
                          </span>
                        </div>
                        <div className="flex justify-between">
                          <span className="text-slate-500">Achieved Score:</span>
                          <span className="font-bold text-emerald-700">
                            {verificationResult.certificate.score}%
                          </span>
                        </div>
                        <div className="flex justify-between">
                          <span className="text-slate-500">Issued On:</span>
                          <span className="text-slate-700">
                            {verificationResult.certificate.issuedAt}
                          </span>
                        </div>
                      </div>
                    )}
                  </div>
                  <button
                    type="button"
                    onClick={onClose}
                    className="mt-4 w-full py-2 px-4 rounded-lg bg-white hover:bg-slate-50 text-slate-700 border border-slate-200 text-xs font-semibold shadow-2xs"
                  >
                    Close
                  </button>
                </div>
              ) : (
                <div className="h-full rounded-xl border border-rose-200 bg-rose-50/70 p-5 flex flex-col items-center justify-between text-center animate-in fade-in duration-150">
                  <div className="w-14 h-14 rounded-full bg-rose-500 text-white flex items-center justify-center shadow-sm my-2">
                    <XCircle className="w-8 h-8" />
                  </div>
                  <div>
                    <h4 className="text-base font-bold text-rose-800">Certificate Not Verified</h4>
                    <p className="text-xs text-rose-700 mt-2 leading-relaxed">
                      {verificationResult.reason ||
                        'No matching verified certificate record was found in the Kaushal database.'}
                    </p>
                  </div>
                  <button
                    type="button"
                    onClick={onClose}
                    className="mt-4 w-full py-2 px-4 rounded-lg bg-white hover:bg-slate-50 text-slate-700 border border-slate-200 text-xs font-semibold shadow-2xs"
                  >
                    Close
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
