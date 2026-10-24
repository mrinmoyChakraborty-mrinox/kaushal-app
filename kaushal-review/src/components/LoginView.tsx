import React, { useState } from 'react';
import {
  ShieldCheck,
  Building2,
  Lock,
  Eye,
  EyeOff,
  ArrowRight,
  Pickaxe,
  Factory,
  Gem,
  AlertCircle,
  CheckCircle2,
  Sparkles,
} from 'lucide-react';
import { AdminPersona } from '../types';
import { ADMIN_PERSONAS } from '../services/kaushalDatabase';

interface LoginViewProps {
  onLoginSuccess: (admin: AdminPersona) => void;
}

export const LoginView: React.FC<LoginViewProps> = ({ onLoginSuccess }) => {
  const [mineId, setMineId] = useState('KSH-DHN-001');
  const [password, setPassword] = useState('admin123');
  const [showPassword, setShowPassword] = useState(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [forgotMsg, setForgotMsg] = useState(false);

  // Handle Sign In submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMsg(null);

    const trimmedId = mineId.trim().toUpperCase();
    if (!trimmedId) {
      setErrorMsg('Please enter a valid Mine / Site ID.');
      return;
    }
    if (!password) {
      setErrorMsg('Please enter your password.');
      return;
    }

    setIsLoading(true);

    setTimeout(() => {
      // Find matching admin persona by Mine ID or match substring
      let matched = ADMIN_PERSONAS.find(
        (p) => p.mineId.toUpperCase() === trimmedId || trimmedId.includes(p.mineId.toUpperCase())
      );

      // Fallback matching by sector/id if user typed something custom
      if (!matched) {
        if (trimmedId.includes('JSR') || trimmedId.includes('STEEL')) {
          matched = ADMIN_PERSONAS[1];
        } else if (trimmedId.includes('KDR') || trimmedId.includes('MICA')) {
          matched = ADMIN_PERSONAS[2];
        } else if (trimmedId.includes('RAN') || trimmedId.includes('PIT')) {
          matched = ADMIN_PERSONAS[3];
        } else {
          // Dynamic fallback for any custom Mine ID entered
          matched = {
            id: `custom_${Date.now()}`,
            mineId: trimmedId,
            name: 'Site Administrator',
            shortLabel: 'Site Admin',
            siteName: `Industrial Site (${trimmedId})`,
            sector: 'Mining',
            role: 'Mine Manager',
            email: `admin@${trimmedId.toLowerCase()}.kaushal.in`,
            avatarUrl:
              'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=160&auto=format&fit=crop&q=80',
          };
        }
      }

      setIsLoading(false);
      onLoginSuccess(matched);
    }, 600);
  };

  // Quick preset click handler
  const handleSelectPreset = (persona: AdminPersona) => {
    setMineId(persona.mineId);
    setPassword('admin123');
    setErrorMsg(null);
  };

  return (
    <div className="min-h-screen w-full relative flex items-center justify-center bg-slate-950 overflow-hidden font-sans">
      {/* Background Image with Rich Industrial Overlay */}
      <div className="absolute inset-0 z-0">
        <img
          src="/login_hero.jpg"
          alt="Industrial Mine Background"
          className="w-full h-full object-cover object-center scale-105 filter brightness-75 contrast-110"
        />
        {/* Dark Blue to Slate Gradient Overlay */}
        <div className="absolute inset-0 bg-gradient-to-r from-slate-950/95 via-slate-900/85 to-slate-950/70" />
        <div className="absolute inset-0 bg-radial from-blue-900/20 via-transparent to-black/60" />
      </div>

      {/* Main Container */}
      <div className="relative z-10 w-full max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 flex flex-col lg:flex-row items-center justify-between gap-8 lg:gap-12">
        {/* LEFT HERO SECTION */}
        <div className="flex-1 text-white space-y-6 lg:max-w-xl text-center lg:text-left">
          {/* Logo & Brand Header */}
          <div className="inline-flex items-center gap-3.5 bg-white/10 backdrop-blur-md px-4 py-2 rounded-2xl border border-white/15 shadow-lg">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-[#1d63dc] to-blue-500 flex items-center justify-center shadow-md">
              <ShieldCheck className="w-6 h-6 text-white" />
            </div>
            <div>
              <div className="flex items-center gap-1.5 font-extrabold tracking-wider text-xl text-white">
                KAUSHAL
                <span className="text-xs font-semibold px-2 py-0.5 rounded bg-amber-400 text-slate-950 tracking-normal">
                  FIELD BOOK
                </span>
              </div>
              <p className="text-[10px] uppercase font-bold tracking-widest text-slate-300">
                LEARN • PRACTICE • STAY SAFE
              </p>
            </div>
          </div>

          {/* Main Headline */}
          <div className="space-y-3">
            <h1 className="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-white leading-tight">
              From Learning Safety <br />
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-amber-400 via-orange-400 to-amber-300">
                to Living It.
              </span>
            </h1>
            <p className="text-base sm:text-lg text-slate-300 font-normal max-w-lg leading-relaxed">
              Digital Safety Training for India's Industrial Workforce across Mining, Steel, and Mica operations.
            </p>
          </div>

          {/* 3 Sector Highlights Bar */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-4">
            {/* Mining Pill */}
            <div className="bg-slate-900/80 backdrop-blur-md border border-slate-700/60 p-3.5 rounded-2xl flex items-center gap-3 hover:border-amber-500/50 transition-colors">
              <div className="w-10 h-10 rounded-xl bg-amber-500/20 text-amber-400 flex items-center justify-center shrink-0">
                <Pickaxe className="w-5 h-5" />
              </div>
              <div>
                <p className="text-xs font-bold text-white">Mining</p>
                <p className="text-[11px] text-slate-400">Safer Mines</p>
              </div>
            </div>

            {/* Steel Pill */}
            <div className="bg-slate-900/80 backdrop-blur-md border border-slate-700/60 p-3.5 rounded-2xl flex items-center gap-3 hover:border-blue-500/50 transition-colors">
              <div className="w-10 h-10 rounded-xl bg-blue-500/20 text-blue-400 flex items-center justify-center shrink-0">
                <Factory className="w-5 h-5" />
              </div>
              <div>
                <p className="text-xs font-bold text-white">Steel</p>
                <p className="text-[11px] text-slate-400">Stronger Industries</p>
              </div>
            </div>

            {/* Mica Pill */}
            <div className="bg-slate-900/80 backdrop-blur-md border border-slate-700/60 p-3.5 rounded-2xl flex items-center gap-3 hover:border-emerald-500/50 transition-colors">
              <div className="w-10 h-10 rounded-xl bg-emerald-500/20 text-emerald-400 flex items-center justify-center shrink-0">
                <Gem className="w-5 h-5" />
              </div>
              <div>
                <p className="text-xs font-bold text-white">Mica</p>
                <p className="text-[11px] text-slate-400">Sustainable Practices</p>
              </div>
            </div>
          </div>
        </div>

        {/* RIGHT WHITE LOGIN CARD */}
        <div className="w-full max-w-md shrink-0">
          <div className="bg-white/95 backdrop-blur-xl rounded-3xl shadow-2xl border border-white/40 p-7 sm:p-8 space-y-6 relative overflow-hidden">
            {/* Top Logo inside Card */}
            <div className="text-center space-y-2">
              <div className="inline-flex items-center justify-center gap-2 mb-1">
                <div className="w-9 h-9 rounded-xl bg-[#0b1936] text-amber-400 flex items-center justify-center shadow-sm">
                  <ShieldCheck className="w-5 h-5" />
                </div>
                <span className="text-xl font-extrabold tracking-wider text-[#0b1936]">
                  KAUSHAL
                </span>
                <span className="text-xs font-bold px-1.5 py-0.5 rounded bg-amber-100 text-amber-800">
                  FIELD BOOK
                </span>
              </div>

              <h2 className="text-2xl font-extrabold text-[#0b1936]">Admin Login</h2>
              <p className="text-xs text-slate-500 max-w-xs mx-auto leading-relaxed">
                Access your mine or site dashboard to manage training and monitor safety progress.
              </p>
            </div>

            {/* Quick Demo Mine Selectors */}
            <div className="space-y-2 bg-slate-50 p-3 rounded-2xl border border-slate-200/80">
              <div className="flex items-center justify-between text-[11px] font-semibold text-slate-600">
                <span className="flex items-center gap-1">
                  <Sparkles className="w-3.5 h-3.5 text-amber-500" />
                  Quick Login Demo Sites:
                </span>
                <span className="text-[10px] text-slate-400">(Click to select)</span>
              </div>

              <div className="grid grid-cols-2 gap-1.5">
                {ADMIN_PERSONAS.map((p) => {
                  const isSelected = mineId === p.mineId;
                  return (
                    <button
                      key={p.id}
                      type="button"
                      onClick={() => handleSelectPreset(p)}
                      className={`px-2.5 py-1.5 rounded-xl text-[11px] font-semibold text-left transition-all flex items-center justify-between cursor-pointer border ${
                        isSelected
                          ? 'bg-[#0b1936] text-white border-[#0b1936] shadow-xs'
                          : 'bg-white text-slate-700 border-slate-200 hover:border-blue-400 hover:bg-blue-50/50'
                      }`}
                    >
                      <span className="truncate">{p.mineId}</span>
                      <span className="text-[9px] opacity-75 font-normal ml-1">
                        {p.sector}
                      </span>
                    </button>
                  );
                })}
              </div>
            </div>

            {/* Login Form */}
            <form onSubmit={handleSubmit} className="space-y-4">
              {errorMsg && (
                <div className="p-3 rounded-xl bg-rose-50 border border-rose-200 flex items-start gap-2 text-xs text-rose-700">
                  <AlertCircle className="w-4 h-4 text-rose-600 shrink-0 mt-0.5" />
                  <span>{errorMsg}</span>
                </div>
              )}

              {/* Mine / Site ID Input */}
              <div className="space-y-1.5">
                <label className="block text-xs font-bold text-slate-700">
                  Mine / Site ID
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-slate-400">
                    <Building2 className="w-4 h-4" />
                  </div>
                  <input
                    type="text"
                    value={mineId}
                    onChange={(e) => setMineId(e.target.value)}
                    placeholder="Enter your Mine ID (e.g. KSH-DHN-001)"
                    className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-slate-300 focus:border-[#1d63dc] focus:ring-2 focus:ring-blue-100 text-xs font-medium text-slate-900 placeholder:text-slate-400 transition-all outline-hidden"
                  />
                </div>
              </div>

              {/* Password Input */}
              <div className="space-y-1.5">
                <div className="flex items-center justify-between">
                  <label className="block text-xs font-bold text-slate-700">Password</label>
                  <button
                    type="button"
                    onClick={() => setForgotMsg(true)}
                    className="text-xs font-semibold text-[#1d63dc] hover:underline cursor-pointer"
                  >
                    Forgot Password?
                  </button>
                </div>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-slate-400">
                    <Lock className="w-4 h-4" />
                  </div>
                  <input
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password"
                    className="w-full pl-10 pr-10 py-2.5 rounded-xl border border-slate-300 focus:border-[#1d63dc] focus:ring-2 focus:ring-blue-100 text-xs font-medium text-slate-900 placeholder:text-slate-400 transition-all outline-hidden"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute inset-y-0 right-0 pr-3 flex items-center text-slate-400 hover:text-slate-600 cursor-pointer"
                  >
                    {showPassword ? (
                      <EyeOff className="w-4 h-4" />
                    ) : (
                      <Eye className="w-4 h-4" />
                    )}
                  </button>
                </div>
              </div>

              {forgotMsg && (
                <div className="p-3 rounded-xl bg-blue-50 border border-blue-200 text-xs text-blue-800 flex items-center gap-2">
                  <CheckCircle2 className="w-4 h-4 text-blue-600 shrink-0" />
                  <span>
                    Password reset link sent to your registered mine supervisor email.
                  </span>
                </div>
              )}

              {/* Sign In Button */}
              <button
                type="submit"
                disabled={isLoading}
                className="w-full py-3 px-4 rounded-xl bg-[#0b1936] hover:bg-[#152a54] text-white font-bold text-sm shadow-lg hover:shadow-xl transition-all flex items-center justify-center gap-2 group cursor-pointer disabled:opacity-75"
              >
                {isLoading ? (
                  <span className="flex items-center gap-2">
                    <span className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                    Authenticating Site...
                  </span>
                ) : (
                  <>
                    <span>Sign In</span>
                    <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
                  </>
                )}
              </button>
            </form>

            {/* Divider with Shield */}
            <div className="relative flex items-center justify-center my-4">
              <div className="w-full border-t border-slate-200" />
              <div className="absolute bg-white px-3 text-slate-400">
                <ShieldCheck className="w-4 h-4" />
              </div>
            </div>

            {/* Authorized Notice Banner */}
            <div className="p-3 rounded-2xl bg-blue-50/70 border border-blue-100 flex items-center gap-3">
              <div className="w-8 h-8 rounded-xl bg-blue-100 text-[#1d63dc] flex items-center justify-center shrink-0">
                <Lock className="w-4 h-4" />
              </div>
              <div className="text-[11px] text-slate-700">
                <span className="font-bold text-slate-900 block">
                  Authorized mine and site access only.
                </span>
                Use your assigned Mine ID and password.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
