package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import data.IndicacionesDbHelper;


// Imports de speech-to-text -------------------------------------
import java.util.ArrayList;

import android.speech.RecognizerIntent;
// ----------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    // Base de datos. Se declara como Static para poder usarla en cualquier Activity con los datos cargados
    protected static IndicacionesDbHelper database;
    private static final int CAMERA_REQUEST = 1888;

    private SensorManager sm;
    private ShakeDetector sd;


    // Variables para el speech-to-text
    private static final int REQUEST_CODE = 1234;
    ImageButton speakButton;

    //Variable sonido botones
    MediaPlayer mp;
    ImageButton btnMedicacion;
    ImageButton btnAsistente;
    ImageButton btnLlamar;
    ImageButton btnTiempo;

    public static final String RUTA = "com.NPI.etsiitutilities.MESSAGE";
    public static final String AULA = "com.NPI.etsiitutilities.MESSAGE";

    //Lectura de indicaciones
    public static TextToSpeech textToSpeechEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new IndicacionesDbHelper(this);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST);
            return;
        }

        //Sonidos botones
        mp = MediaPlayer.create(this,R.raw.click);

        //Inicializacion del textSpeech
        textToSpeechEngine = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la s??ntesis fallido");
                }
            }
        });

        // Detectar agitaci??n
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(() -> {
            Intent intent = new Intent(this, ActivityEmergencias.class);
            startActivity(intent);
        });
        sd.start(sm);

        /*
        // Elementos de speech-to-text -------------------------------------------------------------
        speakButton = (ImageButton) findViewById(R.id.speakButton);

        // Si se est?? disponible, lo activamos en la pulsaci??n del bot??n.
        speakButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });
        */

        // -----------------------------------------------------------------------------------------
    }

    // M??todos para Speech-to-text -----------------------------------------------------------------

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "??A que pantalla quieres navegar?");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            /*
            if ( matches.get(0).contains("mapa") ) {
                startActivity(new Intent(this, ActivitySelectRoute.class));
            } else if ( matches.get(0).contains("aula") ){
                Intent intent = new Intent(this, ActivityMap.class);
                intent.putExtra(RUTA, "Banho_Clase");
                startActivity(intent);
            } else if ( matches.get(0).contains("ba??o") ){
                Intent intent = new Intent(this, ActivityMap.class);
                intent.putExtra(RUTA, "Clase_Banho");
                startActivity(intent);
            } else if ( matches.get(0).contains("escaleras") ){
                Intent intent = new Intent(this, ActivityMap.class);
                intent.putExtra(RUTA, "Clase_EscalerasExteriores");
                startActivity(intent);
            } else if ( matches.get(0).contains("horario") ){
                startActivity(new Intent(this, ActivityHorarios.class));
            } else if ( matches.get(0).contains("parking") ){
                startActivity(new Intent(this, ActivityParking.class));
            } else if ( matches.get(0).contains("bicis") || matches.get(0).contains("bicicletas") ){
                Intent intent = new Intent(this, ActivityFotoParking.class);
                intent.putExtra("opcion", 'B');
                startActivity(intent);
            } else if ( matches.get(0).contains("patinetes") ){
                Intent intent = new Intent(this, ActivityFotoParking.class);
                intent.putExtra("opcion", 'P');
                startActivity(intent);
            } else if ( matches.get(0).contains("coches") || matches.get(0).contains("automoviles") ){
                Intent intent = new Intent(this, ActivityFotoParking.class);
                intent.putExtra("opcion", 'C');
                startActivity(intent);
            } else if ( matches.get(0).contains("motos") || matches.get(0).contains("motocicletas") ){
                Intent intent = new Intent(this, ActivityFotoParking.class);
                intent.putExtra("opcion", 'M');
                startActivity(intent);
            } else if ( matches.get(0).contains("comedor") ){
                startActivity(new Intent(this, ActivityComedor.class));
            } else if ( matches.get(0).contains("3.3") || matches.get(0).contains("33") ){
                Intent intent = new Intent(this, ActivityMostrarAsignatura.class);
                intent.putExtra(AULA, "Aula 3.3");
                startActivity(intent);
            } else if ( matches.get(0).contains("1.5") || matches.get(0).contains("15") ){
                Intent intent = new Intent(this, ActivityMostrarAsignatura.class);
                intent.putExtra(AULA, "Aula 1.5");
                startActivity(intent);
            } else{
                Toast.makeText(getApplicationContext(), "No hay ninguna pantalla " +
                "relacionada con: " + matches.get(0) + " - Vuelve a intentarlo", Toast.LENGTH_LONG).show();
            }

            */

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    // ---------------------------------------------------------------------------------------------



    @Override
    protected void onResume() {
        super.onResume();
        sd.start(sm);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sd.stop();
    }


    public void goLlamar(View view) {
        mp.start();
        Intent intent = new Intent(this, ActivityLlamar.class);
        startActivity(intent);
    }

    public void goTiempo(View view) {
        mp.start();
        Intent intent = new Intent(this, ActivityTiempo.class);
        startActivity(intent);
    }


    public void goAsistente(View view) {
        mp.start();
        Intent intent = new Intent(this, ActivityAsistente.class);
        startActivity(intent);
    }

    public void goMedicacion(View view) {
        mp.start();
        Intent intent = new Intent(this, ActivityMedicacion.class);
        startActivity(intent);
    }
    public void goTest(View view) {
        Intent intent = new Intent(this, ActivityTest.class);
        startActivity(intent);
    }

}
