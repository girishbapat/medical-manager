package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.ChatMedicalSessionMessage;

public interface ChatMedicalSessionMessageRepository extends CrudRepository<ChatMedicalSessionMessage, Long>{

}
