package com.example.lostandfound.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lostandfound.R;

import java.util.List;

public class MyMessageAdapter extends ArrayAdapter<MyMessage> {
    private int resourceId;

    public MyMessageAdapter(Context context, int textViewResourceId, List<MyMessage> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MyMessage message=getItem(position);
        @SuppressLint("ViewHolder") View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        ImageView imageView=view.findViewById(R.id.iv_message_object);
        TextView tv_message_name=view.findViewById(R.id.tv_message_name);
        TextView tv_message_main_event_type=view.findViewById(R.id.tv_message_main_event_type);
        TextView tv_message_location=view.findViewById(R.id.tv_message_location);
        TextView tv_message_time=view.findViewById(R.id.tv_message_time);

        imageView.setImageResource(R.drawable.default_picture);
        assert message != null;
        tv_message_main_event_type.setText(MyEventChange.MainEventToString(message.getMain_event_type()));
        tv_message_name.setText(message.getName());
        tv_message_location.setText(message.getLocation());
        tv_message_time.setText(message.getTime());
        return view;
    }
}