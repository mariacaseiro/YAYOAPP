package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityParking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
    }

    public void goBicis(View view) {
        Intent intent = new Intent(this, ActivityFotoParking.class);
        intent.putExtra("opcion", 'B');
        startActivity(intent);
    }

    public void goPatinetes(View view) {
        Intent intent = new Intent(this, ActivityFotoParking.class);
        intent.putExtra("opcion", 'P');
        startActivity(intent);
    }

    public void goCoches(View view) {
        Intent intent = new Intent(this, ActivityFotoParking.class);
        intent.putExtra("opcion", 'C');
        startActivity(intent);
    }

    public void goMotos(View view) {
        Intent intent = new Intent(this, ActivityFotoParking.class);
        intent.putExtra("opcion", 'M');
        startActivity(intent);
    }
}