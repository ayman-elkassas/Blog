package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.apisClasses;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO:RETROFIT CLIENT CLASS IS SAVED template
public class RetrofitClient {
	
	//TODO:FOR AUTHENTICATED WITH APIs
	private static final String AUTH = "Basic " + Base64.encodeToString(("ayman:123456").getBytes(), Base64.NO_WRAP);
	private static final String BASE_URL="https://myappslimretrofit.000webhostapp.com/AndroidApps/myapp/public/";
	
	private static RetrofitClient mInstance;
	private Retrofit retrofit;
	
	public RetrofitClient() {
		//TODO:PREPARE okHttpClient object Authenticated with apis
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(
						new Interceptor() {
							@Override
							public Response intercept(Chain chain) throws IOException {
								Request original = chain.request();
								
								Request.Builder requestBuilder = original.newBuilder()
										.addHeader("Authorization", AUTH)
										.method(original.method(), original.body());
								
								Request request = requestBuilder.build();
								return chain.proceed(request);
							}
						}
				).build();
		
		retrofit=new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(okHttpClient)
				.build();
	}
	
	//TODO:SINGLETON
	//TODO:FIRST CALL getInstance() then return RetrofitClient object
	//TODO:then call getApi to get object from Apis
	public static synchronized RetrofitClient getInstance() {
		if (mInstance == null) {
			mInstance = new RetrofitClient();
		}
		return mInstance;
	}
	
	public Apis getApi() {
		return retrofit.create(Apis.class);
	}
}
