package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;

public class ActivityTouchParking extends AppCompatActivity {
    private Context context;

    // Almacena los gestos
    GestureLibrary gesturelib;

    // Vista para gestos
    GestureOverlayView gestureview;

    //Lectura de indicaciones
    private TextToSpeech textToSpeechEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_parking);

        context = this;

        textToSpeechEngine = MainActivity.textToSpeechEngine;
        textToSpeechEngine.speak(getString(R.string.instrucciones_parking), TextToSpeech.QUEUE_FLUSH, null, "tts1");

        // Almacena los gestos
        gesturelib = GestureLibraries.fromRawResource(this, R.raw.gestures);

        if (!gesturelib.load()) {
            Log.w("GestureActivity", "Error en la carga de Gestos");
            finish();
        }

        // Vista para gestos
        gestureview = (GestureOverlayView) findViewById(R.id.gesture_view);

        // Añade el Listener
        gestureview.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gesturelib.recognize(gesture);
                char opcion;

                Prediction predict = predictions.get(0);

                if (predict.score >= 4.0) {
                    opcion = predict.name.charAt(0);

                    Intent intent = new Intent(context, ActivityFotoParking.class);
                    intent.putExtra("opcion", opcion);
                    startActivity(intent);
                }
                // Toast.makeText(context, "score: " + predict.score, Toast.LENGTH_SHORT).show();
            }
        });
    }

}