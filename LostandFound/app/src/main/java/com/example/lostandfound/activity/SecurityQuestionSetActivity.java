package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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

public class SecurityQuestionSetActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_information_security_set_question_question;
    private EditText et_information_security_set_question_answer;
    private Button btn_information_security_set_question_commit;

    @SuppressLint("HandlerLeak")
    private Handler setSecurityQuestionHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(SecurityQuestionSetActivity.this,"设置成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(SecurityQuestionSetActivity.this,"设置失败",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(SecurityQuestionSetActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_set_question_layout);
        initComponent();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_information_security_set_question_commit:
                SetSecurityQuestion();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        et_information_security_set_question_question=findViewById(R.id.et_information_security_set_question_question);
        et_information_security_set_question_answer=findViewById(R.id.et_information_security_set_question_answer);
        btn_information_security_set_question_commit=findViewById(R.id.btn_information_security_set_question_commit);
    }

    void initEvent(){
        btn_information_security_set_question_commit.setOnClickListener(this);
    }

    void SetSecurityQuestion(){
        int id=((MyApplication)getApplication()).getId();
        String question=et_information_security_set_question_question.getText().toString();
        String answer=et_information_security_set_question_answer.getText().toString();
        if(question.equals("") || answer.equals("")){
            Message message=new Message();
            message.what=MyDefine.REPLY_FAILED;
            setSecurityQuestionHandler.sendMessage(message);
            return;
        }
        Bundle bundle=MyBundle.InsertSecurityQuestionBundle(id,question,answer);
        MyDataProcesser.InsertSecurityQuestion(bundle,setSecurityQuestionHandler);
    }
}
