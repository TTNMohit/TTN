package com.ttn.resources;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;

public class DataPooling {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	String JDBC_DB_URL = "jdbc:mysql://127.0.0.1:3306/sys";
	String JDBC_USER = "root";
	String JDBC_PASS = "mysql";

	public DataPooling(String url, String user, String pass) {
		JDBC_DB_URL = url;
		JDBC_USER = user;
		JDBC_PASS = pass;
	}

	private static GenericObjectPool gPool = null;

	@SuppressWarnings("unused")
	public DataSource setUpPool() throws Exception {
		try {
		Class.forName(JDBC_DRIVER);
		gPool = new GenericObjectPool();
		gPool.setMaxActive(2);
		ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL,
				JDBC_USER, JDBC_PASS);
		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf,
				gPool, null, null, false, true);
		return new PoolingDataSource(gPool);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			throw e;
		}
	}

	public GenericObjectPool getConnectionPool() {
		return gPool;
	}
}
