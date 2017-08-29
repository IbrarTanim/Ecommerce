package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.educareapps.quiz.R;

import java.util.Locale;

public class QuizActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    QuizActivity activity;
    TextToSpeech textToSpeech;
    EditText edtTTS;
    Button btnSpeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activity = this;

        textToSpeech = new TextToSpeech(this, this);
        edtTTS = (EditText) findViewById(R.id.edtTTS);
        btnSpeek = (Button) findViewById(R.id.btnSpeek);
        btnSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vlaueTTS = edtTTS.getText().toString();
                speak(vlaueTTS);
            }
        });

    }

    private void speak(String textToSpeak) {
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // Note that a language may not be available, and the result will indicate this.
            int result = textToSpeech.setLanguage(new Locale("bn", "BD"));

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Lanuage data is missing or the language is not supported.
                Log.e("404", "Language is not available.");
            }
        } else {
            // Initialization failed.
            Log.e("404", "Could not initialize TextToSpeech.");
            // May be its not installed so we prompt it to be installed
            Intent installIntent = new Intent();
            installIntent.setAction(
                    TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }


    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}
