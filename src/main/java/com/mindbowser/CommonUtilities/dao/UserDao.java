package com.mindbowser.CommonUtilities.dao;


import org.hibernate.HibernateException;

import com.mindbowser.CommonUtilities.dto.UserDTO;
import com.mindbowser.CommonUtilities.model.UserModel;

public interface UserDao {

	UserDTO getUserByAuthToken(String authToken);
	UserDTO saveUser(UserDTO user) throws HibernateException;
	public UserDTO checkUserByFacebookId(String facebookId);
	public UserDTO checkUserByTwitterId(String twitterId);
	public UserDTO checkUserByGooglePlusId(String facebookId);
	public UserDTO checkUserBySocialId(UserModel userModel);
	public UserDTO checkEmail(String email);
	public UserDTO getUserUsingAuthToken(String authToken);
	public UserDTO getUserById(int userId) ;

}
