package com.feicent.zhang.plugin.db.h2;

public class H2Constans {

	//数据库连接URL，当前连接的是E:/H2目录下的gacl数据库
    public static final String JDBC_URL = "jdbc:h2:D:/Users/H2/mydb";
    
    //连接数据库时使用的用户名
    public static final String USER = "root";
    
    //连接数据库时使用的密码
    public static final String PASSWORD = "123456";
    
    //连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
    public static final String DRIVER_CLASS="org.h2.Driver";
}
