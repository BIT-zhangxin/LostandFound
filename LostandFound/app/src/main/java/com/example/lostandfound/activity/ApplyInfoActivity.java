package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyApplyInfo;
import com.example.lostandfound.component.MyApplyInfoAdapter;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplyInfoActivity extends MyAppCompatActivity{

    private List<MyApplyInfo> myApplyInfoList=new ArrayList<>();
    private ListView list_view_apply_info;
    RefreshLayout refreshLayout_user_apply_info;

    @SuppressLint("HandlerLeak")
    private Handler informationHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ApplyInfoActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ApplyInfoActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ApplyInfoActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_info_layout);
        initComponent();
        initData();
        initView();
        initEvent();

    }

    void initComponent(){
        list_view_apply_info=findViewById(R.id.list_view_apply_info);
        refreshLayout_user_apply_info=findViewById(R.id.refreshLayout_user_apply_info);
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
                        String mysql_sql="call proc_select_apply_info(?)";
                        String sql_server_sql = "exec proc_select_apply_info ?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        ResultSet rs = preSt.executeQuery();
                        while(rs.next()){
                            MyApplyInfo myApplyInfo=new MyApplyInfo();
                            myApplyInfo.setObject_id(rs.getInt("object_id"));
                            myApplyInfo.setObject_name(rs.getString("object_name"));
                            myApplyInfo.setUser_id(rs.getInt("user_id"));
                            myApplyInfo.setNickname(rs.getString("nickname"));
                            myApplyInfo.setContact_information(rs.getString("contact_information"));
                            myApplyInfoList.add(myApplyInfo);
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
        MyApplyInfoAdapter myApplyInfoAdapter=new MyApplyInfoAdapter(ApplyInfoActivity.this,R.layout.apply_info_item,myApplyInfoList);
        list_view_apply_info.setAdapter(myApplyInfoAdapter);
    }

    void initEvent(){
        refreshLayout_user_apply_info.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                refreshlayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout_user_apply_info.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initData();
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });

    }
}
