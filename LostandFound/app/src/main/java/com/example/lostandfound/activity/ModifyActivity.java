package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class ModifyActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_modify_nickname;
    private EditText et_modify_contact_information;
    private Button btn_modify_commit;

    @SuppressLint("HandlerLeak")
    private Handler modifyHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(ModifyActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ModifyActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ModifyActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);
        initComponent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_modify_commit:
                Modify();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        et_modify_nickname = findViewById(R.id.et_modify_nickname);
        et_modify_contact_information = findViewById(R.id.et_modify_contact_information);
        btn_modify_commit = findViewById(R.id.btn_modify_commit);
    }

    private void initData(){
        btn_modify_commit.setOnClickListener(this);
    }

    private void Modify(){
        int id=((MyApplication)getApplication()).getId();
        String nickname=et_modify_nickname.getText().toString();
        String contact_information=et_modify_contact_information.getText().toString();
        Bundle bundle=MyBundle.UpdateUserInformationBundle(id,nickname,contact_information);
        MyDataProcesser.UpdateUseInformation(bundle,modifyHandler);
    }
}
