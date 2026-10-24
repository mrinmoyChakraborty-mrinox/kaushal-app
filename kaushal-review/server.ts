import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import { createServer as createViteServer } from 'vite';
import { GoogleGenAI, Type } from '@google/genai';
import dotenv from 'dotenv';

dotenv.config();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

async function startServer() {
  const app = express();
  const PORT = 3000;

  app.use(express.json({ limit: '5mb' }));

  // Health & API status
  app.get('/api/v1/admin/health', (_req, res) => {
    res.json({
      status: 'ok',
      platform: 'Kaushal Admin Platform',
      aiConfigured: Boolean(process.env.GEMINI_API_KEY),
      timestamp: new Date().toISOString(),
    });
  });

  // TASK-ADM-102: Optional AI Assistance to Draft Safety Content
  // AI drafts suggested wording based on Admin's change description & module/chapter/screen context.
  // AI NEVER publishes directly; human review + peer approval required.
  app.post('/api/ai/draft-content', async (req, res) => {
    try {
      const {
        moduleName,
        chapterName,
        screenName,
        currentContent,
        changeDescription,
        sector = 'Mining',
      } = req.body || {};

      if (!changeDescription || typeof changeDescription !== 'string') {
        res.status(400).json({ error: 'changeDescription is required' });
        return;
      }

      const apiKey = process.env.GEMINI_API_KEY;
      if (!apiKey || apiKey === 'MY_GEMINI_API_KEY') {
        res.status(503).json({
          error: 'GEMINI_API_KEY not configured on server',
          fallbackAvailable: true,
        });
        return;
      }

      const ai = new GoogleGenAI({ apiKey });

      const prompt = `You are a certified industrial safety instructional designer for the KAUSHAL Vocational Safety Training Platform (${sector} sector).
An authorized site administrator has requested a controlled content update for:
- Module: ${moduleName || 'Fire & Explosion Response'}
- Chapter: ${chapterName || 'Chapter 2: Fire Extinguisher Use'}
- Screen / Section: ${screenName || 'Screen 4: Correct Extinguisher Selection'}
- Current Published Screen Text: "${currentContent || 'In case of a workplace fire, identify the class of fire and select the matching extinguisher before attempting suppression.'}"
- Admin's Change Instruction: "${changeDescription}"

Draft a concise, unmistakable, life-saving instructional screen text (2 to 4 sentences) suitable for industrial workers (with corresponding Hindi and Santali romanized/Devanagari translations). Keep sentences clear, practical, and action-oriented. Do NOT include markdown formatting.`;

      const response = await ai.models.generateContent({
        model: 'gemini-3-flash-preview',
        contents: prompt,
        config: {
          responseMimeType: 'application/json',
          responseSchema: {
            type: Type.OBJECT,
            properties: {
              draftEn: {
                type: Type.STRING,
                description: 'Suggested English instructional wording for the learning screen.',
              },
              draftHi: {
                type: Type.STRING,
                description: 'Hindi translation of the suggested screen wording.',
              },
              draftSat: {
                type: Type.STRING,
                description: 'Santali translation of the suggested screen wording.',
              },
              summaryNote: {
                type: Type.STRING,
                description: 'Brief 1-sentence summary of the safety improvement for peer reviewers.',
              },
            },
            required: ['draftEn', 'draftHi', 'draftSat', 'summaryNote'],
          },
        },
      });

      const rawText = response.text || '{}';
      const parsed = JSON.parse(rawText);
      res.json({
        draftEn: parsed.draftEn,
        draftHi: parsed.draftHi,
        draftSat: parsed.draftSat,
        summaryNote: parsed.summaryNote,
        generatedBy: 'gemini-3-flash-preview',
      });
    } catch (error: any) {
      console.error('AI Draft generation error:', error);
      res.status(500).json({
        error: error?.message || 'Failed to generate AI draft',
        fallbackAvailable: true,
      });
    }
  });

  if (process.env.NODE_ENV !== 'production') {
    const vite = await createViteServer({
      server: { middlewareMode: true },
      appType: 'spa',
    });
    app.use(vite.middlewares);
  } else {
    const distPath = path.join(__dirname, 'dist');
    app.use(express.static(distPath));
    app.get('*', (_req, res) => {
      res.sendFile(path.join(distPath, 'index.html'));
    });
  }

  app.listen(PORT, '0.0.0.0', () => {
    console.log(`Kaushal Admin Server running on http://0.0.0.0:${PORT}`);
  });
}

startServer();
