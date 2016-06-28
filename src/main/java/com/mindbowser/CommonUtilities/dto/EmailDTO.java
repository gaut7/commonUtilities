package com.mindbowser.CommonUtilities.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable {

	private static final long serialVersionUID = -7645517832019406737L;
	private String to;
	private String from;
	private String subject;
	private String body;
	private String password;
	private String username;
	
	// For forgot password
	private String emailAsUserName ;
	
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmailAsUserName() {
		return emailAsUserName;
	}
	public void setEmailAsUserName(String emailAsUserName) {
		this.emailAsUserName = emailAsUserName;
	}
	
	
}
