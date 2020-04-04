package com.hrsnkwge.todoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import java.util.Calendar;

import static com.hrsnkwge.todoapp.MainActivity.dBlistAdapter;

public class ReminderBroadcast extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        if(dBlistAdapter != null){
            dBlistAdapter.notifyDataSetChanged();
        }

        Notification notification;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           notification = new Notification.Builder(context,"channel_1")
                    .setContentTitle(intent.getStringExtra("title"))
                    .setContentText(intent.getStringExtra("content"))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
        }else{
            notification = new Notification.Builder(context)
                    .setContentTitle(intent.getStringExtra("title"))
                    .setContentText(intent.getStringExtra("content"))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        /*
        Intent alarmIntent = new Intent();
        alarmIntent.setClassName(context.getPackageName(),AlarmActivity.class.getName());
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
         */
    }
}
