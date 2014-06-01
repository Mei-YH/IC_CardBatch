package com.icys.batch.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author admin
 * 数据库连接
 */
public class DataBaseUtil {

	/**
	 * @param prmIndex 参数索引
	 * @return PrmValue 参数值
	 */
	public static String sysPrmValue (String prmIndex){
		String sql ="select prm_value from  gr_sys_para  where prm_index="+prmIndex;
		String PrmValue = null;
		Statement stmt;
		try {
			stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql) ; 
			while(rs.next()){   
				PrmValue = rs.getString(1) ;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return PrmValue;
	}


	/**
	 * @return 数据库连接
	 */
	public static Connection getConnection () {

		String url="jdbc:db2://localhost:50000/icmpdb:currentSchema=ICMPDB;";
		String user="db2admin";
		String password="mac**19891220";
		Connection conn = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
