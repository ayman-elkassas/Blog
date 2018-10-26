package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.DefaultResponse;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.GetAllUsers;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Apis {

	//TODO:SHOULD FIELD THE SAME SPELLING OF SERVER REST API
	//TODO:@FormUrlEncoded WITH ANY HTTP METHOD EXPECT (GET,DELETE) only...
	
	@FormUrlEncoded
	@POST("createuser")
	Call<DefaultResponse> createuser(
			@Field("email") String email,
			@Field("password") String password,
			@Field("name") String name,
			@Field("school") String school
	);
	
	@FormUrlEncoded
	@POST("userlogin")
	Call<LoginResponse> userlogin(
			@Field("email") String email,
			@Field("password") String password
	);
	
	@GET("allusers")
	Call<GetAllUsers> allusers();
	
	//TODO:ANY PARAMETERS IN PATH ITSELF PASSING UNDER ANNOTATION OF @PATH
	//TODO:AND FIELDS ARE FIELDS IN REQUEST BODY PARAMETERS
	@FormUrlEncoded
	@PUT("updateuser/{id}")
	Call<LoginResponse> updateuser(
			@Path("id") int id,
			@Field("email") String email,
			@Field("name") String name,
			@Field("school") String school
	);
	
	@FormUrlEncoded
	@PUT("updatepassword")
	Call<LoginResponse> updatepassword(
			@Field("currentpassword") String currentpassword,
			@Field("newpassword") String newpassword,
			@Field("email") String email
	);
	
	@DELETE("deleteuser/{id}")
	Call<DefaultResponse> deleteuser(
			@Path("id") int id
	);
	
	

}
