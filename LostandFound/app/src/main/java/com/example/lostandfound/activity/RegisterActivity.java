package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.R;

public class RegisterActivity extends MyAppCompatActivity implements View.OnClickListener{

    private EditText et_register_password;
    private EditText et_register_password_repetition;
    private EditText et_register_phone_number;
    private ImageView iv_register_show_password;
    private TextView tv_register_tips;
    private Button btn_register_register;

    @SuppressLint("HandlerLeak")
    private Handler registerHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    RegisterSuccess();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(RegisterActivity.this,"该手机已被注册",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(RegisterActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(RegisterActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initComponent();
        initView();
        initEvent();
    }

    private void initComponent(){
        et_register_password= findViewById(R.id.et_register_password);
        et_register_password_repetition= findViewById(R.id.et_register_password_repetition);
        et_register_phone_number= findViewById(R.id.et_register_phone_number);
        iv_register_show_password= findViewById(R.id.iv_register_show_password);
        tv_register_tips= findViewById(R.id.tv_register_tips);
        btn_register_register= findViewById(R.id.btn_register_register);

    }

    private void initView(){
        setStatusBarColor(this, ContextCompat.getColor(this,R.color.style));
    }

    private void initEvent(){
        iv_register_show_password.setOnClickListener(this);
        btn_register_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_register_show_password:
                passwordVisibility(iv_register_show_password,et_register_password);
                break;
            case R.id.btn_register_register:
                registerProcess();
                break;
            default:
                break;
        }
    }

    private String getEditPhoneNumber(){
        return et_register_phone_number.getText().toString();
    }

    private String getEditPassword(){
        return MD5.md5(et_register_password.getText().toString());
    }

    private String getEditPasswordRepetition(){
        return MD5.md5(et_register_password_repetition.getText().toString());
    }

    boolean checkPasswordRepetition(){
        String Password= getEditPassword();
        String PasswordRepetition= getEditPasswordRepetition();
        return Password.equals(PasswordRepetition);
    }

    private String checkInput(){
        if(getEditPhoneNumber().isEmpty()){
            return "手机号不能为空！";
        }
        else if(getEditPhoneNumber().length()!=MyDefine.LENGTH_PHONENUMBER){
            return "手机号不合法！";
        }
        else if(getEditPassword().isEmpty()){
            return "密码不能为空！";
        }
        else if(getEditPasswordRepetition().isEmpty()){
            return "重复密码不能为空！";
        }
        else if(!checkPasswordRepetition()){
            return "两次密码输入不一致！！";
        }
        else{
            return "";
        }
    }

    void changeTextTip(String warning){
        tv_register_tips.setText(warning);
    }

    void warningTip(String warning){
        MyAlertDialog myAlertDialog=new MyAlertDialog(RegisterActivity.this,0,
                "提示",warning,"知道了","",true);
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

    void registerTip(){
        MyAlertDialog myAlertDialog=new MyAlertDialog(RegisterActivity.this,0,
                "提示","你确认注册么？","确认","再想想",false);
        myAlertDialog.setOnCertainButtonClickListener(new MyAlertDialog.onMyAlertDialogListener() {
            public void onCancelButtonClick() {

            }
            public void onCertainButtonClick() {
                Register();
            }
            public void onDismissListener() {

            }
        });
        myAlertDialog.show();
    }

    void registerProcess(){
        String warning=checkInput();
        if(warning.equals("")){
            registerTip();
        }
        else{
            warningTip(warning);
        }
        changeTextTip(warning);
    }

    void Register(){
        Bundle bundle= MyBundle.RegisterBundle(getEditPhoneNumber(),getEditPassword());
        MyDataProcesser.Register(bundle,registerHandler);
    }

    void RegisterSuccess(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
