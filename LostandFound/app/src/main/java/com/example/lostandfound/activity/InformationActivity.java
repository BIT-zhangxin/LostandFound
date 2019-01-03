package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InformationActivity extends MyAppCompatActivity {

    MyApplication myApplication;

    private TextView tv_info_info_nickname;
    private TextView tv_info_info_id;
    private TextView tv_info_info_phone_number;
    private TextView tv_info_info_contact_information;
    private TextView tv_info_info_credit_score;

    @SuppressLint("HandlerLeak")
    private Handler informationHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(InformationActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(InformationActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(InformationActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_information_layout);
        initComponent();
        initData();
        initView();
    }

    private void initComponent(){
        tv_info_info_nickname = findViewById(R.id.tv_info_info_nickname);
        tv_info_info_id = findViewById(R.id.tv_info_info_id);
        tv_info_info_phone_number = findViewById(R.id.tv_info_info_phone_number);
        tv_info_info_contact_information = findViewById(R.id.tv_info_info_contact_information);
        tv_info_info_credit_score = findViewById(R.id.tv_info_info_credit_score);
        myApplication=(MyApplication)getApplication();
    }

    private void initData(){
        int id=myApplication.getId();

        class MyThread extends Thread{

            private int id;
            private MyApplication myApplication;

            private MyThread(int id,MyApplication myApplication){
                this.id=id;
                this.myApplication=myApplication;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String mysql_sql="call proc_select_userinfo(?)";
                        String sql_server_sql = "exec proc_select_userinfo ?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            myApplication.setPhone_number(rs.getString("phone_number"));
                            myApplication.setNickname(rs.getString("nickname"));
                            myApplication.setContact_information(rs.getString("contact_information"));
                            myApplication.setCredit_score(rs.getInt("credit_score"));
                        } else{
                            msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                informationHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(id,myApplication);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        tv_info_info_nickname.setText(myApplication.getNickname());
        tv_info_info_id.setText(""+myApplication.getId());
        tv_info_info_phone_number.setText(myApplication.getPhone_number());
        tv_info_info_contact_information.setText(myApplication.getContact_information());
        tv_info_info_credit_score.setText(""+myApplication.getCredit_score());
    }
}
