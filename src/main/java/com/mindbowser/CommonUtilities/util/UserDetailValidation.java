package com.mindbowser.CommonUtilities.util;

import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.EMAIL_IS_NOT_VALID_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.NOT_FOUND;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.NAME_IS_NOT_VALID_EXCEPTION;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.NAME_NO_CHARACTER_EXCEPTION;

import com.mindbowser.CommonUtilities.Exception.UserException;




public class UserDetailValidation {
	
	
	public void validateEmail(String email) throws UserException {
		  try {
		   String EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
		   Boolean b = email.matches(EMAIL_REGEX);
		   if (!b) {
		    throw new UserException(
		      ResourceManager.getMessage(EMAIL_IS_NOT_VALID_EXCEPTION, null, NOT_FOUND, null));
		   }
		  } catch (Exception ex) {
		   throw ex;
		  }
	}
	
	
	public void validateName(String name) throws UserException {
		  try {
		   String NAME_REGEX = "^[a-zA-Z\\s]*$";
		   Boolean b = name.matches(NAME_REGEX);
			 if(name.length()>50 || name.length()<1)
			 {
				 throw new UserException(
					      ResourceManager.getMessage(NAME_NO_CHARACTER_EXCEPTION, null, NOT_FOUND, null));
			 }else
			 {
				 if (!b) {
					    throw new UserException(
					      ResourceManager.getMessage(NAME_IS_NOT_VALID_EXCEPTION, null, NOT_FOUND, null));
					   }
					   
			 }
		  } catch (Exception ex) {
		   throw ex;
		  }
	}
	
	
	
	
}