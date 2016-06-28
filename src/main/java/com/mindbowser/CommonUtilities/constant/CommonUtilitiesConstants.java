package com.mindbowser.CommonUtilities.constant;

public class CommonUtilitiesConstants {
	public static final String POST = "POST";
	public static final String AUTHTOKEN = "authToken";
	public static final String SIGN_UP_USER_URL = "/user/signUp";
	public static final String SWAGGER_DOC = "/sw/api-docs";
	public static final String LOGIN_URL = "/user/logIn";
	public static final String FORGOT_PASSWORD = "/user/forgot-password";
	public static final String AUTHTOKEN_KEYWORD = "authToken= ";
	public static final String EMAIL_SUBJECT = "email.subject";
	public static final String FAIL = "fail";
	public static final String USER = "user";
	public static final String RETURN_SUCCESS = "return.success";
	public static final String RETURN_ERROR = "return.error";
	public static final String CLOUD_FRONT_URL = "cloud.front.url";
	
	
	public static final String PASSWORD_MIN_LENGTH = "password.min.length";
	public static final String PASSWORD_MAX_LENGTH = "password.max.length";
	
	// Paths
	public static final String REGISTRATION_CONFIRMATION_EMAIL_PATH = "message.registration.confirmation.email.path";
	public static final String FORGOT_PASSWORD_EMAIL_PATH = "message.forgot.password.email.path";
	
	// Success Messages
	public static final String REGISTRATION_CONFIRMATION_EMAIL_BODY = "message.registration.confirmation.email.body";
	public static final String FORGOT_PASSWORD_EMAIL_BODY = "message.forgot.password.email.body";
	public static final String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "message.user.successfully.registered";
	public static final String USER_DETAILS_MESSAGE = "message.user.details.sent";
	public static final String USER_UPDATE_SUCCESS_MESSAGE = "message.user.details.updated";
	public static final String USER_LOGOUT_SUCCESS_MESSAGE = "message.user.logout.success";
	public static final String USER_PASSWORD_CHANGE_SUCCESS_MESSAGE = "message.user.password.change.success";
	
	
	
	// Exception Messages
	public static final String NOT_FOUND = "not.found";
	public static final String REGISTER_FAILED_EXCEPTION = "exception.message.register.fail";
	public static final String INVALID_USER_TOKEN = "Invalid User Token.";
	public static final String USER_NOT_REGISTERED = "exception.message.user.not.registered";
	public static final String LOG_IN_FAILED = "exception.message.user.login.fail";
	public static final String PASSWORD_MISMATCH = "exception.message.password.mismatch";
	public static final String USER_LOGGED_IN_SUCCESSFULLY = "message.user.login.successfully";
	public static final String PASSWORD_SENT_SUCCESSFULLY = "message.password.sent.successfully";
	public static final String USER_ALREADY_EXIST_EXCEPTION = "exception.message.email.already.exists";
	public static final String PASSWORD_AND_CONFIRMATION_PASSWORD_DOES_NOT_MATCH_EXCEPTION = "exception.password.confirmation.password.does.not.match";
	public static final String WRONG_OLD_PASSWORD_EXCEPTION = "exception.wrong.old.password";
	public static final String DATA_MISSING_EXCEPTION = "exception.data.missing";
	public static final String EMAIL_IS_NOT_VALID_EXCEPTION = "exception.email.id.not.valid";
	public static final String USER_NOT_REGISTER_TO_MIRROR_EXCEPTION = "exception.user.not.register.to.mirror";
	public static final String PASSWORD_LENGTH_EXCEPTION = "exception.password.length";
	public static final String NAME_IS_NOT_VALID_EXCEPTION = "exception.user.name.format";
	public static final String NAME_NO_CHARACTER_EXCEPTION = "exception.user.name.character";
	public static final String PASSWORD_NOT_REGISTERED = "exception.password.not.register";
	public static final String SOMETHING_WRONG= "exception.something.wrong";
	
	public static final String FACEBOOKID_ALREADY_EXIST = "exception.facebook.already.exist";
	public static final String FACEBOOK_LOGIN_EXCEPTION= "exception.facebook.login.fail";
	
	public static final String TWITTERID_ALREADY_EXIST = "exception.twitterid.already.exist";
	public static final String TWITTERID_LOGIN_EXCEPTION= "exception.twitter.login.fail";
	
	public static final String GOOGLEPLUSID_ALREADY_EXIST = "exception.googleplus.already.exist";
	public static final String GOOGLEPLUSID_LOGIN_EXCEPTION= "exception.googleplus.login.fail";
	
}
