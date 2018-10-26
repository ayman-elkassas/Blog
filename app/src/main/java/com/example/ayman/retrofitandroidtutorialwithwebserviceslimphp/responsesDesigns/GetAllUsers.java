package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;

import java.util.List;

public class GetAllUsers {
	
	private boolean error;
	private List<User> users;
	
	public GetAllUsers(boolean error, List<User> users) {
		this.error = error;
		this.users = users;
	}
	
	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
