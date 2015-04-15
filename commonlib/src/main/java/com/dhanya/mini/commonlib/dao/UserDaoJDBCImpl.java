package com.dhanya.mini.commonlib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dhanya.mini.commonlib.domain.UserDomain;
import com.dhanya.mini.commonlib.exception.DbException;
import com.dhanya.mini.commonlib.model.UserModel;
import com.mysql.jdbc.Connection;

/** 
 * User DAO JDBC implementation.
 * 	
 * @author Leela
 */
public class UserDaoJDBCImpl implements UserDao {
	
	private static final Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

	private JdbcConnectionPool pool;
	
	private static final String INSERT_INTO_USER_QUERY = "INSERT INTO user(uuid,firstname,lastname,gender,googleId) VALUES (?,?,?,?,?) ";
	
	private static final String SELECT_FROM_USER_QUERY = "SELECT * FROM user WHERE googleId = ? ";
	
	@Autowired
	public UserDaoJDBCImpl(JdbcConnectionPool pool){
		this.pool = pool;
	}

	public UserModel create(UserDomain userDomain) {
		Connection conn = null;
		UserModel userModel=null;
		try {
			conn = pool.getConnectionFromPool();
			PreparedStatement preparedStatement = 
					conn.prepareStatement(INSERT_INTO_USER_QUERY);
					
			preparedStatement.setString(1, userDomain.getUuid());
			preparedStatement.setString(2, userDomain.getFirstName());
			preparedStatement.setString(3, userDomain.getLastName());
			preparedStatement.setString(4, userDomain.getGender());
			preparedStatement.setString(5, userDomain.getGoogleId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			userModel = new UserModel();
			userModel.setFirstName(userDomain.getFirstName());
			userModel.setLastName(userDomain.getLastName());
			userModel.setGender(userDomain.getGender());
			userModel.setUuid(userDomain.getUuid());
		
		 } catch (SQLException ex) {
			log.error("SQL exception thrown", ex);
			throw new DbException("DataBase exception thrown", ex);
		 } 
			
		  finally {
		  if (conn != null) {
			 try {
				 conn.close();
			     } catch (SQLException ex) {
			        log.error("SQL exception", ex);
			        throw new DbException("DataBase exception thrown", ex);
			     }
	         }				
         }
		return userModel;
	}
   
	public UserModel getByUserGoogleId(String userGoogleId) {	
		Connection conn = null;
		UserModel userModel=null;
		
		try{
			conn = pool.getConnectionFromPool();
		PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FROM_USER_QUERY);
		preparedStatement.setString(1, userGoogleId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.isBeforeFirst()) {
			resultSet.close();
			preparedStatement.close();
		  } else {
		     while (resultSet.next()) {
              userModel = new UserModel();
		      userModel.setFirstName(resultSet.getString("firstname"));
		      userModel.setLastName(resultSet.getString("lastname"));
		      userModel.setUuid(resultSet.getString("uuid"));
		      userModel.setGender(resultSet.getString("gender"));
			 }
		  }
		  resultSet.close();
	      preparedStatement.close();     
		} catch (SQLException ex) {
			log.error("SQL exception thrown", ex);
			throw new RuntimeException(ex);
		} 
		return userModel;	
	}
}