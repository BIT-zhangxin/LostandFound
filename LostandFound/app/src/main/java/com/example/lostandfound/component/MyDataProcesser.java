package com.example.lostandfound.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDataProcesser {
    //所有可以异步处理的网络通信操作

    //对主事件进行申请
    public static void Apply(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id = bundle.getInt("user_id", 0);
                        int main_event_id = bundle.getInt("main_event_id",0);
                        String mysql_sql="call proc_apply(?,?)";
                        String sql_server_sql = "exec proc_apply ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, user_id);
                        preSt.setInt(2, main_event_id);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(e.getMessage().equals("self")){
                        msg.what = MyDefine.REPLY_FAILED;
                    }
                    else{
                        msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                    }
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //修改主事件状态为结束
    public static void CommitEnd(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id = bundle.getInt("user_id", 0);
                        int main_event_id = bundle.getInt("main_event_id",0);
                        String mysql_sql="call proc_commit_end(?,?)";
                        String sql_server_sql = "exec proc_commit_end ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, user_id);
                        preSt.setInt(2, main_event_id);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //设置密保问题
    public static void InsertSecurityQuestion(Bundle bundle, Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String question = bundle.getString("question", "");
                        String answer = bundle.getString("answer", "");
                        String mysql_sql = "call proc_insert_security_question(?,?,?)";
                        String sql_server_sql = "exec proc_insert_security_question ?,?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, question);
                        preSt.setString(3, answer);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //登录查询
    public static void Login(Bundle bundle,Handler handler){

        class MyThread extends Thread{

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle,Handler handler){
                this.bundle=bundle;
                this.handler=handler;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String id_or_phone_number = bundle.getString("id_or_phone_number", "");
                        String password = bundle.getString("password", "");
                        String mysql_sql="call proc_login(?,?)";
                        String sql_server_sql = "exec proc_login ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1, id_or_phone_number);
                        preSt.setString(2, password);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            Bundle bundle=new Bundle();
                            bundle.putInt("id",rs.getInt("id"));
                            msg.setData(bundle);
                        } else{
                            msg.what = MyDefine.REPLY_FAILED;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle,handler).start();
    }

    //发布信息
    public static void Publish(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id=bundle.getInt("user_id",0);
                        int event_type=bundle.getInt("event_type",0);
                        String object_name=bundle.getString("object_name","");
                        String location=bundle.getString("location","");
                        String time=bundle.getString("time","");
                        String description=bundle.getString("description","");
                        String mysql_sql="call proc_publish(?,?,?,?,?,?)";
                        String sql_server_sql="exec proc_publish ?,?,?,?,?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,user_id);
                        preSt.setInt(2,event_type);
                        preSt.setString(3, object_name);
                        preSt.setString(4, location);
                        preSt.setString(5,time);
                        preSt.setString(6,description);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //注册账号
    public static void Register(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String phone_number = bundle.getString("phone_number", "");
                        String password = bundle.getString("password", "");
                        String mysql_sql = "call proc_register(?,?)";
                        String sql_server_sql="exec proc_register ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1, phone_number);
                        preSt.setString(2, password);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //进行举报
    public static void Report(Bundle bundle, Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id=bundle.getInt("user_id",0);
                        int main_event_id=bundle.getInt("main_event_id",0);
                        String mysql_sql="call proc_report(?,?)";
                        String sql_server_sql = "exec proc_report ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,user_id);
                        preSt.setInt(2, main_event_id);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(e.getMessage().equals("self")){
                        msg.what = MyDefine.REPLY_FAILED;
                    }
                    else{
                        msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                    }
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //通过密码修改密码
    public static void UpdatePasswordPassword(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String old_password=bundle.getString("old_password", "");
                        String new_password=bundle.getString("new_password", "");
                        String mysql_sql="call proc_update_password_password(?,?,?)";
                        String sql_server_sql = "exec proc_update_password_password ?,?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, old_password);
                        preSt.setString(3, new_password);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //通过密保修改密码
    public static void UpdatePasswordQuestion(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String security_answer=bundle.getString("security_answer", "");
                        String new_password=bundle.getString("new_password", "");
                        String mysql_sql="call proc_update_password_question(?,?,?)";
                        String sql_server_sql = "exec proc_update_password_question ?,?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, security_answer);
                        preSt.setString(3, new_password);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //修改个人信息
    public static void UpdateUseInformation(Bundle bundle, Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String nickname=bundle.getString("nickname", "");
                        String contact_information=bundle.getString("contact_information", "");
                        String mysql_sql="call proc_update_user_information(?,?,?)";
                        String sql_server_sql = "exec proc_update_user_information ?,?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, nickname);
                        preSt.setString(3, contact_information);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }
}
