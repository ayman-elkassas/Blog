package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses.RetrofitClient;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.LoginResponse;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
	
	private android.widget.EditText editTextEmail;
	private android.widget.EditText editTextPassword;
	private android.widget.LinearLayout linearLayout;
	private android.widget.TextView textViewRegister;
	private android.widget.Button buttonLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		this.buttonLogin = (Button) findViewById(R.id.buttonLogin);
		
		this.textViewRegister = (TextView) findViewById(R.id.textViewRegister);
		
		this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		this.editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		
		textViewRegister.setOnClickListener(this);
		buttonLogin.setOnClickListener(this);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(SharedPrefManager.getInstance(this).isLoggedIn())
		{
			Intent in =new Intent(LoginActivity.this,ProfileActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(in);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v==textViewRegister)
		{
			Intent in=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(in);
		}
		else if(v==buttonLogin)
		{
			userLogin();
		}
	}
	
	private void userLogin() {
		
		String email=editTextEmail.getText().toString().trim();
		String password=editTextPassword.getText().toString().trim();
		
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
		
		//TODO:PASSWORD VALIDATION
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
		
		Call<LoginResponse> call=RetrofitClient
				.getInstance()
				.getApi()
				.userlogin(email,password);
		
		call.enqueue(new Callback<LoginResponse>() {
			@Override
			public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
				
				LoginResponse loginResponse=response.body();

				if(!loginResponse.isError())
				{
					Toast.makeText(LoginActivity.this, ""+loginResponse.getMessage(),
							Toast.LENGTH_SHORT).show();
					
					User user=loginResponse.getUser();
					SharedPrefManager.getInstance(LoginActivity.this)
							.saveUser(user);
					
					Intent in =new Intent(LoginActivity.this,ProfileActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(in);
					
					
				}
				else
				{
					Toast.makeText(LoginActivity.this, ""+loginResponse.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Call<LoginResponse> call, Throwable t) {
				Toast.makeText(LoginActivity.this, ""+t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
