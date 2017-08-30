package com.educareapps.quiz.utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Rakib on 8/29/2017.
 */

public class SpeechToTextUtil implements RecognitionListener {
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    Activity activity;

    SpeechListeningFinishListener speechListeningFinishListener;

    public SpeechToTextUtil(Activity activity, SpeechListeningFinishListener speechListeningFinishListener) {
        this.activity = activity;
        this.speechListeningFinishListener = speechListeningFinishListener;
        speech = SpeechRecognizer.createSpeechRecognizer(activity);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                activity.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public void startListening() {
        if (speech != null)
            speech.startListening(recognizerIntent);
    }

    public void stopListening() {
        if (speech != null)
            speech.stopListening();
    }

    public void destroySpeechToText() {
        if (speech != null) {
            speech.destroy();
        }
    }
    private String LOG_TAG = "SpeechToText";
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.i(LOG_TAG, "FAILED " + errorMessage);
        speechListeningFinishListener.getErrorMsg(errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        speechListeningFinishListener.getListeningResult(matches.get(0));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error, please try again.";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error, please try again.";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions, please try again.";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error, please try again.";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout, please try again.";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match, please try again.";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Recognition Service busy, please try again.";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server, please try again.";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input, please try again.";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public interface SpeechListeningFinishListener {
        public void getListeningResult(String result);
        public void getErrorMsg(String result);
    }
}
