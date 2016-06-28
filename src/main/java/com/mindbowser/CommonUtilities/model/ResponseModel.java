package com.mindbowser.CommonUtilities.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseModel {

	private ErrorModel error;
	private Object object;
	private String status;
	private String message;
	
	//private boolean joinedStatus;
	
	public ErrorModel getError() {
		return error;
	}

	public void setError(ErrorModel error) {
		this.error = error;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

		public static ResponseModel getInstance() {
		ResponseModel response=new ResponseModel();
		response.setStatus("SUCCESS");
		return response;
	}


}
