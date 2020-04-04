package com.hrsnkwge.todoapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        try{
            AssetFileDescriptor afdescripter = getAssets().openFd("test.mp3");
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),afdescripter.getLength());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
            final TextView textView = findViewById(R.id.currenttime);
            Button stop = findViewById(R.id.stop);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.stop();
                    finish();
                }
            });
            final Handler someHandler = new Handler(getMainLooper());
            someHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(new SimpleDateFormat("HH:mm", Locale.JAPAN).format(new Date()));
                    someHandler.postDelayed(this, 1000);
                }
            }, 10);
        }catch (Exception e){

        }
    }
}
