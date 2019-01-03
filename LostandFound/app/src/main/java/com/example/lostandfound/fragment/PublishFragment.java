package com.example.lostandfound.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

import java.util.Objects;

public class PublishFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private Spinner spinner_event_type;
    private EditText et_publish_object_name;
    private EditText et_publish_location;
    private EditText et_publish_time;
    private EditText et_publish_description;
    private Button btn_publish_publish;

    @SuppressLint("HandlerLeak")
    private Handler publishHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(mContext,"发布成功",Toast.LENGTH_LONG).show();
                    Clear();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(mContext,"发布失败",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(mContext,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_layout,container,false);
        initComponent(view);
        initEvent();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_publish_publish:
                Publish();
                break;
            default:
                break;
        }
    }

    private void initComponent(View view){
        spinner_event_type=view.findViewById(R.id.spinner_event_type);
        et_publish_object_name=view.findViewById(R.id.et_publish_object_name);
        et_publish_location=view.findViewById(R.id.et_publish_location);
        et_publish_time=view.findViewById(R.id.et_publish_time);
        et_publish_description=view.findViewById(R.id.et_publish_description);
        btn_publish_publish=view.findViewById(R.id.btn_publish_publish);
    }

    private void initEvent(){
        btn_publish_publish.setOnClickListener(this);
    }

    void Publish(){
        String object_name=et_publish_object_name.getText().toString();
        if(object_name.equals("")){
            Message message=new Message();
            message.what=MyDefine.REPLY_FAILED;
            publishHandler.sendMessage(message);
            return;
        }
        int user_id=((MyApplication)Objects.requireNonNull(getActivity()).getApplication()).getId();
        int event_type=(int)spinner_event_type.getSelectedItemId()+1;
        String location=et_publish_location.getText().toString();
        String time=et_publish_time.getText().toString();
        String description=et_publish_description.getText().toString();
        Bundle bundle= MyBundle.PublishBundle(user_id,event_type,object_name,location,time,description);
        MyDataProcesser.Publish(bundle,publishHandler);
    }

    private void Clear(){
        et_publish_object_name.setText("");
        et_publish_location.setText("");
        et_publish_time.setText("");
        et_publish_description.setText("");
    }
}
