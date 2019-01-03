package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class PasswordQuestionActivity extends MyAppCompatActivity implements View.OnClickListener {

    private TextView tv_information_security_question;
    private EditText et_information_security_question_hint;
    private EditText et_information_security_question_new_hint;
    private EditText et_information_security_question_new_repeat_hint;
    private Button btn_information_security_question_commit;

    @SuppressLint("HandlerLeak")
    private Handler updatePQHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(PasswordQuestionActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(PasswordQuestionActivity.this,"密保答案错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(PasswordQuestionActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_question_layout);
        initComponent();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_information_security_question_commit:
                UpdatePasswordQuestion();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        tv_information_security_question=findViewById(R.id.tv_information_security_question);
        et_information_security_question_hint=findViewById(R.id.et_information_security_question_hint);
        et_information_security_question_new_hint=findViewById(R.id.et_information_security_question_new_hint);
        et_information_security_question_new_repeat_hint=findViewById(R.id.et_information_security_question_new_repeat_hint);
        btn_information_security_question_commit=findViewById(R.id.btn_information_security_question_commit);
    }

    private void initEvent(){
        btn_information_security_question_commit.setOnClickListener(this);
    }

    private void initData(){
        Bundle bundle=getIntent().getExtras();
        assert bundle != null;
        tv_information_security_question.setText(bundle.getString("security_question",""));
    }

    private void UpdatePasswordQuestion(){
        String security_answer=et_information_security_question_hint.getText().toString();
        String new_password=MD5.md5(et_information_security_question_new_hint.getText().toString());
        String new_password_repeat=MD5.md5(et_information_security_question_new_repeat_hint.getText().toString());
        if(!new_password.equals(new_password_repeat)){
            warningTip();
            return;
        }
        int id=((MyApplication)getApplication()).getId();
        Bundle bundle=MyBundle.UpdatePasswordQuestionBundle(id,security_answer,new_password);
        MyDataProcesser.UpdatePasswordQuestion(bundle,updatePQHandler);
    }

    void warningTip(){
        MyAlertDialog myAlertDialog=new MyAlertDialog(PasswordQuestionActivity.this,0,
                "提示","两次密码输入不一致！","知道了","",true);
        myAlertDialog.setOnCertainButtonClickListener(new MyAlertDialog.onMyAlertDialogListener() {
            public void onCancelButtonClick() {

            }
            public void onCertainButtonClick() {

            }
            public void onDismissListener() {

            }
        });
        myAlertDialog.show();
    }
}
