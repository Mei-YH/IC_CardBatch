package com.icys.batch.test.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author JDBC������ ���ڹ������ݿ����ӣ����õ���ģʽ��
 */
public final class Test {
	// ��urlΪȱʡ��ʽ��ʡ���������˿ڣ�
	// ����Ϊ��jdbc:mysql//localhost:3306/test
	static String url = "jdbc:mysql:///test";
	static String name = "root";
	static String password = "sli";
	static Connection conn = null;
	private static Test jdbcUtilSingle = null;

	public static Test getInitJDBCUtil() {
		if (jdbcUtilSingle == null) {
			// ������� ��ֹ�̲߳���
			synchronized (Test.class) {
				if (jdbcUtilSingle == null) {
					jdbcUtilSingle = new Test();
				}
			}
		}
		return jdbcUtilSingle;
	}

	private Test() {
	}

	// ͨ����̬�����ע�����ݿ���������֤ע��ִֻ��һ��
	static {
		try {
			// ע�����������·�ʽ��
			// 1.ͨ������������ע������������ע�����Σ����һ�������������������಻�����Ǿͱ����ˡ�
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			// 2.��3����
			// System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver");// �Ƽ�ʹ�÷�ʽ
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// �������
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url,name,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}

	// �ر�����
	public void closeConnection(ResultSet rs, Statement statement, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

