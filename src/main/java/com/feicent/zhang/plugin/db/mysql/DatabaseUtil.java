package com.feicent.zhang.plugin.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接实体类
 * @author yzuzhang
 */
public class DatabaseUtil {
	
	//数据库驱动
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/zhang?user=root&password=123456";
	
	private DatabaseUtil(){
		super();
	}

	/**
	 * 建立连接
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getMySQLConnection(String url) {
		return getConnection(driver, url, "root", "123456");
	}
	
	public static Connection getMySQLConnection(String url, String user, String pwd) {
		return getConnection(driver, url, user, pwd);
	}
	
	public static Connection getConnection(String driver,String url,String user,String pwd) {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args)
    {
		Connection conn = null;
        try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from china_city_2016 limit 100");       
			while(rs.next())
			{
			    System.out.println(rs.getRow()+" "+rs.getString("NAME")+ ", "+rs.getString("NAME_EN"));
			}
			close(rs);
			close(stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(conn);
		}
    }

	
	public static Statement createStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public static ResultSet executeQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static int executeUpdate(Connection conn, String sql) {
		int row = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			row = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return row;
	}

	public static PreparedStatement prepareStatement(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static PreparedStatement prepareStatement(Connection conn,
			String sql, int autoGeneratedKeys) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql, autoGeneratedKeys);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static void close(PreparedStatement pstmt) {// 可以使用close(Statement
														// stmt)方法,父类引用指向子类对象;
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pstmt = null;
	}

	public static void close(Statement stmt) {
		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stmt = null;
	}

	public static void close(ResultSet rs) {
		if ( null != rs ) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs = null;
	}

	public static void close(Connection conn) {
		if ( null != conn ) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;
	}
	
}
