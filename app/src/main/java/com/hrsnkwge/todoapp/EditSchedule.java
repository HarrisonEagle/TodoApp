package com.hrsnkwge.todoapp;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.hrsnkwge.todoapp.MainActivity.alarmManager;
import static com.hrsnkwge.todoapp.MainActivity.context;

public class EditSchedule extends AppCompatActivity implements
        View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    Button Editdate,Edittime,delete,save,back;
    EditText title,content;
    static int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editschedule);
        delete = findViewById(R.id.delete);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.edittitle);
        content = findViewById(R.id.editcontent);
        Editdate = findViewById(R.id.editdate);
        Editdate.setOnClickListener(this);
        Edittime = findViewById(R.id.edittime);
        Edittime.setOnClickListener(this);
        save.setOnClickListener(this);
        delete.setOnClickListener(this);

        if(getIntent().getStringExtra("flag").equals("edit")){
            index = Integer.valueOf(getIntent().getStringExtra("index"));
            int id = ((DataChild)MainActivity.datas.get(index)).getId();
            DataChild dc = (DataChild) MainActivity.datas.get(index);
            Long milltime = dc.getTime();
            Date d = new Date(milltime);
            title.setText(dc.getTitle());
            content.setText(dc.getContent());
            Editdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(d));
            Edittime.setText(new SimpleDateFormat("HH:mm").format(d));

        }else{
            delete.setVisibility(View.INVISIBLE);

        }




    }


    @Override
    public void onClick(View v) {
        if(v == Editdate){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String month = String.valueOf(monthOfYear+1);
                            if(monthOfYear+1<10&&month.length()==1){
                                month = "0" + month;
                            }
                            String day = String.valueOf(dayOfMonth);
                            if(dayOfMonth<10&&day.length()==1){
                                day = "0" + day;
                            }


                            Editdate.setText(year+"-"+month+ "-" + day);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if(v == Edittime){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String hour = String.valueOf(hourOfDay);
                            if(hourOfDay<10&&hour.length()==1){
                                hour = "0" + hour;
                            }
                            String minutes = String.valueOf(minute);
                            if(minute<10&&minutes.length()==1){
                                minutes = "0" + minutes;
                            }

                            Edittime.setText(hour + ":" + minutes);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }else if(v == back){
            finish();
        }else if(v == save){
            String timetemp = Editdate.getText().toString() + " " + Edittime.getText().toString();
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Long mills = 0L;
            try{
                Date newDate = sdfDate.parse(timetemp);
                mills = newDate.getTime();
            }catch (Exception e) {

            }
            if(getIntent().getStringExtra("flag").equals("edit")){
                int id = ((DataChild)MainActivity.datas.get(index)).getId();
                MainActivity.updateData(MainActivity.db,id,title.getText().toString(),content.getText().toString(),mills);
            }else{
                MainActivity.insertData(MainActivity.db,title.getText().toString(),content.getText().toString(),mills);
            }
            Toast.makeText(getApplicationContext(),"保存しました！",Toast.LENGTH_LONG);
            MainActivity.getData();
            finish();

        }else if(v==delete){
            int id = ((DataChild)MainActivity.datas.get(index)).getId();
            MainActivity.deleteData(MainActivity.db,id);
            Intent intent = new Intent(context, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
            Toast.makeText(getApplicationContext(),"削除しました！",Toast.LENGTH_LONG);
            MainActivity.getData();
            finish();
        }
    }



}
