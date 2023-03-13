package com.NPI.yayo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityMedicacion extends AppCompatActivity {

    Button mSetAlarmButton;
    EditText newMedicacion;
    EditText horaAlarma;
    Button anhadirMed;
    CheckedTextView alarmPrompt;
    Button deleteBtn;

    ConstraintLayout popup;

    MediaPlayer mp;
    String FILE_NAME = "medicamentos.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacion);
        Calendar calendar = Calendar.getInstance();
        mSetAlarmButton = (Button) findViewById(R.id.anhadir_alarma);
        alarmPrompt = (CheckedTextView) findViewById(R.id.alarmPrompt);
        popup = (ConstraintLayout) findViewById(R.id.popup);
        deleteBtn=(Button) findViewById(R.id.deleteBtn);

        //Sonidos botones
        mp = MediaPlayer.create(this,R.raw.click);
        alarmPrompt.setText(read_med().toString());
        popup.setVisibility(View.INVISIBLE);


        mSetAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE));
                ArrayList<Integer> alarmDays= new ArrayList<Integer>();
                alarmDays.add(Calendar.SUNDAY);
                alarmDays.add(Calendar.MONDAY);
                alarmDays.add(Calendar.TUESDAY);
                alarmDays.add(Calendar.WEDNESDAY);
                alarmDays.add(Calendar.THURSDAY);
                alarmDays.add(Calendar.FRIDAY);
                alarmDays.add(Calendar.SATURDAY);
                intent.putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
                startActivity(intent);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                popup.setVisibility(View.VISIBLE);
                mSetAlarmButton.setVisibility(View.INVISIBLE);
                alarmPrompt.setVisibility(View.INVISIBLE);

            }
        });

        anhadirMed = (Button) findViewById(R.id.btn);
        anhadirMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.INVISIBLE);
                newMedicacion = (EditText) findViewById(R.id.new_med);
                horaAlarma = (EditText) findViewById(R.id.new_med_hour);
                String med = "        "+horaAlarma.getText().toString()+" - "+newMedicacion.getText().toString();

                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    fos.write("\n".getBytes());
                    fos.write(med.getBytes());
                    fos.write("\n".getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alarmPrompt.setText(read_med().toString());
                alarmPrompt.setVisibility(View.VISIBLE);
                mSetAlarmButton.setVisibility(View.VISIBLE);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_meds();
            }
        });
    }

    public StringBuffer read_med(){
        List<String> arr = new ArrayList<String>();
        StringBuffer text = new StringBuffer();
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(openFileInput("medicamentos.txt")));
            String line;
            while ((line = bReader.readLine()) != null) {
                text.append(line + "\n");
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void delete_meds(){
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        alarmPrompt.setText(read_med().toString());
        alarmPrompt.setVisibility(View.VISIBLE);
    }
}