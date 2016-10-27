package ar.com.lodev.medical_manager.component.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.util.DateUtil;

@Component
public class ScheduledTasks {
	
	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	
    @Scheduled(fixedDelay = 30 * 60 * 1000)//milisenconds
    @Transactional
    public void reportCurrentTime() {
    	List<MedicalSession> sessions = medicalSessionRepository.findOpeningSessions();
    	for(MedicalSession session : sessions){
    		long diffMinutes = DateUtil.getDifferenceInMinutes(session.getStartDate(), new Date());
    		if(diffMinutes > 15){
    			session.setFinishDate(new Date());
    			session.setOpen(false);
    		}
    	}
    }
	
}
