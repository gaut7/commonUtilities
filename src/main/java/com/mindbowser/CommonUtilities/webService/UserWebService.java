package com.mindbowser.CommonUtilities.webService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindbowser.CommonUtilities.Exception.UserException;
import com.mindbowser.CommonUtilities.model.ResponseModel;
import com.mindbowser.CommonUtilities.model.UserModel;


public interface UserWebService {
	UserModel getUser(String authToken);

	ResponseModel signUpUser(HttpServletRequest request, HttpServletResponse response, UserModel userModel)
			throws UserException;

	ResponseModel logIn(HttpServletRequest request, HttpServletResponse response, UserModel userModel)
			throws UserException;

	ResponseModel forgotPassword(HttpServletRequest request, HttpServletResponse response, UserModel userModel)
			throws UserException,Exception;

	ResponseModel editProfile(HttpServletRequest request, UserModel userModel) throws UserException;
	
	ResponseModel logOut(HttpServletRequest request) throws UserException;
	
	ResponseModel changePassword(HttpServletRequest request, UserModel userModel) throws UserException;
	
}
