package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.activities.LoginActivity;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.activities.MainActivity;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses.RetrofitClient;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.DefaultResponse;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.LoginResponse;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
	
	private EditText editTextEmail, editTextName, editTextSchool;
	private EditText editTextCurrentPassword, editTextNewPassword;
	
	public SettingsFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		editTextEmail = view.findViewById(R.id.editTextEmail);
		editTextName = view.findViewById(R.id.editTextName);
		editTextSchool = view.findViewById(R.id.editTextSchool);
		editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
		editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
		
		view.findViewById(R.id.buttonSave).setOnClickListener(this);
		view.findViewById(R.id.buttonChangePassword).setOnClickListener(this);
		view.findViewById(R.id.buttonLogout).setOnClickListener(this);
		view.findViewById(R.id.buttonDelete).setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.buttonSave:
				updateProfile();
				break;
			case R.id.buttonChangePassword:
				updatePassword();
				break;
			case R.id.buttonLogout:
				logout();
				break;
			case R.id.buttonDelete:
				deleteUser();
				break;
		
		}
	}
	
	private void deleteUser() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Are you sure?");
		builder.setMessage("This action is irreversible...");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Call<DefaultResponse> call= RetrofitClient
						.getInstance()
						.getApi()
						.deleteuser(SharedPrefManager.getInstance(getActivity())
								.getUser().getId());
				
				call.enqueue(new Callback<DefaultResponse>() {
					@Override
					public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
						
						if(!response.body().isErr())
						{
							SharedPrefManager.getInstance(getActivity()).clearSession();
							Intent intent = new Intent(getActivity(), MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							startActivity(intent);
						}
						
						Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(Call<DefaultResponse> call, Throwable t) {
						Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
			}
		});
		
		AlertDialog ad = builder.create();
		ad.show();
		
		
		
	}
	
	private void logout() {
		
		SharedPrefManager.getInstance(getActivity()).clearSession();
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		
	}
	private void updateProfile() {
		
		String email=editTextEmail.getText().toString().trim();
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
		
		Call<LoginResponse> call= RetrofitClient
				.getInstance().getApi()
				.updateuser(
						SharedPrefManager.getInstance(getActivity()).getUser().getId(),
						email,
						name,
						school
				);
		
		call.enqueue(new Callback<LoginResponse>() {
			@Override
			public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
				Toast.makeText(getContext(), ""+response.body().getMessage()
						, Toast.LENGTH_LONG).show();
				if(!response.body().isError())
				{
					SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
				}
			}
			
			@Override
			public void onFailure(Call<LoginResponse> call, Throwable t) {
				Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	
	}
	private void updatePassword() {
		String currentpassword = editTextCurrentPassword.getText().toString().trim();
		String newpassword = editTextNewPassword.getText().toString().trim();
		
		if (currentpassword.isEmpty()) {
			editTextCurrentPassword.setError("Password required");
			editTextCurrentPassword.requestFocus();
			return;
		}
		
		if (newpassword.isEmpty()) {
			editTextNewPassword.setError("Enter new password");
			editTextNewPassword.requestFocus();
			return;
		}
		
		User user = SharedPrefManager.getInstance(getActivity()).getUser();
		Call<LoginResponse> call=RetrofitClient
				.getInstance().getApi()
				.updatepassword(currentpassword,newpassword,user.getEmail());
		
		call.enqueue(new Callback<LoginResponse>() {
			@Override
			public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
				Toast.makeText(getActivity(), ""+response.body().getMessage(),
						Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(Call<LoginResponse> call, Throwable t) {
				Toast.makeText(getActivity(), ""+t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
