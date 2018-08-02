package com.example.amel.parler;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Connexion extends Activity{
    Button connex;
    TextView inscri;
    EditText et_ndt,et_mdp;
    TextToSpeech voix;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_connexion );
        et_ndt = (EditText) findViewById(R.id.et_ndt);
        et_mdp = (EditText) findViewById(R.id.mdp);
        connex = (Button) findViewById(R.id.btn_con);
        inscri=(TextView)findViewById( R.id.inscri );
        voix = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {

                    int result = voix.setLanguage(Locale.FRANCE);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }

            }
        });

        et_ndt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Numéro de téléphone";

                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );
        et_mdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Mot de passe";

                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );




        connex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = "Connexion";

                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent i = new Intent(Connexion.this,MainActivity.class);
                startActivity(i);
            }
        });
        inscri.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Pas encore inscrit?";

                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent ins = new Intent(Connexion.this,Inscription.class);
                startActivity(ins);
            }
        } );




    }


}
