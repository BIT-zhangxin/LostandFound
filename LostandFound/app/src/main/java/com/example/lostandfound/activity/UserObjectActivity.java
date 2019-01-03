package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyEventChange;

public class UserObjectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_user_object_name;
    private TextView tv_user_object_main_event_type;
    private TextView tv_user_object_location;
    private TextView tv_user_object_time;
    private TextView tv_user_object_description;
    private Button btn_user_object_commit_end;

    private Bundle messageBundle;

    @SuppressLint("HandlerLeak")
    private Handler commitHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(UserObjectActivity.this,"结束成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(UserObjectActivity.this,"事件已经结束，结束失败",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(UserObjectActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_object_layout);
        initComponent();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_object_commit_end:
                CommitEnd();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        tv_user_object_name = findViewById(R.id.tv_user_object_name);
        tv_user_object_main_event_type = findViewById(R.id.tv_user_object_main_event_type);
        tv_user_object_location = findViewById(R.id.tv_user_object_location);
        tv_user_object_time = findViewById(R.id.tv_user_object_time);
        tv_user_object_description = findViewById(R.id.tv_user_object_description);
        btn_user_object_commit_end = findViewById(R.id.btn_user_object_commit_end);
    }

    private void initEvent(){
        btn_user_object_commit_end.setOnClickListener(this);
    }

    private void initData(){
        messageBundle = getIntent().getExtras();
        assert messageBundle != null;
        tv_user_object_name.setText(messageBundle.getString("name",""));
        tv_user_object_main_event_type.setText(MyEventChange.MainEventToString(messageBundle.getInt("main_event_type",0)));
        tv_user_object_location.setText(messageBundle.getString("location",""));
        tv_user_object_time.setText(messageBundle.getString("time",""));
        tv_user_object_description.setText(messageBundle.getString("description",""));
    }

    private void CommitEnd(){
        int user_id=((MyApplication)getApplication()).getId();
        int main_event_id=messageBundle.getInt("main_event_id",0);
        Bundle bundle=MyBundle.CommitEndBundle(user_id,main_event_id);
        MyDataProcesser.CommitEnd(bundle,commitHandler);
    }
}
