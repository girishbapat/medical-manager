package ar.com.lodev.medical_manager.component.service.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMClient {
	
	@Headers({
		"Content-Type:application/json"
	})
	@POST("/fcm/send")
	Call<Void> notifyNewMessage(@Header("Authorization") String authKey,
			@Body Map<String, Object> content);
}
