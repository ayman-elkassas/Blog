package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.R;
import com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
	
	private Context context;
	private List<User> userList;
	
	public UsersAdapter(Context context, List<User> userList) {
		this.context = context;
		this.userList = userList;
	}
	
	@NonNull
	@Override
	public UsersAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		
		View view= LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
		return new UsersViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull UsersAdapter.UsersViewHolder holder, int position) {
		User user=userList.get(position);
		holder.name.setText(user.getName());
		holder.email.setText(user.getEmail());
		holder.school.setText(user.getSchool());
		
	}
	
	@Override
	public int getItemCount() {
		return userList.size();
	}
	
	class UsersViewHolder extends RecyclerView.ViewHolder
	{
		TextView name,email,school;
		
		public UsersViewHolder(@NonNull View itemView) {
			super(itemView);
			
			name=itemView.findViewById(R.id.name);
			email=itemView.findViewById(R.id.email);
			school=itemView.findViewById(R.id.school);
			
		}
	}
}
