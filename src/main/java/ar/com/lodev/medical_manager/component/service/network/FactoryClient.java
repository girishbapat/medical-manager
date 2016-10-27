package ar.com.lodev.medical_manager.component.service.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FactoryClient {
	
	public static <T> T buildClient(Class<T> clazz){
		
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
		
		Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl(getBaseUrl(clazz))
			    .client(client)
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();

			T service = retrofit.create(clazz);
			
			return service;
	}
	
	private static <T> String getBaseUrl(Class<T> clazz){
		String baseUrl = null;
		if(clazz.isAssignableFrom(FCMClient.class)){
			baseUrl = "https://fcm.googleapis.com";
		}
		
		return baseUrl;
	}


}
