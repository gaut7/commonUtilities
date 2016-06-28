package com.mindbowser.CommonUtilities.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindbowser.CommonUtilities.model.ErrorModel;
import com.mindbowser.CommonUtilities.model.ResponseModel;
import com.mindbowser.CommonUtilities.model.UserModel;
import com.mindbowser.CommonUtilities.service.UserService;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.AUTHTOKEN;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.AUTHTOKEN_KEYWORD;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.FAIL;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.FORGOT_PASSWORD;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.INVALID_USER_TOKEN;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.LOGIN_URL;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.SIGN_UP_USER_URL;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.USER;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.SWAGGER_DOC;




@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TokenAuthorizationInterceptor extends
		AbstractPhaseInterceptor<Message> {
	private Logger logger = Logger
			.getLogger(TokenAuthorizationInterceptor.class);
	@Autowired
	private UserService userService;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public TokenAuthorizationInterceptor() {
		super(Phase.PRE_INVOKE); // Put this interceptor in this phase
	}

	public void handleMessage(Message message) throws RuntimeException {
		HttpServletRequest httpRequest = (HttpServletRequest) message
				.get(AbstractHTTPDestination.HTTP_REQUEST);

		String pathInfo = httpRequest.getPathInfo();
		
		logger.info("PATH httpRequest" + pathInfo);
		MDC.put("ipaddress", httpRequest.getRemoteAddr());
		
		if(!(pathInfo.contains(SIGN_UP_USER_URL)) && !(pathInfo.contains(LOGIN_URL)) && !(pathInfo.contains(FORGOT_PASSWORD)) && !(pathInfo.contains(SWAGGER_DOC)))
		{
			System.out.println("******************** \n"+pathInfo);
			// get the authToken value from header
			String authToken = httpRequest.getHeader(AUTHTOKEN);
			logger.info(AUTHTOKEN_KEYWORD+ authToken);
			
			System.out.println("**********************"+authToken);
			
			UserModel user=null; 
			//try{
				user = userService.getUserByAuthToken(authToken);
			//}
//			catch(Exception ex)
//			{
//				logger.error(ex.getStackTrace(),ex);
//			}
			if (user == null) {
				String errorMessage=INVALID_USER_TOKEN;						
				 logger.info(AUTHTOKEN_KEYWORD+ errorMessage);				
				ResponseModel responseModel = ResponseModel.getInstance();
				ErrorModel error = new ErrorModel(errorMessage);
				responseModel.setError(error);
				responseModel.setStatus(FAIL);
				String errorResponse = null;
				ObjectMapper mapper = new ObjectMapper();
				try {
					errorResponse = mapper.writeValueAsString(responseModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				HttpServletResponse response = (HttpServletResponse) message
						.get(AbstractHTTPDestination.HTTP_RESPONSE);
				response.setStatus(500);
				try {
					response.getWriter().write(errorResponse);
				} catch (IOException e) {
					e.printStackTrace();
				}
				throw new org.apache.cxf.interceptor.security.AccessDeniedException(
					"INVALID_USER_TOKEN");

			}
			
			httpRequest.setAttribute(USER, user);
			httpRequest.setAttribute("authToken", authToken);
			
		}
	}

	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
