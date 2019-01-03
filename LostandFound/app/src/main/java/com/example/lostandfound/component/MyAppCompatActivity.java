package com.example.lostandfound.component;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public abstract class MyAppCompatActivity extends AppCompatActivity {

    //密码可见
    protected void passwordVisibility(ImageView imageView, EditText editText){
        if (imageView.isSelected()) {
            imageView.setSelected(false);
            //密码不可见
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            imageView.setSelected(true);
            //密码可见
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    //设置顶部状态栏颜色(大于等于android 21版本)
    protected void setStatusBarColor(Activity activity, int statusColor){
        Window window=activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    //重写点击事件的处理方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard(ev);
        return super.dispatchTouchEvent(ev);
    }

    protected void hideKeyboard(MotionEvent ev){
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //如果不是落在EditText区域,则需要关闭输入法
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //根据EditText所在坐标和用户点击的坐标相对比,来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event){
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            //获取现在拥有焦点的控件view的位置,即EditText
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            //判断我们手指点击的区域是否落在EditText上面,如果不是,则返回true,否则返回false
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
