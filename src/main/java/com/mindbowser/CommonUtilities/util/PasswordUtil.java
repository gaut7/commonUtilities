package com.mindbowser.CommonUtilities.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil implements PasswordEncoder {
	
	  private static final String BCRYP_TYPE = "$";
	  private static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder();


	@Override
	public String encode(CharSequence rawPassword) {
		 return BCRYPT.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
	
		Boolean result = null;
		if (encodedPassword.startsWith(BCRYP_TYPE)) {
		    result =  BCRYPT.matches(rawPassword, encodedPassword);   
		    }
		return result;
	
	}
}
