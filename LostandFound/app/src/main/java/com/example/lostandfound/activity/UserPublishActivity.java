package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyMessage;
import com.example.lostandfound.component.MyMessageAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPublishActivity extends AppCompatActivity {

    private List<MyMessage> myMessageList=new ArrayList<>();
    private ListView list_view_user_publish;
    RefreshLayout refreshLayout_user_publish;

    @SuppressLint("HandlerLeak")
    private Handler informationHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(UserPublishActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(UserPublishActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(UserPublishActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_publish_layout);
        initData();
        initComponent();
        initView();
        initEvent();

    }

    void initComponent(){
        list_view_user_publish=findViewById(R.id.list_view_user_publish);
        refreshLayout_user_publish=findViewById(R.id.refreshLayout_user_publish);
    }

    private void initData(){
        int id=((MyApplication)getApplication()).getId();

        class MyThread extends Thread{

            private int id;

            private MyThread(int id){
                this.id=id;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String mysql_sql="call proc_select_user_message(?)";
                        String sql_server_sql = "exec proc_select_user_message ?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        ResultSet rs = preSt.executeQuery();
                        while(rs.next()){
                            MyMessage myMessage=new MyMessage();
                            myMessage.setMain_event_id(rs.getInt("main_event_id"));
                            myMessage.setMain_event_type(rs.getInt("main_event_type"));
                            myMessage.setObject_id(rs.getInt("object_id"));
                            myMessage.setName(rs.getString("name"));
                            myMessage.setLocation(rs.getString("location"));
                            myMessage.setTime(rs.getString("time"));
                            myMessage.setDescription(rs.getString("description"));
                            myMessageList.add(myMessage);
                        }
                        msg.what=MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                informationHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(id);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void initView(){
        MyMessageAdapter myMessageAdapter=new MyMessageAdapter(UserPublishActivity.this,R.layout.message_item,myMessageList);
        list_view_user_publish.setAdapter(myMessageAdapter);
    }

    void initEvent(){
        list_view_user_publish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(UserPublishActivity.this,UserObjectActivity.class);
                intent.putExtras(MyBundle.ObjectBundle(myMessageList.get(position)));
                startActivity(intent);
            }
        });

        refreshLayout_user_publish.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                refreshlayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout_user_publish.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initData();
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });

    }
}
