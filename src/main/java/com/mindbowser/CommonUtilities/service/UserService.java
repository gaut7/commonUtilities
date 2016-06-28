package com.mindbowser.CommonUtilities.service;


import javax.servlet.http.HttpServletRequest;

import com.mindbowser.CommonUtilities.Exception.UserException;
import com.mindbowser.CommonUtilities.model.UserModel;

public interface UserService {
	public UserModel getUserByAuthToken(String authToken);

	public UserModel saveUser(UserModel userModel, HttpServletRequest request) throws UserException;

	public UserModel logIn(UserModel userModel, HttpServletRequest request) throws UserException;
	
	public void logOut(HttpServletRequest request) throws UserException;
	
	public void changePassword(HttpServletRequest request,UserModel userModel) throws UserException;

	public void forgotPassword(HttpServletRequest request, UserModel userModel) throws UserException,Exception;

	public UserModel editProfile(HttpServletRequest request, UserModel userModel) throws UserException;
	
}
