package com.NPI.yayo;

import static com.NPI.yayo.MainActivity.textToSpeechEngine;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//Tutorial avaliable in: https://www.geeksforgeeks.org/how-to-create-a-chatbot-in-android-with-brainshop-api/
public class ActivityChat extends AppCompatActivity {

    // creating variables for our
    // widgets in xml file.
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    private String url = "https://api.openai.com/v1/chat/completions";
    private static String context = "Vas a ser mi asistente motivacional. Serás servicial, empático y amable."+
            " Tu objetivo es el hacer que me se sienta mejor al ser escuchado. Te daré datos sobre mi estado anímico y emocional, e intentarás animarme."+
            " Lo más importante es que seas muy rápido y breve en tus respuestas, no tardando más de 3 segundos bajo ningún concepto.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // on below line we are initializing all our views.
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(ActivityChat.this);
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();

        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the message entered
                // by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(ActivityChat.this, "Introduce antes un mensaje..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to send message
                // to our bot to get response.
                sendMessage(userMsgEdt.getText().toString());

                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });

        // on below line we are initializing our adapter class and passing our array list to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, this);

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityChat.this, RecyclerView.VERTICAL, false);

        // below line is to set layout
        // manager to our recycler view.
        linearLayoutManager.setReverseLayout(true);
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageRVAdapter);
    }

    private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        //scroll.fullScroll(ScrollView.FOCUS_AFTER_DESCENDANTS);

        // creating a json object on below line.
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonContext = new JSONObject();
        JSONObject jsonPrediction = new JSONObject();
        JSONArray jsonMessage = new JSONArray();

        try {
            jsonContext.put("role","system");
            jsonContext.put("content",this.context);

            jsonPrediction.put("role","user");
            jsonPrediction.put("content",userMsg);

            jsonMessage.put(jsonContext);
            jsonMessage.put(jsonPrediction);
            jsonObject.put("model", "gpt-3.5-turbo");
            jsonObject.put("messages", jsonMessage);
            jsonObject.put("max_tokens", 360);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(ActivityChat.this);

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String botResponse = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                    messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                    //scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    textToSpeechEngine.setSpeechRate((float) 0.95);
                    textToSpeechEngine.setLanguage(new Locale("es","ES"));
                    textToSpeechEngine.speak(botResponse, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    System.out.println(botResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // handling error response from bot.
                    messageModalArrayList.add(new MessageModal("Sin respuesta del asistente", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError e) {
                    // error handling.
                    messageModalArrayList.add(new MessageModal("No hay respuesta", BOT_KEY));
                    Toast.makeText(ActivityChat.this, "El asistenta no funciona :(.", Toast.LENGTH_SHORT).show();
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
            RetryPolicy policy = new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);

            queue.add(jsonObjectRequest);
        }


}


