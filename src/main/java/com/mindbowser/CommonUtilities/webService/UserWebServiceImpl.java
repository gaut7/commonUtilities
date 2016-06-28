package com.mindbowser.CommonUtilities.webService;

// Built-in packages
// User defined messages/constants.

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindbowser.CommonUtilities.Exception.UserException;
import com.mindbowser.CommonUtilities.model.ResponseModel;
import com.mindbowser.CommonUtilities.model.UserModel;
import com.mindbowser.CommonUtilities.service.UserService;
import com.mindbowser.CommonUtilities.util.LocaleConverter;
import com.mindbowser.CommonUtilities.util.ResourceManager;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.NOT_FOUND;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_LOGGED_IN_SUCCESSFULLY;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_SENT_SUCCESSFULLY;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_UPDATE_SUCCESS_MESSAGE;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_LOGOUT_SUCCESS_MESSAGE;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_PASSWORD_CHANGE_SUCCESS_MESSAGE;




/**
 * 
 * @author MB User : Gaurav Tripathi.
 *
 */
@Component
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Path("/user")
@Api(value="UserServices",description="User related operations like login,registration,edit profile etc." )

public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = Logger.getLogger(UserWebServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private Mapper dozerMapper;
	
	@POST
	@Path("/getUserByAuthToken")
	@ApiOperation(value="usermodel" ,notes="This method is used to get user by its authtoken")
	public UserModel getUser(@QueryParam("token") String authToken) {
		UserModel userModel = userService.getUserByAuthToken(authToken);
		return userModel;
	}

	@POST
	@Path("/signUp")
	@ApiOperation(value="usermodel" ,notes="This method is used to register new user")
	public ResponseModel signUpUser(@Context HttpServletRequest request,
			@Context HttpServletResponse response, UserModel userModel)
			throws UserException {
		logger.info("<------inside signUpUser start------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		ResponseModel responseModel = null;
		userModel = userService.saveUser(userModel, request);
		userModel.setPassword(null);
		userModel.setConfirmPassword(null);
		responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModel);
		responseModel.setMessage(ResourceManager.getMessage(USER_REGISTERED_SUCCESSFULLY_MESSAGE, null, NOT_FOUND, locale));
		return responseModel;
	}

	@PUT
	@Path("/logIn")
	@ApiOperation(value="usermodel" ,notes="This method is used to login to the system with accurate credential")
	@Override
	public ResponseModel logIn(@Context HttpServletRequest request,
			@Context HttpServletResponse response, UserModel userModel)
			throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		ResponseModel responseModel = null;
		userModel = userService.logIn(userModel, request);
		responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModel);
		responseModel.setMessage(ResourceManager.getMessage(
				USER_LOGGED_IN_SUCCESSFULLY, null, NOT_FOUND, locale));
		return responseModel;
	}

	@POST
	@Path("/forgot-password")
	@ApiOperation(value="usermodel" ,notes="This method is used to retrive password if user forgot it.")
	@Override
	public ResponseModel forgotPassword(@Context HttpServletRequest request,
			@Context HttpServletResponse response, UserModel userModel)
			throws UserException,Exception {
		logger.info("<------Forgot Password start------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		userService.forgotPassword(request, userModel);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(ResourceManager.getMessage(
				PASSWORD_SENT_SUCCESSFULLY, null, "not.found", locale));
		return responseModel;
	}


	@POST
	@Path("/editProfile")
	@ApiOperation(value="usermodel" ,notes="This method is used to edit existing user details.")
	@Override
	public ResponseModel editProfile(@Context HttpServletRequest request,
			UserModel userModel) throws UserException {

		logger.info("<------Edit Profile start------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		UserModel userModel2 = userService.editProfile(request, userModel);
		userModel2.setAuthToken(null);
		userModel2.setPassword(null);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModel2);
		responseModel.setMessage(ResourceManager.getMessage(
				USER_UPDATE_SUCCESS_MESSAGE, null, "not.found", locale));
		return responseModel;
	}

	@PUT
	@Path("/logOut")
	@ApiOperation(value="usermodel" ,notes="This method is used to make the logout operations")
	@Override
	public ResponseModel logOut(@Context HttpServletRequest request)
			throws UserException {
		logger.info("<------logout user------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		userService.logOut(request);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(ResourceManager.getMessage(
				USER_LOGOUT_SUCCESS_MESSAGE, null, "not.found", locale));
		return responseModel;
	}

	@POST
	@Path("/changePassword")
	@ApiOperation(value="usermodel" ,notes="This method is used to change the password")
	@Override
	public ResponseModel changePassword(@Context HttpServletRequest request,
			UserModel userModel) throws UserException {
		logger.info("<------change password------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		userService.changePassword(request, userModel);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(ResourceManager.getMessage(
				USER_PASSWORD_CHANGE_SUCCESS_MESSAGE, null, "not.found", locale));
		return responseModel;
	}


}