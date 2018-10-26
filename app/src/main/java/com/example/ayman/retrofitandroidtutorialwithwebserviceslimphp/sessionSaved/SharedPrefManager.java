package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;

public class SharedPrefManager {
	
	private static final String SHARED_PREF_NAME = "my_shared_preff";
	
	private static SharedPrefManager mInstance;
	private Context mCtx;
	
	private SharedPrefManager(Context mCtx) {
		this.mCtx = mCtx;
	}
	
	//TODO:Singleton class
	public static synchronized SharedPrefManager getInstance(Context mCtx) {
		if (mInstance == null) {
			mInstance = new SharedPrefManager(mCtx);
		}
		return mInstance;
	}
	
	public void saveUser(User user) {
		
		SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.putInt("id", user.getId());
		editor.putString("ic_email", user.getEmail());
		editor.putString("name", user.getName());
		editor.putString("school", user.getSchool());
		
		editor.apply();
		
	}
	
	public boolean isLoggedIn() {
		SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		//TODO:CHECK IF ANY ARGU CHANGE ABOUT DEFAULT VALUE TAKEN for ex : id
		return sharedPreferences.getInt("id", -1) != -1;
	}
	
	public User getUser() {
		SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		return new User(
				sharedPreferences.getInt("id",-1),
				sharedPreferences.getString("ic_email", null),
				sharedPreferences.getString("name", null),
				sharedPreferences.getString("school", null)
		);
	}
	
	public void clearSession() {
		//TODO:WHEN SIGN OUT
		SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.apply();
	}
	
}
