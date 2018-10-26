package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses.RetrofitClient;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.DefaultResponse;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	private android.widget.EditText editTextEmail;
	private android.widget.EditText editTextPassword;
	private android.widget.EditText editTextName;
	private android.widget.EditText editTextSchool;
	private android.widget.LinearLayout linearLayout;
	private android.widget.TextView textViewLogin;
	private android.widget.Button buttonSignUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
		this.textViewLogin = (TextView) findViewById(R.id.textViewLogin);
		this.linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		this.editTextSchool = (EditText) findViewById(R.id.editTextSchool);
		this.editTextName = (EditText) findViewById(R.id.editTextName);
		this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		this.editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		
		textViewLogin.setOnClickListener(this);
		buttonSignUp.setOnClickListener(this);
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(SharedPrefManager.getInstance(this).isLoggedIn())
		{
			Intent in =new Intent(this,ProfileActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(in);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v==buttonSignUp)
		{
			usersignup();
		}
		else if(v==textViewLogin)
		{
			Intent in =new Intent(MainActivity.this,LoginActivity.class);
			startActivity(in);
		}
	}
	
	private void usersignup()
	{
		String email=editTextEmail.getText().toString().trim();
		String password=editTextPassword.getText().toString().trim();
		String name=editTextName.getText().toString().trim();
		String school=editTextSchool.getText().toString().trim();
		
		//TODO:EMAIL VALIDATION
		if(email.isEmpty())
		{
			editTextEmail.setError("Email is required");
			editTextEmail.requestFocus();
			return;
		}
		
		if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
		{
			editTextEmail.setError("Enter a valid ic_email");
			editTextEmail.requestFocus();
			return;
		}
		
		if(password.isEmpty())
		{
			editTextPassword.setError("Password is required");
			editTextPassword.requestFocus();
			return;
		}
		
		if(password.length()<6)
		{
			editTextPassword.setError("Password should at least 6 character long");
			editTextPassword.requestFocus();
			return;
		}
		
		if(name.isEmpty())
		{
			editTextName.setError("Name is required");
			editTextEmail.requestFocus();
			return;
		}
		
		if(school.isEmpty())
		{
			editTextSchool.setError("School is required");
			editTextSchool.requestFocus();
			return;
		}
		
		Call<DefaultResponse> call=RetrofitClient
				.getInstance().getApi()
				.createuser(email,password,name,school);
		
		call.enqueue(new Callback<DefaultResponse>() {
			@Override
			public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
				if (response.code() == 201) {

					DefaultResponse defaultResponse = response.body();
					Toast.makeText(MainActivity.this, defaultResponse.getMsg(),
							Toast.LENGTH_LONG).show();

				} else if (response.code() == 422) {
					DefaultResponse defaultResponse = response.body();
					Toast.makeText(MainActivity.this, defaultResponse.getMsg(),
							Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Call<DefaultResponse> call, Throwable t) {
				Toast.makeText(MainActivity.this, ""+t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		});
//
		//TODO:OLD WAY WITH NO DESIGN RESPONSE
//		call.enqueue(new Callback<ResponseBody>() {
//			@Override
//			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//				String responseBody=null;
//
//				try {
//					if(response.code()==201)
//					{
//						responseBody=response.body().string();
//					}
//					else
//					{
//						responseBody=response.errorBody().string();
//					}
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//				if(responseBody!=null)
//				{
//					try {
//						JSONObject jsonObject=new JSONObject(responseBody);
//						Toast.makeText(MainActivity.this,
//								""+jsonObject.getString("message"),
//								Toast.LENGTH_LONG).show();
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//			@Override
//			public void onFailure(Call<ResponseBody> call, Throwable t) {
//				Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();
//			}
//		});
		
	}
}
