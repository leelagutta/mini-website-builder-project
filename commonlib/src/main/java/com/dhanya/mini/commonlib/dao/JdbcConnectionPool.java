package com.dhanya.mini.commonlib.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.Connection;

/**
 * Connection pool for ability to allocate and reuse a 
 * set of db connections for an application.
 * 
 * @author Leela
 */
public class JdbcConnectionPool {

	private DataSource dataSource;

	List<Connection> availableConnections = new ArrayList<Connection>();

	@Autowired
	public JdbcConnectionPool(DataSource dataSource) {
		this.dataSource = dataSource;
		initializeConnectionPool();
	}

	private void initializeConnectionPool() {
        while (!checkIfConnectionPoolIsFull()) {
        	availableConnections.add(createNewConnectionForPool());
		}
	}

	private synchronized boolean checkIfConnectionPoolIsFull() {
       final int MAX_POOL_SIZE = 100;
         if (availableConnections.size() < MAX_POOL_SIZE) {
			return false;
		}
          return true;
	}

	private Connection createNewConnectionForPool() {
		Connection connection = null;
		try {
			connection = (Connection) dataSource.getConnection(); 
		    } catch (SQLException e) {
           e.printStackTrace();
		}
		return connection;

	}

	public synchronized Connection getConnectionFromPool() {
		Connection connection = null;

		if (availableConnections.size() > 0) {
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
			
		}else{
			initializeConnectionPool();
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
		}
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection) {
		availableConnections.add(connection);
	}
}
