package com.feicent.zhang.util.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;

/**
 * Druid里的工具类
 * @author yzuzhang
 * @date 2017年9月29日 上午9:15:06
 */
public class DruidUtil {
	
	private static final String user="root";
	private static final String pwd="123456";
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/zhang";
    
    public static Connection getConnection(){  
        Connection con= null;
        try {
        	Class.forName(driver);
        	con=DriverManager.getConnection(url, user, pwd); 
            System.out.println("Database connection successfully");  
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {  
            e.printStackTrace();  
        } 
        
        return con;
    }
    
	public static void main(String[] args) {
		String dbType = JdbcConstants.MYSQL;
		Connection conn = null;
		try {
			conn = getConnection();
			String createTableScript = JdbcUtils.getCreateTableScript(conn, dbType);
			System.out.println(createTableScript);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}
	
	public static void close(Connection conn){
		if( conn != null ){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
