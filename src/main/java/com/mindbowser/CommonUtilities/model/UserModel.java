package com.mindbowser.CommonUtilities.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {

	private int id;
	private String name;
	private String emailId;
	private String password;
	private String confirmPassword;
	private String googlePlusId;
	private String facebookId;
	private String twitterId;
	private String deviceToken;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String gender;
	private String authToken ;
	private String oldPassword;
	private String newPassword;
	private String profilePic;

	
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonIgnore
	public String getGooglePlusId() {
		return googlePlusId;
	}
	@JsonProperty
	public void setGooglePlusId(String googlePlusId) {
		this.googlePlusId = googlePlusId;
	}
	@JsonIgnore
	public String getFacebookId() {
		return facebookId;
	}
	@JsonProperty
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	@JsonIgnore
	public String getTwitterId() {
		return twitterId;
	}
	@JsonProperty
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	@JsonIgnore
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	@JsonProperty
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	@JsonIgnore
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	@JsonProperty
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	@JsonIgnore
	public String getConfirmPassword() {
		return confirmPassword;
	}
	@JsonProperty
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@JsonIgnore
	public String getOldPassword() {
		return oldPassword;
	}
	@JsonProperty
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}
	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
