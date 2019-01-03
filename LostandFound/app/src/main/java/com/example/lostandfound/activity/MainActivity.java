package com.example.lostandfound.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lostandfound.fragment.InformationFragment;
import com.example.lostandfound.fragment.MessageFragment;
import com.example.lostandfound.fragment.PublishFragment;
import com.example.lostandfound.R;

public class MainActivity extends AppCompatActivity {

    private Fragment messageFragment;
    private Fragment publishFragment;
    private Fragment informationFragment;
    private Fragment mContent;//当前的fragment

    FragmentManager fragmentManager;

    private TextView tv_main_title;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            loadFragment(item.getItemId());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initComponent();
        initEvent();
    }

    private void initComponent(){
        tv_main_title = findViewById(R.id.tv_main_title);
        navigation = findViewById(R.id.navigation);
        messageFragment=new MessageFragment();
        publishFragment=new PublishFragment();
        informationFragment=new InformationFragment();
        fragmentManager=getSupportFragmentManager();

        if(mContent==null){
            tv_main_title.setText(R.string.main_title_message);
            mContent=messageFragment;
            fragmentManager.beginTransaction()
                    .add(R.id.main_framelayout,mContent)
                    .commit();
        }
    }

    private void initEvent(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //loadFragment(R.id.navigation_message);
    }

    private void loadFragment(int itemId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        switch (itemId){
            case R.id.navigation_message:
                tv_main_title.setText(R.string.main_title_message);
                if (!messageFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,messageFragment).commit();
                } else {
                    transaction.hide(mContent).show(messageFragment).commit();
                }
                mContent=messageFragment;
                break;
            case R.id.navigation_publish:
                tv_main_title.setText(R.string.main_title_publish);
                if (!publishFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,publishFragment).commit();
                } else {
                    transaction.hide(mContent).show(publishFragment).commit();
                }
                mContent=publishFragment;
                break;
            case R.id.navigation_information:
                tv_main_title.setText(R.string.main_title_information);
                if (!informationFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,informationFragment).commit();
                } else {
                    transaction.hide(mContent).show(informationFragment).commit();
                }
                mContent=informationFragment;
                break;
        }
    }
}
