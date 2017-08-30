package com.educareapps.quiz.utilities;

/**
 * Created by Rakib on 8/30/2017.
 */

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class TextToSpeechManager {
    private static final String TAG = TextToSpeechManager.class.getSimpleName();

    private Context mContext;
    private TextToSpeech mTextToSpeech;
    private boolean isTTSInitialized;
    private Queue<String> mTextToSpeakQueue;
    FinishSpeakListener finishSpeakListener;
    private HashMap<String, String> params;
    private String ttsID = "myTTS";

    public TextToSpeechManager(Context context, FinishSpeakListener finishSpeakListener) {
        mContext = context;
        isTTSInitialized = false;
        mTextToSpeakQueue = new LinkedList<String>();
        mTextToSpeech = new TextToSpeech(mContext, new TTSListener());
        this.finishSpeakListener = finishSpeakListener;
    }

    public void speak(String text) {
        if (isTTSInitialized) {
            Log.d(TAG, "TTS initialized. Speaking text");
            params = new HashMap<String, String>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, ttsID);
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
        } else {
            Log.d(TAG, "TTS not initialized. Pushing text to queue");
            mTextToSpeakQueue.add(text);
        }
    }

    private void flushTextToSpeakQueue() {
        Log.d(TAG, "flushTextToSpeakQueue()");
        while (mTextToSpeakQueue.peek() != null) {
            speak(mTextToSpeakQueue.poll());
        }
    }

    private class TTSListener implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                String da = Locale.getDefault().getDisplayLanguage();
                Log.e("lan", String.valueOf(da));
                mTextToSpeech.setLanguage(Locale.getDefault());
                mTextToSpeech.setSpeechRate(0.7f);
                isTTSInitialized = true;
                mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {
                        if (utteranceId.equals(ttsID)) {
                            finishSpeakListener.speakFinished();


                        }

                    }

                    @Override
                    public void onError(String utteranceId) {

                    }
                });
                flushTextToSpeakQueue();
            }
        }
    }

    public void stopTextToSpeech() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    public interface FinishSpeakListener {
        public void speakFinished();
    }
}