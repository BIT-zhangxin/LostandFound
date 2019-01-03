package com.example.lostandfound.component;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnectionHelper {

    //MySQL驱动方式
    //驱动路径
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";

    //数据库地址
    private static final String DBURL ="jdbc:mysql://47.95.203.128:3306/LostandFound"
    +"?useUnicode=true&characterEncoding=utf-8&connectTimeout=5000&socketTimeout=5000";

//    //数据库地址
//    private static final String DBURL ="jdbc:mysql://192.168.137.1:3306/LostandFound"
//    +"?useUnicode=true&characterEncoding=utf-8&connectTimeout==5000&socketTimeout=5000";

    //数据库登录用户名
    private static final String DBUSER = "LostandFound";

    //数据库用户密码
    private static final String DBPASSWORD = "LostandFound";

//    //SQL Server驱动方式
//    //驱动路径
//    private static final String DBDRIVER = "net.sourceforge.jtds.jdbc.Driver";
//    //数据库地址
//    private static final String DBURL =
//            "jdbc:jtds:sqlserver://192.168.137.1:1433;" +
//            "DataBaseName=LostandFound;loginTimeout=5;socketTimeout=5";
//    //数据库登录用户名
//    private static final String DBUSER = "android_lostandfound";
//    //数据库用户密码
//    private static final String DBPASSWORD = "android_lostandfound";

    static{
        try{
            Class.forName(DBDRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
