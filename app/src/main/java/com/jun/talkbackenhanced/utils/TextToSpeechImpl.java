package com.jun.talkbackenhanced.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.ERROR;

import java.util.Locale;

public class TextToSpeechImpl {

    private TextToSpeech tts;

    private Context context;

    public TextToSpeechImpl (Context context, Locale lang) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    tts.setLanguage(lang);
                }
            }
        });
    }


    public void speech(String sentence) {
        tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
    }
}
