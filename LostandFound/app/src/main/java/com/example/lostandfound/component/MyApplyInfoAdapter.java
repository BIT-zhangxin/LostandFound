package com.example.lostandfound.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lostandfound.R;

import java.util.List;

public class MyApplyInfoAdapter extends ArrayAdapter<MyApplyInfo> {
    private int resourceId;

    public MyApplyInfoAdapter(Context context, int textViewResourceId, List<MyApplyInfo> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MyApplyInfo myApplyInfo=getItem(position);
        @SuppressLint("ViewHolder") View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        TextView tv_apply_info_object_name=view.findViewById(R.id.tv_apply_info_object_name);
        TextView tv_apply_info_nickname=view.findViewById(R.id.tv_apply_info_nickname);
        TextView tv_apply_info_contact_information=view.findViewById(R.id.tv_apply_info_contact_information);

        assert myApplyInfo != null;
        tv_apply_info_object_name.setText(myApplyInfo.getObject_name());
        tv_apply_info_nickname.setText(myApplyInfo.getNickname());
        tv_apply_info_contact_information.setText(myApplyInfo.getContact_information());
        return view;
    }
}