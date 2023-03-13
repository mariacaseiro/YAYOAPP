package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityLlamar extends AppCompatActivity {

    ImageView make_call;
    ImageView call_susi;
    ImageView call_paco;
    ImageView call_pedro;
    ImageView call_maria;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamar);

        //Sonidos botones
        mp = MediaPlayer.create(this,R.raw.click);

        make_call = (ImageView) findViewById(R.id.call_keyboard);
        call_susi = (ImageView) findViewById(R.id.callsusi);
        call_paco = (ImageView) findViewById(R.id.callpaco);
        call_pedro = (ImageView) findViewById(R.id.callpedro);
        call_maria = (ImageView) findViewById(R.id.callmaria);

        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        });
        call_susi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + Uri.encode("981563100")));
                startActivity(intent);
            }
        });
        call_paco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + Uri.encode("981563100")));
                startActivity(intent);
            }
        });
        call_pedro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("981563100"));
                startActivity(intent);
            }
        });
        call_maria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:981563100"));
                startActivity(intent);
            }
        });
    }
}