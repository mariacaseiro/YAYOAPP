package com.NPI.yayo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//Partial tutorial: https://www.c-sharpcorner.com/article/how-to-implement-chatgpt-in-android-application/
public class ActivityTiempo extends AppCompatActivity {

    TextView textPrediccion;

    private TextToSpeech textToSpeechEngine;

    //OpenIA petitions variables
    private RequestQueue mRequestQueue;
    private JsonRequest mJsonRequest;
    private String url = "https://api.openai.com/v1/chat/completions";
    private static String context = "Te voy a pasar los datos meteorológicos de un determinado lugar. " +
            "Quiero que me des una predicción meteorológica basada en ellos, teniendo en cuenta que lo va a leer gente de avanzada edad. " +
            "Además, me darás una recomendación sobre qué ropa ponerme para ese tiempo. " +
            "Hazlo todo de una manera breve y concisa. " +
            "La predicción es la siguiente: ";
    private String prediction = "";
    private String recomendation = "Sin recomendacion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        textToSpeechEngine = MainActivity.textToSpeechEngine;

        //Cambiamos la politica de internet para hacer la peticion
        StrictMode.ThreadPolicy goodPolicy = StrictMode.getThreadPolicy();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textPrediccion = (TextView) findViewById(R.id.text_prediccion);
        //Documentacion: https://github.com/Prominence/openweathermap-java-api/blob/master/docs/Release_2.3.0.md
        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(BuildConfig.OPENWEATHER_KEY);
        try {

            final Weather weather = openWeatherClient
                    .currentWeather()
                    .single()
                    .byCityName("A Coruña")
                    .language(Language.SPANISH)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava();

            //Volvemos a la politica original
            StrictMode.setThreadPolicy(goodPolicy);

            Byte nubosity = weather.getClouds().getValue();
            String weatherState = weather.getWeatherState().getDescription();
            int temperature = (int) weather.getTemperature().getValue();
            int humidity = weather.getHumidity().getValue();
            double windVelocity = weather.getWind().getSpeed();

            this.prediction = "Hoy el cielo estará " + weatherState +" y habrá una temperatura de " + temperature + "ºC. " +
                    "La humedad es del " + humidity + "% y la velocidad del viento de " + windVelocity + " km/h.";
            this.recomendation = "Te recomiendo que te pongas ";
            if (temperature < 15) {
                this.recomendation += "un jersey.";
            } else if (temperature > 25) {
                this.recomendation += "una blusa o camiseta.";
            } else {
                this.recomendation += "un polo o chaqueta fina.";
            }
            if (nubosity > 60) {
                this.recomendation += "No te olvides de coger paraguas!";
            }

            //Allow scroll to fit prediction text to screen
            textPrediccion.setMovementMethod(new ScrollingMovementMethod());
            textPrediccion.setText("Obteniendo predicción . . .");

            obtainRecomendation();
        }catch(Exception e){
            textPrediccion.setText("No es posible obtener la predicción. \nRevisa tu conexión a Internet!");
            e.printStackTrace();
        }
    }

    public void obtainRecomendation() {

        // creating a json object on below line.
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonContext = new JSONObject();
        JSONObject jsonPrediction = new JSONObject();
        JSONArray jsonMessage = new JSONArray();

        try {
            jsonContext.put("role","system");
            jsonContext.put("content",this.context);

            jsonPrediction.put("role","user");
            jsonPrediction.put("content",this.prediction);

            jsonMessage.put(jsonContext);
            jsonMessage.put(jsonPrediction);
            jsonObject.put("model", "gpt-3.5-turbo");
            jsonObject.put("messages", jsonMessage);
            jsonObject.put("max_tokens", 360);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //RequestQueue initialized
        this.mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        this.mJsonRequest = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    recomendation =response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                    System.out.println(recomendation);
                    textPrediccion = (TextView)findViewById(R.id.text_prediccion);
                    textPrediccion.setText(recomendation);
                    textToSpeechEngine.speak(recomendation, TextToSpeech.QUEUE_FLUSH, null, "tts1");

                    System.out.println(recomendation);;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                textPrediccion.setText(prediction+"\n\n"+recomendation);
                System.out.println("Error en la llamada: "+error.getMessage());
            }
        }){
            @Override
            public Map< String, String > getHeaders() throws AuthFailureError {
                Map < String, String > headers = new HashMap< >();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + BuildConfig.OPENAI_KEY);
                return headers;
            }
            @Override
            protected Response < JSONObject > parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        //Max time to request: 3 seconds
        RetryPolicy policy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mJsonRequest.setRetryPolicy(policy);

        mRequestQueue.add(mJsonRequest);
    }

}

