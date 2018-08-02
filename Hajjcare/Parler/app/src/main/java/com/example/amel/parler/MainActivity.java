package com.example.amel.parler;







import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private ImageButton b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;
    private TextToSpeech voix;


    protected Intent intent;
    protected SpeechRecognizer recognizer;
    private TextToSpeech tts;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );


        setContentView( R.layout.activity_main );
        requestRecordAudioPermission();
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "fr-FRANCE");
        intent.putExtra(RecognizerIntent.EXTRA_WEB_SEARCH_ONLY, "false");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, "5");

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this);


        t = (TextView) findViewById( R.id.textView );
        b = (ImageButton) findViewById( R.id.button );

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



        locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );


        /*----------------------------------------------------------------------------------------------------------------------------------*/
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder;
                List<Address> addresses;
                try {
                    geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    String address = addresses.get(0).getAddressLine(0);

                    if(!address.equals(t.getText().toString())) {
                        String toSpeak = address.toString();
                        voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        locationManager.removeUpdates(listener);

                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                startActivity( i );
            }
        };
        /*----------------------------------------------------------------------------------------------------------------------------------*/
        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // This method is called when the  permissions are given
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions( new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10 );
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recognizer.startListening(intent);
            }
        });
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        String message;
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Erreur audio";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Erreur client";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Permissions insuffisantes";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Erreur connexion";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "temps d'attente du réseau atteint";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message ="";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "La reconnaissance vocale est occupée\n";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "Erreur serveur";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "Aucune entrée vocale";
                break;
            default:
                message = "Je n'ai pas compris";
                break;
        }

        t.setText(message);
        String toSpeak = t.getText().toString();

        voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        t.setText( "" );

    }



    @Override
    public void onResults(Bundle results) {
        ArrayList<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String text = "";

        for (String word : words) {
            text += word + " ";
        }


        if (text.contains("position") || text.contains("où suis-je")) {


            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return;

            }
            locationManager.requestLocationUpdates("gps", 5000, 0
                    , listener);

    t.setText("");
        }
else if (text.contains("date")){
            locationManager.removeUpdates(listener);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c.getTime());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
            t.setText( weekday+formattedDate);
            String toSpeak = t.getText().toString();
            voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            t.setText( "" );

        }
      else  if ( text.contains( "urgent ")){

            String number = "12346556";  // The number on which you want to send SMS
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));

        }
        else if ( text.contains( "Bonjour" )){
            locationManager.removeUpdates(listener);
            t.setText("Bonjour Amel, comment puis-je vous aider?" );
            String toSpeak = t.getText().toString();
            voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            t.setText( "" );
        }

        else if (text.contains("quelle heure est-il") || text.contains("heure")){
            locationManager.removeUpdates(listener);
            Calendar c = Calendar.getInstance();


            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = df.format(c.getTime());



            t.setText("Il est : "+formattedDate);
            String toSpeak = t.getText().toString();

            voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            t.setText( "" );

        }



        else {
           locationManager.removeUpdates(listener);
         t.setText("je n'ai pas compris");
           String toSpeak = t.getText().toString();
           voix.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            t.setText( "" );
       }



    }





    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestRecordAudioPermission() {

        String requiredPermission = Manifest.permission.RECORD_AUDIO;


        if (this.checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_GRANTED) {

        } else {

            Toast.makeText(this, "This app needs to record audio through the microphone....", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{requiredPermission}, 101);
        }


    }

}
