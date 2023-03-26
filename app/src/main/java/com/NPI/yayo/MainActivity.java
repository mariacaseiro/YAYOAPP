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

import java.util.Calendar;

import android.speech.RecognizerIntent;
import android.widget.TextView;

import medicinas.ActivityMedicinas;
// ----------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

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

    //variable mensaje bienvenida
    TextView mensaje;
    TextView fechaBienvenida;
    //Lectura de indicaciones
    public static TextToSpeech textToSpeechEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //database = new IndicacionesDbHelper(this);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST);
            return;
        }

        mensaje = (TextView) findViewById(R.id.mensaje);

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        int weekDay = rightNow.get(Calendar.DAY_OF_WEEK);
        System.out.println("Dia: "+day+" Mes: "+month+" Semana: "+weekDay);
        String fecha = obtenerFecha(day,month,weekDay);
        fechaBienvenida = (TextView) findViewById(R.id.fecha);
        fechaBienvenida.setText(fecha);

        if(5 < hour && hour < 12){
            mensaje.setText("¡Buenos días!");
        }else if(12 <= hour && hour < 21){
            mensaje.setText("¡Buenas tardes!");
        }else{
            mensaje.setText("¡Buenas noches!");
        }

        //Sonidos botones
        mp = MediaPlayer.create(this,R.raw.click);

        //Inicializacion del textSpeech
        textToSpeechEngine = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la síntesis fallido");
                }
            }
        });

        // Detectar agitación
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(() -> {
            Intent intent = new Intent(this, ActivityEmergencias.class);
            startActivity(intent);
        });
        sd.start(sm);
    }

    private String obtenerFecha(int day, int month, int weekDay) {
        String fecha = "";
        switch (weekDay){
            case 1:
                fecha += "Domingo "; break;
            case 2:
                fecha += "Lunes "; break;
            case 3:
                fecha += "Martes "; break;
            case 4:
                fecha += "Miercoles "; break;
            case 5:
                fecha += "Jueves "; break;
            case 6:
                fecha += "Viernes"; break;
            case 7:
                fecha += "Sábado "; break;
        }
        fecha += day;
        switch (month){
            case 0:
                fecha += " de Enero "; break;
            case 1:
                fecha += " de Febrero "; break;
            case 2:
                fecha += " de Marzo "; break;
            case 3:
                fecha += " de Abril "; break;
            case 4:
                fecha += " de Mayo "; break;
            case 5:
                fecha += " de Junio "; break;
            case 6:
                fecha += " de Julio "; break;
            case 7:
                fecha += " de Agosto "; break;
            case 8:
                fecha += " de Septiembre "; break;
            case 9:
                fecha += " de Octubre "; break;
            case 10:
                fecha += " de Noviembre "; break;
            case 11:
                fecha += " de Diciembre "; break;
        }
        return fecha;
    }

    // Métodos para Speech-to-text -----------------------------------------------------------------

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "¿A que pantalla quieres navegar?");
        startActivityForResult(intent, REQUEST_CODE);
    }


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
        Intent intent = new Intent(this, ActivityChat.class);
        startActivity(intent);
    }

    public void goMedicacion(View view) {
        mp.start();
        Intent intent = new Intent(this, ActivityMedicinas.class);
        startActivity(intent);
    }
}
