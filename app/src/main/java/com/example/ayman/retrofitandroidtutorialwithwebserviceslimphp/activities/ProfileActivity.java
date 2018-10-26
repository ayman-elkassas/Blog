package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments.HomeFragment;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments.SettingsFragment;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.fragments.UsersFragment;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.sessionSaved.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity
		implements BottomNavigationView.OnNavigationItemSelectedListener{
	
	private android.widget.TextView username;
	private android.support.design.widget.BottomNavigationView bottomnav;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		this.bottomnav = (BottomNavigationView) findViewById(R.id.bottom_nav);
		
		bottomnav.setOnNavigationItemSelectedListener(this);
		
		displayFragment(new HomeFragment());
		
//		User user=SharedPrefManager.getInstance(this).getUser();
	}
	
	private void displayFragment(Fragment fragment)
	{
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content,fragment)
				.commit();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!SharedPrefManager.getInstance(this).isLoggedIn())
		{
			Intent in =new Intent(this,MainActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(in);
		}
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		
		Fragment fragment=null;
		
		switch (item.getItemId())
		{
			case R.id.home:
				fragment=new HomeFragment();
				break;
			case R.id.users:
				fragment=new UsersFragment();
				break;
			case R.id.setting:
				fragment=new SettingsFragment();
				break;
		}
		
		if(fragment!=null)
		{
			displayFragment(fragment);
		}
		
		return false;
	}
}
