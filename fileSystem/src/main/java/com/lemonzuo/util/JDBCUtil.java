package com.lemonzuo.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
	//JDBC 驱动
	private String driver;
	//数据库用户名
	public String userName;
	//数据库密码
	public String userPass;
	//JDBC URL
	public String url;
	//Connection对象
	public Connection con;
	//PreparedStatement对象
	public PreparedStatement ps = null;
	//结果集
	public ResultSet result = null;
	//受影响的行数
	int num = 0;
	/**
	 * 数据库链接的初始化
	 */
	public void init() {
		try {
			Properties properties = new Properties();
			InputStream in = JDBCUtil.class.getResourceAsStream("/db.properties");
			properties.load(in);
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			userName = properties.getProperty("jdbc.user");
			userPass = properties.getProperty("jdbc.password");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void getConnection() {
		try {
			init();
			//1、加载驱动
			Class.forName(driver);
			//2、建立连接
			con = DriverManager.getConnection(url, userName, userPass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行查询
	 * @param sql SQL语句
	 * @param obj SQL参数
	 * @return 查询到的结果集
	 */
	@SuppressWarnings("finally")
	public ResultSet executeQuery(String sql,Object...obj) {
			try {
				ps = con.prepareStatement(sql);
				for(int i = 0;i < obj.length;i++) {
					ps.setObject(i + 1, obj[i]);
				}
				result = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				return result;
			}
	}
	
	/**
	 * 执行增删改操作
	 * @param sql SQL语句
	 * @param obj SQL参数
	 * @return 受影响的行数
	 */
	@SuppressWarnings("finally")
	public int executeUpdate(String sql,Object...obj) {
		try {
			ps = con.prepareStatement(sql);
			for(int i = 0;i < obj.length;i++) {
				ps.setObject(i + 1, obj[i]);
			}
			num = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			return num;
		}
	}
	
	/**
	 * 释放资源
	 */
	public void close() {
		//关闭结果集
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//关闭PreparedStatement
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//关闭连接
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
