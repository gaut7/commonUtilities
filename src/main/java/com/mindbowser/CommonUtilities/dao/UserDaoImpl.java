package com.mindbowser.CommonUtilities.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.mindbowser.CommonUtilities.dto.UserDTO;
import com.mindbowser.CommonUtilities.model.UserModel;


/**
 * 
 * @author MB User : Gaurav Tripathi.
 *
 */

@Transactional
public class UserDaoImpl extends BaseDAO implements UserDao{

	Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public UserDTO getUserByAuthToken(String token) throws HibernateException {
		UserDTO userDetail = null;
		try {
			Session session = getCurrentSession();
			userDetail = (UserDTO) session.createCriteria(UserDTO.class).add(Restrictions.eq("authToken", token)).uniqueResult();
		} catch (HibernateException ex) {
			logger.error(ex.getStackTrace(), ex);
			throw ex;
		}
		return userDetail;
	}
	
	
	/**
	 * This method saves user details in database
	 * 
	 * @param user
	 *            object
	 * @return user object containing all user details.
	 * @throws HibernateException
	 */
	//@Override
	public UserDTO saveUser(UserDTO userDTO) throws HibernateException {
		try {
			Session session = getCurrentSession();
			session.persist(userDTO);
		} catch (HibernateException hibernateException) {
			logger.error(hibernateException.getMessage(), hibernateException);
			throw hibernateException;
		}
		return userDTO;
	}

	
	
	/**
	 * This method checks user already signed up to application by facebookid 
	 * 
	 * @return unique result.
	 * @throws HibernateException
	 */
	@Override
	public UserDTO checkUserByFacebookId(String facebookId)
	{
		UserDTO user=null;
		try
		{
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("facebookId", facebookId));
			user = (UserDTO) criteria.uniqueResult();
		} catch (HibernateException exception)
		{
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return user;
	}
	
	
	/**
	 * This method checks user already signed up to application by facebookid 
	 * 
	 * @return unique result.
	 * @throws HibernateException
	 */
	@Override
	public UserDTO checkUserBySocialId(UserModel userModel)
	{
		UserDTO user=null;
		try
		{
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			if(userModel.getFacebookId()!=null && !userModel.getFacebookId().isEmpty())
			{
				criteria.add(Restrictions.eq("facebookId", userModel.getFacebookId()));	
			}else if(userModel.getTwitterId()!=null && !userModel.getTwitterId().isEmpty())
			{
				criteria.add(Restrictions.eq("twitterId", userModel.getTwitterId()));
			}else
			{
				criteria.add(Restrictions.eq("googlePlusId", userModel.getGooglePlusId()));
			}
			
			user = (UserDTO) criteria.uniqueResult();
		} catch (HibernateException exception)
		{
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return user;
	}
	

	/**
	 * This method checks user already signed up to application by twitterid 
	 * 
	 * @return unique user result.
	 * @throws HibernateException
	 */
	@Override
		public UserDTO checkUserByTwitterId(String twitterId)
		{
			UserDTO user=null;
			try
			{
				Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
				criteria.add(Restrictions.eq("twitterId", twitterId));
				user = (UserDTO) criteria.uniqueResult();
			} catch (HibernateException exception)
			{
				logger.error(exception.getMessage(), exception);
				throw exception;
			}
			return user;
		}
	
		

	/**
	 * This method checks user already signed up to application by googlePlusId 
	 * 
	 * @return unique user result.
	 * @throws HibernateException
	 */
		@Override
		public UserDTO checkUserByGooglePlusId(String googlePlusId)
		{
			UserDTO user=null;
			try
			{
				Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
				criteria.add(Restrictions.eq("googlePlusId", googlePlusId));
				user = (UserDTO) criteria.uniqueResult();
			} catch (HibernateException exception)
			{
				logger.error(exception.getMessage(), exception);
				throw exception;
			}
			return user;
		}
		

		/**
		 * This method checks user already signed up to application by emailid 
		 * 
		 * @return unique user result.
		 * @throws HibernateException
		 */
	   @Override
		public UserDTO checkEmail(String email)
		{
			UserDTO user=null;
			try
			{
				Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
				criteria.add(Restrictions.eq("emailId", email));
				user = (UserDTO) criteria.uniqueResult();
			} catch (HibernateException exception)
			{
				logger.error(exception.getMessage(), exception);
				throw exception;
			}
			return user;
		}
	
	/**
	 * This method checks user already signed up to application 
	 * 
	 * @param authToken contains user authentication token
	 * return user unique result
	 * @throws HibernateException
	 */
	@Override
	
	public UserDTO getUserUsingAuthToken(String authToken)
	{
		UserDTO user=null;
		try
		{
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("authToken", authToken));
			user = (UserDTO) criteria.uniqueResult();
		} catch (HibernateException exception)
		{
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return user;
	}

	
	/**
	 * This API will return user record using userId.
	 * 
	 *  @author MB User : Gaurav Tripathi.
	 *  @param int userId
	 *  @return UserDTO object
	 */
	public UserDTO getUserById(int userId)
	{
		UserDTO user=null;
		try
		{
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("userId", userId));
			user = (UserDTO) criteria.uniqueResult();
		} catch (HibernateException exception)
		{
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
		return user;
	}

}
