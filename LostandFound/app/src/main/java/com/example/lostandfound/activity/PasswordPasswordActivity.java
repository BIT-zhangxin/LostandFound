package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class PasswordPasswordActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_information_security_password_old_hint;
    private EditText et_information_security_password_new_hint;
    private EditText et_information_security_password_new_repeat_hint;
    private ImageView iv_information_security_password;
    private Button btn_information_security_password_commit;

    @SuppressLint("HandlerLeak")
    private Handler updatePPHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(PasswordPasswordActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(PasswordPasswordActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(PasswordPasswordActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_password_layout);
        initComponent();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_information_security_password:
                passwordVisibility(iv_information_security_password, et_information_security_password_new_hint);
                break;
            case R.id.btn_information_security_password_commit:
                UpdatePasswordPassword();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        et_information_security_password_old_hint=findViewById(R.id.et_information_security_password_old_hint);
        et_information_security_password_new_hint=findViewById(R.id.et_information_security_password_new_hint);
        et_information_security_password_new_repeat_hint=findViewById(R.id.et_information_security_password_new_repeat_hint);
        iv_information_security_password=findViewById(R.id.iv_information_security_password);
        btn_information_security_password_commit=findViewById(R.id.btn_information_security_password_commit);
    }

    private void initEvent(){
        iv_information_security_password.setOnClickListener(this);
        btn_information_security_password_commit.setOnClickListener(this);
    }

    private void UpdatePasswordPassword(){
        String old_password=MD5.md5(et_information_security_password_old_hint.getText().toString());
        String new_password=MD5.md5(et_information_security_password_new_hint.getText().toString());
        String new_password_repeat=MD5.md5(et_information_security_password_new_repeat_hint.getText().toString());
        if(!new_password.equals(new_password_repeat)){
            warningTip();
            return;
        }
        int id=((MyApplication)getApplication()).getId();
        Bundle bundle=MyBundle.UpdatePasswordPasswordBundle(id,old_password,new_password);
        MyDataProcesser.UpdatePasswordPassword(bundle,updatePPHandler);
    }

    void warningTip(){
        MyAlertDialog myAlertDialog=new MyAlertDialog(PasswordPasswordActivity.this,0,
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
