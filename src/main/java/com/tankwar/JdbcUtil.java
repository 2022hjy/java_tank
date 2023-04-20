package com.tankwar;

import java.sql.*;

public class JdbcUtil {
    private static String driver= "com.mysql.jdbc.Driver";
    private static String url= "jdbc:mysql://127.0.0.1:3306/player?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static String user= "root";
    private static String password= "";

    //1.注册驱动
    static{

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //2.获得连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    //3.释放资源
    public static void close(Connection con,Statement sta,ResultSet res){
        if(res!=null){
            try {
                res.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(sta!=null){
            try {
                sta.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection con,Statement sta){
        close(con, sta, null);
    }

    public static void close(Connection con,PreparedStatement sta,ResultSet res){
        close(con, (Statement)sta, res);
    }
    public static void close(Connection con,PreparedStatement sta){
        close(con, (Statement)sta, null);
    }

    public static void main(String[] args) {
        System.out.println(JdbcUtil.getConnection());
    }
}


