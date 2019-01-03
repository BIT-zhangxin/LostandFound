package com.example.lostandfound.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.activity.ApplyInfoActivity;
import com.example.lostandfound.activity.InformationActivity;
import com.example.lostandfound.activity.ModifyActivity;
import com.example.lostandfound.activity.SecurityChooseActivity;
import com.example.lostandfound.activity.UserPublishActivity;

public class InformationFragment extends Fragment  implements View.OnClickListener  {

    private TextView tv_information_information;
    private TextView tv_information_modify;
    private TextView tv_information_security;
    private TextView tv_information_publish;
    private TextView tv_information_event;
//    private TextView tv_information_report;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.information_layout,container,false);
        initComponent(view);
        initEvent();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_information_information:
                StartInformation();
                break;
            case R.id.tv_information_modify:
                StartModify();
                break;
            case R.id.tv_information_security:
                StartSecurityChoose();
                break;
            case R.id.tv_information_publish:
                StartUserPublish();
                break;
            case R.id.tv_information_event:
                StartUserApply();
                break;
//            case R.id.tv_information_report:
//                break;
            default:
                break;
        }
    }

    private void initComponent(View view){
        tv_information_information = view.findViewById(R.id.tv_information_information);
        tv_information_modify = view.findViewById(R.id.tv_information_modify);
        tv_information_security = view.findViewById(R.id.tv_information_security);
        tv_information_publish = view.findViewById(R.id.tv_information_publish);
        tv_information_event = view.findViewById(R.id.tv_information_event);
//        tv_information_report = view.findViewById(R.id.tv_information_report);
    }

    private void initEvent(){
        tv_information_information.setOnClickListener(this);
        tv_information_modify.setOnClickListener(this);
        tv_information_security.setOnClickListener(this);
        tv_information_publish.setOnClickListener(this);
        tv_information_event.setOnClickListener(this);
//        tv_information_report.setOnClickListener(this);
    }

    private void StartInformation(){
        Intent intent=new Intent(getActivity(),InformationActivity.class);
        startActivity(intent);
    }

    private void StartModify(){
        Intent intent=new Intent(getActivity(),ModifyActivity.class);
        startActivity(intent);
    }

    private void StartSecurityChoose(){
        Intent intent=new Intent(getActivity(),SecurityChooseActivity.class);
        startActivity(intent);
    }

    private void StartUserPublish(){
        Intent intent=new Intent(getActivity(),UserPublishActivity.class);
        startActivity(intent);
    }

    private void StartUserApply(){
        Intent intent=new Intent(getActivity(),ApplyInfoActivity.class);
        startActivity(intent);
    }
}
