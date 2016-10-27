package ar.com.lodev.medical_manager;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import ar.com.lodev.medical_manager.component.dao.DoctorRepository;
import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.dao.PracticePlaceRepository;
import ar.com.lodev.medical_manager.component.dao.UserRepository;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.User;

/**
 * intercept the subscription in websockets to authorize it.
 * @author PC
 *
 */
@Component(value="channelInterceptor")
public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter{
	
	static final Logger logger = Logger.getLogger(TopicSubscriptionInterceptor.class);
	public static final String PRACTICE_PLACE_SESSIONS = "/topic/practice-place/sessions/";
	public static final String MEDICAL_SESSIONS = "/topic/medicalSession/";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PracticePlaceRepository practicePlaceRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
	    StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(message);
	    if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) && headerAccessor.getUser() !=null ) {
	        Principal userPrincipal = headerAccessor.getUser();
	        if(!validateSubscription(userPrincipal, headerAccessor.getDestination()))
	        {
	            throw new SecurityException("No permission for this topic");
	        }
	    }
	    return message;
	}

	private boolean validateSubscription(Principal principal, String topicDestination)
	{
	    logger.debug("Validate subscription for {} to topic {}, User: "+principal.getName()+" ,topic: "+topicDestination);
	    User user = userRepository.findByUsername(principal.getName());
	    if(user == null){
	    	return false;
	    }
	    String[] pathValues = topicDestination.split("/");
    	if(topicDestination.contains(PRACTICE_PLACE_SESSIONS)){
	    	return validateSubscriptionForPracticePlace(pathValues, user);
	    }else if(topicDestination.contains(MEDICAL_SESSIONS)){
	    	return validateSubscriptionForMedicalSession(pathValues, user);
	    }
	   
	    return false;
	}
	
	private boolean validateSubscriptionForMedicalSession(String[] pathValues,User user){
		long medicalSessionId = Long.parseLong(pathValues[3]);
		MedicalSession medicalSession = medicalSessionRepository.findOne(medicalSessionId);
		
		PracticePlace practicePlace = getPracticeFromUser(user);
    	if(practicePlace != null){
    		//validate if the user is admin of the practice place
    		return medicalSession.getPracticePlace().equals(practicePlace);
    	}else{
    		// find if the user is doctor
    		Doctor doctor = getDoctorFromUser(user);
    		if(doctor != null){
    			return medicalSession.getPracticePlace().equals(doctor.getPracticeLogged().getPracticePlace());
    		}
    	}
    	return false;
	}
	
	private boolean validateSubscriptionForPracticePlace(String[] pathValues,User user){
		long practiceId = Long.parseLong(pathValues[4]);
    	//validate if the user is admin of the practice place
    	PracticePlace practicePlace = getPracticeFromUser(user);
    	if(practicePlace != null){
    		return practicePlace.getId().longValue() == practiceId;
    	}else{
    		// find if the user is doctor
    		Doctor doctor = getDoctorFromUser(user);
    		if(doctor != null){
    			return doctor.getPracticeLogged().getPracticePlace()
    					.getId().longValue() == practiceId;
    		}
    	}
    	return false;
	}
	
	private Doctor getDoctorFromUser(User user){
		return doctorRepository.findByUserId(user.getId());
	}
	
	private PracticePlace getPracticeFromUser(User user){
		return practicePlaceRepository.findByAdminId(user.getId());
	}
}
