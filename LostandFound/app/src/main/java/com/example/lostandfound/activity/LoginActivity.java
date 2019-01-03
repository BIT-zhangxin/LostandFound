package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class LoginActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_login_account;
    private EditText et_login_password;
    private CheckBox checkbox_login_remember_account;
    private ImageView iv_login_show_password;
//    private TextView tv_login_forget_password;
    private Button btn_login_register;
    private Button btn_login_login;

    @SuppressLint("HandlerLeak")
    private Handler loginHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                    setUserId(msg.getData());
                    LoginSuccess();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(LoginActivity.this,"账户或密码错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(LoginActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(LoginActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initConfiguration();
        initComponent();
        initView();
        initEvent();
        initData();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_login_show_password:
                passwordVisibility(iv_login_show_password, et_login_password);
                break;
//            case R.id.tv_login_forget_password:
//                ForgetPassword();
//                break;
            case R.id.btn_login_register:
                Register();
                break;
            case R.id.btn_login_login:
                Login();
                break;
            default:
                break;
        }
    }

    private void initConfiguration(){
        SharedPreferences sp=
                getSharedPreferences("user_configuration", Context.MODE_PRIVATE);
        boolean first=sp.getBoolean("first",true);
        if(first){
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("first",false);
            editor.putString("account","");
            editor.putBoolean("remember_account",false);
            editor.apply();
        }
    }

    private void initComponent(){
        et_login_account = findViewById(R.id.et_login_account);
        et_login_password = findViewById(R.id.et_login_password);
        checkbox_login_remember_account = findViewById(R.id.checkbox_login_remember_account);
        iv_login_show_password = findViewById(R.id.iv_login_show_password);
        //tv_login_forget_password = findViewById(R.id.tv_login_forget_password);
        btn_login_register = findViewById(R.id.btn_login_register);
        btn_login_login = findViewById(R.id.btn_login_login);
    }

    private void initView(){
        setStatusBarColor(this, ContextCompat.getColor(this,R.color.style));
    }

    private void initEvent(){
        iv_login_show_password.setOnClickListener(this);
//        tv_login_forget_password.setOnClickListener(this);
        btn_login_register.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
    }

    private void initData(){
        if(getConfigurationRememberAccount()){
            et_login_account.setText(getConfigurationAccount());
            et_login_password.setText("");
            checkbox_login_remember_account.setChecked(true);
        }
        else{
            et_login_account.setText("");
            et_login_password.setText("");
            checkbox_login_remember_account.setChecked(false);
        }
    }

    private String getConfigurationAccount(){
        SharedPreferences sp=getSharedPreferences("user_configuration", Context.MODE_PRIVATE);
        return sp.getString("account","");
    }

    private boolean getConfigurationRememberAccount(){
        SharedPreferences sp=getSharedPreferences("user_configuration", Context.MODE_PRIVATE);
        return sp.getBoolean("remember_account",false);
    }

    private String getEditAccount(){
        return et_login_account.getText().toString();
    }

    private String getEditPassword(){
        return MD5.md5(et_login_password.getText().toString());
    }

    private boolean getEditRememberAccount(){
        return checkbox_login_remember_account.isChecked();
    }

    private void save(){
        SharedPreferences sp=getSharedPreferences("user_configuration", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("account", getEditAccount());
        editor.putBoolean("remember_account", getEditRememberAccount());
        editor.apply();
    }

    private void setUserId(Bundle bundle){
        MyApplication myApplication=(MyApplication)getApplication();
        myApplication.setId(bundle.getInt("id",0));
    }

    public void Login(){
        Bundle bundle= MyBundle.LoginBundle(getEditAccount(), getEditPassword());
        MyDataProcesser.Login(bundle,loginHandler);
    }

    public void Register(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void ForgetPassword(){

    }

    void LoginSuccess(){
        save();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
