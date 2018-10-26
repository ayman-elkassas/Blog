package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved.SharedPrefManager;

public class HomeFragment extends Fragment {
	
	TextView email,name,school;
	
	public HomeFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		email=view.findViewById(R.id.textViewEmail);
		name=view.findViewById(R.id.textViewName);
		school=view.findViewById(R.id.textViewSchool);
		
		email.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
		name.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
		school.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());
		
	}
}
