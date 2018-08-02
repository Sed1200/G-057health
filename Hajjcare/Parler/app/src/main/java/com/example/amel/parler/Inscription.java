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

public class Inscription extends Activity {
    Button btn_ins;
    TextView con;
    EditText nom, prenom, ndt, mdp, cmdp,numurg;
    TextToSpeech voix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_inscription );
        con = (TextView) (findViewById( R.id.con ));
        nom = (EditText) findViewById( R.id.nom );
        prenom = (EditText) findViewById( R.id.prenom );
        ndt = (EditText) findViewById( R.id.ndt );
        mdp = (EditText) findViewById( R.id.mot_passe );
        cmdp = (EditText) findViewById( R.id.Cmot_passe );
        btn_ins = (Button) (findViewById( R.id.btn_inscription ));


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

        con.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "déja inscrit?";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent conex = new Intent( Inscription.this, Connexion.class );
                startActivity( conex );
            }
        } );

        btn_ins.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "inscription";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Intent in = new Intent( Inscription.this, MainActivity.class );
                startActivity( in );
            }
        } );


        nom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "votre nom";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );

        prenom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "votre prénom";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );
        ndt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "votre numéro de téléphone";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );

        mdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "votre mot de passe";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );
        cmdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Confirmez mot de passe";
                //Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        } );

    }
}
