package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.adapters.UsersAdapter;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses.RetrofitClient;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns.GetAllUsers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
	
	private RecyclerView recyclerView;
	private List<User> userList;
	
	private UsersAdapter usersAdapter;
	
	public UsersFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_users, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		recyclerView=view.findViewById(R.id.recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		Call<GetAllUsers> call= RetrofitClient
				.getInstance()
				.getApi()
				.allusers();
		
		call.enqueue(new Callback<GetAllUsers>() {
			@Override
			public void onResponse(Call<GetAllUsers> call, Response<GetAllUsers> response) {
				userList=response.body().getUsers();
				usersAdapter=new UsersAdapter(getActivity(),userList);
				recyclerView.setAdapter(usersAdapter);
			}
			
			@Override
			public void onFailure(Call<GetAllUsers> call, Throwable t) {
				Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
