package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	//TODO:FIELDS OF RESPONSE SHOULD BE THE SAME SPELLING OF RESPONSE COMING MEAN:
	//TODO:LOGIN RESPONSE FROM SERVER
	//TODO:$response_data['error']=false;
	//TODO:$response_data['message'] = 'Login Successful';
	//TODO:$response_data['user']=$user;
	
	//THEN SHOULD NAME OF FIELDS OF LOGIN RESPONSE (error) not (err) for example and etc.
	private boolean error;
	private String message;
	private User user;
	
	public LoginResponse(boolean error, String message, User user) {
		this.error = error;
		this.message = message;
		this.user = user;
	}
	
	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
