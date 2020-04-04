package com.hrsnkwge.todoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBlistAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    public DBlistAdapter(Context context) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MainActivity.datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.list_child, null);
        }
        LinearLayout linearLayout = convertView.findViewById(R.id.container);
        DataChild dc = (DataChild) MainActivity.datas.get(position);
        TextView title = convertView.findViewById(R.id.title);
        title.setText(dc.getTitle());
        TextView time = convertView.findViewById(R.id.time);
        Long milltime = dc.getTime();
        Date today = new Date();
        if(milltime-today.getTime()<=600000&&milltime>=today.getTime()){
            linearLayout.setBackgroundColor(Color.YELLOW);
        }else if(milltime<today.getTime()){
            linearLayout.setBackgroundColor(Color.RED);
        }else{
            linearLayout.setBackgroundColor(Color.CYAN);
        }
        Date d = new Date(milltime);
        time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditSchedule.class);
                intent.putExtra("flag","edit");
                intent.putExtra("index",String.valueOf(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
