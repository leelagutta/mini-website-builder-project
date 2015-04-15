package com.dhanya.mini.commonlib.dao;

import com.dhanya.mini.commonlib.domain.UserDomain;
import com.dhanya.mini.commonlib.model.UserModel;
/**
 *  User data access object
 *  
 * 	@author Leela
 */
public interface UserDao {
	
	UserModel create(UserDomain userDomain);
	
	UserModel getByUserGoogleId(String userGoogleId);

}