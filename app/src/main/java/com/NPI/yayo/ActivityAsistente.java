package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.tyagiabhinav.dialogflowchatlibrary.Chatbot;
import com.tyagiabhinav.dialogflowchatlibrary.ChatbotActivity;
import com.tyagiabhinav.dialogflowchatlibrary.ChatbotSettings;
import com.tyagiabhinav.dialogflowchatlibrary.DialogflowCredentials;


import java.util.UUID;

public class ActivityAsistente extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sonidos botones
        mp = MediaPlayer.create(this,R.raw.click);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente);
    }

    public void openChatbot(View view) {
        mp.start();
        // provide your Dialogflow's Google Credential JSON saved under RAW folder in resources
        DialogflowCredentials.getInstance().setInputStream(getResources().openRawResource(R.raw.gpt_asistente));

        ChatbotSettings.getInstance().setChatbot( new Chatbot.ChatbotBuilder()
                .setDoAutoWelcome(false)
                .setChatBotAvatar(getDrawable(R.drawable.person))
                .setShowMic(true).build());
        Intent intent = new Intent(this, ChatbotActivity.class);
        Bundle bundle = new Bundle();

        // provide a UUID for your session with the Dialogflow agent
        bundle.putString(ChatbotActivity.SESSION_ID, UUID.randomUUID().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openChatbot2(View view) {
        mp.start();
        // provide your Dialogflow's Google Credential JSON saved under RAW folder in resources
        DialogflowCredentials.getInstance().setInputStream(getResources().openRawResource(R.raw.gpt_asistente_ayudante));

        ChatbotSettings.getInstance().setChatbot( new Chatbot.ChatbotBuilder()
                .setDoAutoWelcome(false)
                .setChatBotAvatar(getDrawable(R.drawable.person))
                .setShowMic(true).build());
        Intent intent = new Intent(this, ChatbotActivity.class);
        Bundle bundle = new Bundle();

        // provide a UUID for your session with the Dialogflow agent
        bundle.putString(ChatbotActivity.SESSION_ID, UUID.randomUUID().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}