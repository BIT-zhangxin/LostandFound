package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyEventChange;

public class ObjectActivity extends MyAppCompatActivity implements View.OnClickListener {

    private TextView tv_object_name;
    private TextView tv_object_main_event_type;
    private TextView tv_object_location;
    private TextView tv_object_time;
    private TextView tv_object_description;
    private Button btn_object_apply;
    private Button btn_object_report;

    private Bundle messageBundle;

    @SuppressLint("HandlerLeak")
    private Handler ApplyHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(ObjectActivity.this,"申请成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ObjectActivity.this,"不能申请自己发布的事件",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ObjectActivity.this,"申请失败,重复申请",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ObjectActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler reportHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(ObjectActivity.this,"举报成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ObjectActivity.this,"不能举报自己发布的事件",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ObjectActivity.this,"举报失败,重复举报",Toast.LENGTH_LONG).show();
                    break;
                    case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ObjectActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_layout);
        initComponent();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_object_apply:
                Apply();
                break;
            case R.id.btn_object_report:
                Report();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        tv_object_name = findViewById(R.id.tv_object_name);
        tv_object_main_event_type = findViewById(R.id.tv_object_main_event_type);
        tv_object_location = findViewById(R.id.tv_object_location);
        tv_object_time = findViewById(R.id.tv_object_time);
        tv_object_description = findViewById(R.id.tv_object_description);
        btn_object_apply = findViewById(R.id.btn_object_apply);
        btn_object_report = findViewById(R.id.btn_object_report);
    }

    private void initEvent(){
        btn_object_apply.setOnClickListener(this);
        btn_object_report.setOnClickListener(this);

    }

    private void initData(){
        messageBundle = getIntent().getExtras();
        assert messageBundle != null;
        tv_object_name.setText(messageBundle.getString("name",""));
        tv_object_main_event_type.setText(MyEventChange.MainEventToString(messageBundle.getInt("main_event_type",0)));
        tv_object_location.setText(messageBundle.getString("location",""));
        tv_object_time.setText(messageBundle.getString("time",""));
        tv_object_description.setText(messageBundle.getString("description",""));
    }

    private void Apply(){
        int user_id=((MyApplication)getApplication()).getId();
        int main_event_id=messageBundle.getInt("main_event_id",0);
        Bundle bundle=MyBundle.ApplyBundle(user_id,main_event_id);
        MyDataProcesser.Apply(bundle,ApplyHandler);
    }

    private void Report(){
        int user_id=((MyApplication)getApplication()).getId();
        int main_event_id=messageBundle.getInt("main_event_id",0);
        Bundle bundle=MyBundle.ReportBundle(user_id,main_event_id);
        MyDataProcesser.Report(bundle,reportHandler);
    }
}
