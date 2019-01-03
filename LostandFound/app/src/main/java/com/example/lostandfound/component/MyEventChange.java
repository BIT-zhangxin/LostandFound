package com.example.lostandfound.component;

public class MyEventChange {

    public static String MainEventToString(int main_event_type){
        switch (main_event_type){
            case 1:
                return "失物事件";
            case 2:
                return "拾物事件";
            case 11:
                return "结束的失物事件";
            case 12:
                return "结束的拾物事件";
            default:
                return "";
        }
    }

    public static String SubEventToString(int sub_event_type){
        switch (sub_event_type){
            case 1:
                return "发布";
            case 2:
                return "申请";
            case 3:
                return "同意申请";
            case 4:
                return "拒绝申请";
            case 5:
                return "结束事件";
            default:
                return "";
        }
    }
}
