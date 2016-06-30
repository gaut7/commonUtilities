package com.mindbowser.CommonUtilities.service;


import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import org.dozer.Mapper;
import com.mindbowser.CommonUtilities.Exception.UserException;
import com.mindbowser.CommonUtilities.dao.UserDao;
import com.mindbowser.CommonUtilities.dto.EmailDTO;
import com.mindbowser.CommonUtilities.dto.UserDTO;
import com.mindbowser.CommonUtilities.model.UserModel;
import com.mindbowser.CommonUtilities.util.ApplicationBeanUtil;
import com.mindbowser.CommonUtilities.util.EmailSender;
import com.mindbowser.CommonUtilities.util.LocaleConverter;
import com.mindbowser.CommonUtilities.util.PasswordUtil;
import com.mindbowser.CommonUtilities.util.ResourceManager;
import com.mindbowser.CommonUtilities.util.TokenGenerator;
import com.mindbowser.CommonUtilities.util.UserDetailValidation;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.DATA_MISSING_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.NOT_FOUND;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_MIN_LENGTH;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_NOT_REGISTERED;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER_ALREADY_EXIST_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_AND_CONFIRMATION_PASSWORD_DOES_NOT_MATCH_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_LENGTH_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_MISMATCH;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.EMAIL_SUBJECT;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_MAX_LENGTH;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.WRONG_OLD_PASSWORD_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.PASSWORD_NOT_REGISTERED;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.SOMETHING_WRONG;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.FACEBOOKID_ALREADY_EXIST;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.FACEBOOK_LOGIN_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.TWITTERID_ALREADY_EXIST;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.TWITTERID_LOGIN_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.GOOGLEPLUSID_ALREADY_EXIST;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.GOOGLEPLUSID_LOGIN_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.CLOUD_FRONT_URL;



public class UserServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	
	@Autowired
	private PasswordUtil passwordUtil;

	@Autowired
	private Mapper dozerMapper;

	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public UserModel getUserByAuthToken(String authToken) {
		UserModel userModel = null;
		UserDTO userDtos = userDao.getUserByAuthToken(authToken);
		if(userDtos!=null)
		{
			userModel = dozerMapper.map(userDtos, UserModel.class);	
		}
		return userModel;
	}

	/**
	 * This method saves user details in database
	 * 
	 * @param userModel
	 * @param request
	 * @throws UserException
	 */
	// @Override
	public UserModel saveUser(UserModel userModel, HttpServletRequest request) throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		EmailDTO emailDTO = new EmailDTO();
		String authToken = null;
		UserDTO userDTO2 = new UserDTO();;
		try {
		
			/*####################Input field validations#################*/
			
		/*	if (userModel.getEmailId() == null || userModel.getName() == null || userModel.getGender() == null || userModel.getPassword() == null) {
				logger.error(ResourceManager.getMessage(DATA_MISSING_EXCEPTION, null, NOT_FOUND, locale));
				throw new UserException(
						ResourceManager.getMessage(DATA_MISSING_EXCEPTION, null, NOT_FOUND, locale));
			}
			if(userModel.getPassword().length() <=Integer.parseInt(ResourceManager.getProperty(PASSWORD_MIN_LENGTH)) || userModel.getPassword().length() >= Integer.parseInt( ResourceManager.getProperty(PASSWORD_MAX_LENGTH)))
			{
				logger.error(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
				throw new UserException(ResourceManager.getMessage(
						PASSWORD_LENGTH_EXCEPTION, null, NOT_FOUND, locale));
			}else
			{
				if(!userModel.getPassword().equals(userModel.getConfirmPassword()))
				{
					logger.error(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
					throw new UserException(ResourceManager.getMessage(
							PASSWORD_AND_CONFIRMATION_PASSWORD_DOES_NOT_MATCH_EXCEPTION, null, NOT_FOUND, locale));
				}	
			}*/
			
			UserDetailValidation userValidation = new UserDetailValidation();
			userValidation.validateEmail(userModel.getEmailId());
			userValidation.validateName(userModel.getName());

			userDTO2 = userDao.checkEmail(userModel.getEmailId());
			UserDTO userDTO  = new UserDTO();
			if (userDTO2 != null) {
			if(userModel.getFacebookId()!=null && !userModel.getFacebookId().isEmpty() )
			{
				if(userDTO2.getFacebookId()!=null)
				{
					if(userDTO2.getFacebookId().equals(userModel.getFacebookId()))
					{
						logger.error(ResourceManager.getMessage(FACEBOOKID_ALREADY_EXIST, null, NOT_FOUND, locale));
						throw new UserException(ResourceManager.getMessage(
								FACEBOOKID_ALREADY_EXIST, null, NOT_FOUND, locale));
					}else
					{
						userDTO2.setFacebookId(userModel.getFacebookId());
					}
				}else
				{
					userDTO2.setFacebookId(userModel.getFacebookId());
				}
			}
				else if (userModel.getTwitterId()!=null && !userModel.getTwitterId().isEmpty()) {
			if(userDTO2.getTwitterId()!=null)
			{
				if(userDTO2.getTwitterId().equals(userModel.getTwitterId()))
				{
					logger.error(ResourceManager.getMessage(TWITTERID_ALREADY_EXIST, null, NOT_FOUND, locale));
					throw new UserException(ResourceManager.getMessage(
							TWITTERID_ALREADY_EXIST, null, NOT_FOUND, locale));
				}else
				{
					userDTO2.setTwitterId(userModel.getTwitterId());
				}
			}else
			{
				userDTO2.setTwitterId(userModel.getTwitterId());
			}
					
				}
				else if (userModel.getGooglePlusId()!=null && !userModel.getGooglePlusId().isEmpty()) {
				if(userDTO2.getGooglePlusId()!=null)
				{
					if(userDTO2.getGooglePlusId().equals(userModel.getGooglePlusId()))
					{
						logger.error(ResourceManager.getMessage(GOOGLEPLUSID_ALREADY_EXIST, null, NOT_FOUND, locale));
						throw new UserException(ResourceManager.getMessage(
								GOOGLEPLUSID_ALREADY_EXIST, null, NOT_FOUND, locale));
					}else
					{
						userDTO2.setGooglePlusId(userModel.getGooglePlusId());
					}
				}else
				{
					userDTO2.setGooglePlusId(userModel.getGooglePlusId());
				}
					
				}
				else if (userModel.getPassword()!=null && !userModel.getPassword().isEmpty()) 
				{
					if(userDTO2.getPassword()!=null)
					{
						logger.error(ResourceManager.getMessage(USER_ALREADY_EXIST_EXCEPTION, null, NOT_FOUND, locale));
						throw new UserException(ResourceManager.getMessage(
								USER_ALREADY_EXIST_EXCEPTION, null, NOT_FOUND, locale));
					}else
					{
						String password = passwordUtil.encode(userModel.getPassword());
						userDTO2.setPassword(password);
					}
				}else
				{
					logger.error(ResourceManager.getMessage(SOMETHING_WRONG, null, NOT_FOUND, locale));
					throw new UserException(ResourceManager.getMessage(
							SOMETHING_WRONG, null, NOT_FOUND, locale));
				}
			}
			else
			{
				userDTO2 = dozerMapper.map(userModel, UserDTO.class);
				
				if(userModel.getPassword()!=null && !userModel.getPassword().isEmpty())
				{
					PasswordUtil passwordUtil =new PasswordUtil();
					String encryptedPassword = passwordUtil.encode(userModel.getPassword());
					userDTO2.setPassword(encryptedPassword);
				}
				
				authToken = TokenGenerator.generateToken(userModel.getEmailId());
				userDTO2.setAuthToken(authToken);
				
				// To save updated time
				java.util.Date javaDate = new java.util.Date();
				long javaTime = javaDate.getTime();
				java.sql.Timestamp updatedAt = new java.sql.Timestamp(javaTime);
				userDTO2.setUpdatedAt(updatedAt);

				// To save created Time
				java.sql.Timestamp createdAt = new java.sql.Timestamp(javaTime);
				userDTO2.setCreatedAt(createdAt);
				
				// To save object into table.
				userDTO2 = userDao.saveUser(userDTO2);
			
				// set data to email dto
				emailDTO.setTo(userDTO2.getEmailId());
				emailDTO.setPassword(userDTO2.getPassword());
				emailDTO.setUsername(userDTO2.getName());
				emailDTO.setSubject(ResourceManager.getProperty(EMAIL_SUBJECT)+" "+userDTO2.getName());
				try {
					EmailSender emailSender = (EmailSender) ApplicationBeanUtil.getApplicationContext().getBean("mail");
					emailSender.sendRegistrationConfirmationMail(emailDTO, userDTO2);
				} catch (Exception ex) {
					logger.error(ex.getStackTrace(), ex);
				}
				
			}
			
		}catch (UserException ex) {
			logger.error(ex.getStackTrace(), ex);
			throw ex;
		}catch (Exception ex) {
			logger.error(ex.getStackTrace(), ex);
		}
		userModel = dozerMapper.map(userDTO2, UserModel.class);
		if(userModel.getProfilePic()!=null) 
		{
			String profilePicUrl = ResourceManager.getProperty(CLOUD_FRONT_URL)+userModel.getProfilePic();
			userModel.setProfilePic(profilePicUrl);	
		}

		return userModel;
	}

	/**
	 * This method checks whether user registered with application or not and if
	 * exist then allows user to login with valid credentials
	 * 
	 * @param request
	 *            containing user credentials
	 * @param userModel
	 *            containing user details.
	 * @return userModel containing user object
	 * @throws UserException
	 */
	public UserModel logIn(UserModel userModel, HttpServletRequest request) throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
	    PasswordUtil passwordUtil =new PasswordUtil();
		String authToken = TokenGenerator.generateToken(userModel.getEmailId());
		UserDTO user = new UserDTO();
		if(userModel.getFacebookId()!=null && !userModel.getFacebookId().isEmpty())
		{
			/*user = userDao.checkUserByFacebookId(userModel.getFacebookId());*/
			user = userDao.checkUserBySocialId(userModel);
			if(user == null)
			{
				logger.error(ResourceManager.getProperty(FACEBOOK_LOGIN_EXCEPTION));
				throw new UserException(ResourceManager.getMessage(FACEBOOK_LOGIN_EXCEPTION, null, "not.found", locale));
			}
		
		}
		else if (userModel.getGooglePlusId()!=null && !userModel.getGooglePlusId().isEmpty()) {
			/*user = userDao.checkUserByGooglePlusId(userModel.getGooglePlusId());*/
			user = userDao.checkUserBySocialId(userModel);
			if(user == null)
			{
				logger.error(ResourceManager.getProperty(GOOGLEPLUSID_LOGIN_EXCEPTION));
				throw new UserException(ResourceManager.getMessage(GOOGLEPLUSID_LOGIN_EXCEPTION, null, "not.found", locale));
			}
		
		}else if (userModel.getTwitterId()!=null && !userModel.getTwitterId().isEmpty() ) {
			/*user = userDao.checkUserByTwitterId(userModel.getTwitterId());*/
			user = userDao.checkUserBySocialId(userModel);
			if(user == null)
			{
				logger.error(ResourceManager.getProperty(TWITTERID_LOGIN_EXCEPTION));
				throw new UserException(ResourceManager.getMessage(TWITTERID_LOGIN_EXCEPTION, null, "not.found", locale));
			}
		
		}else 
			{
			if(userModel.getEmailId()!=null && !userModel.getEmailId().isEmpty()) {
				user = userDao.checkEmail(userModel.getEmailId());
				if (user != null) {
					if(!passwordUtil.matches(userModel.getPassword(), user.getPassword()))
					{
						throw new UserException(ResourceManager.getMessage(PASSWORD_MISMATCH, null, "not.found", locale));	
					}
				}else
				{
					logger.error(ResourceManager.getProperty(USER_NOT_REGISTERED));
					throw new UserException(ResourceManager.getMessage(USER_NOT_REGISTERED, null, "not.found", locale));
				}
			}
		}
		user.setAuthToken(authToken);
		 if( userModel.getDeviceToken()!=null && !userModel.getDeviceToken().isEmpty())
		  {
			user.setDeviceToken(userModel.getDeviceToken());	
		  }
		 userModel = dozerMapper.map(user, UserModel.class);
		  if(!userModel.getProfilePic().isEmpty() && userModel.getProfilePic()!=null) 
			{
				String profilePicUrl = ResourceManager.getProperty(CLOUD_FRONT_URL)+userModel.getProfilePic();
				userModel.setProfilePic(profilePicUrl);	
			}
		 return userModel;
	}

	/**
	 * This method sends an mail containing user's password.
	 * 
	 * @param request
	 * @param email
	 * @return message containing password sent to user or not
	 * @throws UserException
	 */
	// @Override
	public void forgotPassword(HttpServletRequest request, UserModel userModel) throws UserException,Exception {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		EmailDTO emailDTO = new EmailDTO();

		try {
			
		UserDTO retrievedUser = userDao.checkEmail(userModel.getEmailId());
		if (retrievedUser == null) {
			logger.error(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
			throw new UserException(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
		}
		else
		{
			if(retrievedUser.getPassword().length()<1)
			{
				logger.error(ResourceManager.getMessage(PASSWORD_NOT_REGISTERED, null, NOT_FOUND, locale));
				throw new UserException(ResourceManager.getMessage(PASSWORD_NOT_REGISTERED, null, NOT_FOUND, locale));
			}
		}
		
		PasswordUtil passwordUtil = new PasswordUtil();
		emailDTO.setTo(retrievedUser.getEmailId());
		emailDTO.setEmailAsUserName(retrievedUser.getEmailId());
		String tempPassword = TokenGenerator.generateToken(retrievedUser.getEmailId());
		int passwordlimit = Integer.parseInt(ResourceManager.getProperty(PASSWORD_MIN_LENGTH));
		String password = tempPassword.substring(0,passwordlimit);
		retrievedUser.setPassword(passwordUtil.encode(password));
		emailDTO.setPassword(password);
		emailDTO.setUsername(retrievedUser.getName());
		EmailSender emailSender = (EmailSender) ApplicationBeanUtil.getApplicationContext().getBean("mail");
		emailSender.sendForgotPasswordMail(emailDTO);

		}
		catch(UserException exception)
		{
			logger.error(exception.getMessage());
			throw exception;
		}
		catch (Exception e) {
			logger.error(e.getMessage() + e.getStackTrace());
			throw e;
		}

	}

	/**
	 * This method is used to edit current user's profile.
	 * 
	 * @param request
	 *            containing user credentials
	 * @param userModel
	 *            containing user details.
	 * @return userModel containing user object
	 * @throws UserException
	 */
	public UserModel editProfile(HttpServletRequest request, UserModel userModel) throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		UserModel authUser = (UserModel) request.getAttribute(USER);
		UserDTO userDTO = userDao.getUserUsingAuthToken(authUser.getAuthToken());
		if (userDTO != null) {
			if(userModel.getName()!= null && !userModel.getName().isEmpty())
			{
				userDTO.setName(userModel.getName());
			}
			if(userModel.getGender()!= null && !userModel.getGender().isEmpty())
			{
				userDTO.setGender(userModel.getGender());				
			}
			if(userModel.getProfilePic()!= null && !userModel.getProfilePic().isEmpty())
			{
				userDTO.setProfilePic(userModel.getProfilePic());				
			}
		}
		java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp updatedAt = new java.sql.Timestamp(javaTime);
		userDTO.setUpdatedAt(updatedAt);
	    userModel = dozerMapper.map(userDTO, UserModel.class);
	    if(!userModel.getProfilePic().isEmpty() && userModel.getProfilePic()!=null) 
		{
			String profilePicUrl = ResourceManager.getProperty(CLOUD_FRONT_URL)+userModel.getProfilePic();
			userModel.setProfilePic(profilePicUrl);	
		}
		return userModel;
	}

	
	/**
	 * This method is used to logout user.
	 * 
	 * @param request
	 *            containing user credentials
	 * @param userModel
	 *            containing user details.
	 * @return userModel containing user object
	 * @throws UserException
	 */
	@Override
	public void logOut(HttpServletRequest request)
			throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		
		try
		{
			UserModel authUser = (UserModel) request.getAttribute(USER);
			UserDTO userDTO = userDao.getUserUsingAuthToken(authUser.getAuthToken());
			if (userDTO != null) {
				userDTO.setAuthToken(null);
				userDTO.setDeviceToken(null);
			}
			else
			{
				logger.error(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
				throw new UserException(ResourceManager.getMessage(USER_NOT_REGISTERED, null, NOT_FOUND, locale));
			}
	
		}
		catch(UserException exception)
		{
			logger.error(exception.getMessage());
			throw exception;
		}
		catch (Exception e) {
			logger.error(e.getMessage() + e.getStackTrace());
			throw e;
		}
	}

	
	
	@Override
	public void changePassword(HttpServletRequest request, UserModel userModel)
			throws UserException {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		UserModel authUser = (UserModel) request.getAttribute(USER);
		UserDTO userDTO = userDao.getUserUsingAuthToken(authUser.getAuthToken());
		PasswordUtil passwordUtil = new PasswordUtil();
		if (userDTO != null) {
			
			if (passwordUtil.matches(userModel.getOldPassword(), userDTO.getPassword())) 
			{
				if ((userModel.getNewPassword().equals(userModel.getConfirmPassword()))) 
				{
					String password = passwordUtil.encode(userModel.getNewPassword());
					userDTO.setPassword(password);
				}else
				{
					logger.error(ResourceManager.getMessage(PASSWORD_AND_CONFIRMATION_PASSWORD_DOES_NOT_MATCH_EXCEPTION, null, NOT_FOUND, locale));
					throw new UserException(ResourceManager.getMessage(
							PASSWORD_AND_CONFIRMATION_PASSWORD_DOES_NOT_MATCH_EXCEPTION, null, NOT_FOUND, locale));
				}
			} else {
				logger.error(ResourceManager.getMessage(WRONG_OLD_PASSWORD_EXCEPTION, null, NOT_FOUND, locale));
				throw new UserException(
						ResourceManager.getMessage(WRONG_OLD_PASSWORD_EXCEPTION, null, NOT_FOUND, locale));
			}

		}
	}
	
}