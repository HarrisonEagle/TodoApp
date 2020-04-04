package com.hrsnkwge.todoapp;



import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static DBHelper helper;
    public static SQLiteDatabase db;
    public static Context context;
    public static List datas;
    public static ListView listView;
    public static DBlistAdapter dBlistAdapter;
    public static AlarmManager alarmManager;
    public static String CHANNEL_ID = "225";
    public static NotificationManager manager;
    public static NotificationChannel channel;

    public static void getData(){
        if(helper == null){
            helper = new DBHelper(context);
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }
        if(datas == null){
           datas = new ArrayList<DataChild>();
        }
        if(dBlistAdapter == null){
            dBlistAdapter = new DBlistAdapter(context);
        }
        datas.clear();
        Cursor cursor = db.query(
                "tododb",
                new String[] { "id","title","content", "time" },
                null,
                null,
                null,
                null,
                "time DESC"
        );

        cursor.moveToFirst();
        Log.d("size",cursor.getCount()+"");
        for(int i = 0;i <cursor.getCount();i++){
            Log.d("tag",cursor.getString(0)+":"+cursor.getString(1));
            //
            DataChild dh = new DataChild(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));

            Intent intentbefore = new Intent(context, ReminderBroadcast.class);
            PendingIntent pendingIntentbefore = PendingIntent.getBroadcast(context, dh.getId(), intentbefore, 0);
            pendingIntentbefore.cancel();
            alarmManager.cancel(pendingIntentbefore);


            //
            long today = (new Date()).getTime();
            long after = dh.getTime();
            Date date = new Date(after);
            String notificationtitle = new SimpleDateFormat("hh:mm").format(date);
            Intent intent = new Intent(context,ReminderBroadcast.class);
            intent.putExtra("id",String.valueOf(dh.getId()));
            intent.putExtra("title",notificationtitle);
            intent.putExtra("content",dh.getTitle());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,dh.getId(),intent,PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            if(today<after){
                alarmManager.set(AlarmManager.RTC_WAKEUP,after,pendingIntent);
            }


            datas.add(dh);
            cursor.moveToNext();
        }
        listView.setAdapter(dBlistAdapter);
        dBlistAdapter.notifyDataSetChanged();
        cursor.close();
    }

    public static void insertData(SQLiteDatabase db,String title,String com, long time){

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content", com);
        values.put("time", time);

        db.insert("tododb", null, values);
    }

    public static void updateData(SQLiteDatabase db, int id, String title,String com, long time){

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content", com);
        values.put("time", time);

        db.update("tododb", values, "id = "+id, null);
    }

    public static void deleteData(SQLiteDatabase db, int id){
        db.delete("tododb","id = "+id,null);
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.todolist);
        manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        channel = new NotificationChannel(
                "channel_1",
                "89319",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        manager.createNotificationChannel(channel);

        getData();
        Button addtodo = findViewById(R.id.addtodo);
        addtodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditSchedule.class);
                intent.putExtra("flag","new");
                startActivity(intent);
            }
        });
    }
}
