package com.icsys.batch.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import com.icsys.platform.dao.JNDIUtil;

public abstract class DBUtils {
	
	public static DB getDatabaseType() {
        DataSource ds;
		try {
			ds = (DataSource)JNDIUtil.getInitialContext().lookup(SystemConstans.DB_SOURCE_NAME);
	        Connection con = ds.getConnection();
	        DatabaseMetaData meta = con.getMetaData();
	        DB type;
			String proName =  meta.getDatabaseProductName().toLowerCase();
			if (proName.equals("h2")) {
				type = DB.H2;
			} else if (proName.startsWith("postgresql")) {
				type = DB.PSQL;
			} else if (proName.startsWith("mysql")) {
				type = DB.MYSQL;
			} else if (proName.startsWith("oracle")) {
				type = DB.ORACLE;
			} else if (proName.startsWith("db2")) {
				type = DB.DB2;
			} else if (proName.startsWith("microsoft sql")) {
				type = DB.SQLSERVER;
			} else {
				type = DB.OTHER;
			}
			return type;
		} catch (Exception e) {
			return DB.OTHER;
		}

	}

}
