package ar.com.lodev.medical_manager.component.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import ar.com.lodev.medical_manager.component.service.NotificationService;
import ar.com.lodev.medical_manager.component.service.network.FCMClient;
import ar.com.lodev.medical_manager.component.service.network.FactoryClient;
import ar.com.lodev.medical_manager.component.service.network.model.FCMDoctorAllocateContent;
import ar.com.lodev.medical_manager.component.service.network.model.FCMDoctorSendMessageDataContent;
import ar.com.lodev.medical_manager.component.service.network.model.FCMPracticeSendMessageDataContent;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;

@Service
public class NotificationServiceImpl extends BaseService implements NotificationService{
	
//	private static final String NEW_MESSAGE = "New Message";
//	private static final String ALLOCATION = "Allocation"; 
	
	static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);
	
	@Value("${notification.fcm.server_key}")
	private String fcmServerKey;
	
	
	@Override
	@Async
	public void notifyNewMessage(Doctor doctor,String fcmId,String message,Date dateOfMessage){
		//{doctor id: long, doctor name: string, message: string, date: string}
		FCMClient fcmClient = FactoryClient.buildClient(FCMClient.class);
		
		//build content
//		FCMNotificationContent notificationContent = new FCMNotificationContent(doctor.getName(), NEW_MESSAGE);
		FCMDoctorSendMessageDataContent dataContent = new FCMDoctorSendMessageDataContent(doctor.getId(), 
				doctor.getFullName(), message, dateOfMessage.getTime());
		
		Map<String, Object> body = new HashMap<>();
//		body.put("notification", notificationContent);
		body.put("data", dataContent);
		body.put("to", fcmId);
		
		Call<Void> call = fcmClient.notifyNewMessage("key="+fcmServerKey, body);
		try {
			Response<Void> response = call.execute();
			if(response.code() != 200){
				logger.error(response.errorBody());
			}
		} catch (IOException e) {
			logger.error("ERROR", e);
		}
	}
	
	@Override
	@Async
	public void notifyNewMessage(PracticePlace practicePlace,String fcmId,String message,Date dateOfMessage){
		//{doctor id: long, doctor name: string, message: string, date: string}
		FCMClient fcmClient = FactoryClient.buildClient(FCMClient.class);
		
		//build content
//		FCMNotificationContent notificationContent = new FCMNotificationContent(practicePlace.getPracticeId(), NEW_MESSAGE);
		FCMPracticeSendMessageDataContent dataContent = 
				new FCMPracticeSendMessageDataContent(practicePlace.getId(), 
						practicePlace.getPracticeId(), message, dateOfMessage.getTime());
		
		Map<String, Object> body = new HashMap<>();
		//		body.put("notification", notificationContent);
		body.put("data", dataContent);
		body.put("to", fcmId);
		
		Call<Void> call = fcmClient.notifyNewMessage("key="+fcmServerKey, body);
		try {
			Response<Void> response = call.execute();
			if(response.code() != 200){
				logger.error(response.errorBody());
			}
		} catch (IOException e) {
			logger.error("ERROR", e);
		}
	}
	
	
	@Override
	@Async
	public void notifyDoctorAllocate(Doctor doctor,String fcmId){
		//{doctor id: long, doctor name: string, message: string, date: string}
		FCMClient fcmClient = FactoryClient.buildClient(FCMClient.class);
		
		//build content
//		FCMNotificationContent notificationContent = new FCMNotificationContent(doctor.getName(), ALLOCATION);
		FCMDoctorAllocateContent dataContent = new FCMDoctorAllocateContent(new DoctorDTO(doctor));
		
		Map<String, Object> body = new HashMap<>();
//		body.put("notification", notificationContent);
		body.put("data", dataContent);
		body.put("to", fcmId);
		
		Call<Void> call = fcmClient.notifyNewMessage("key="+fcmServerKey, body);
		try {
			Response<Void> response = call.execute();
			if(response.code() != 200){
				logger.error(response.errorBody());
			}
		} catch (IOException e) {
			logger.error("ERROR", e);
		}
	}
	
}
