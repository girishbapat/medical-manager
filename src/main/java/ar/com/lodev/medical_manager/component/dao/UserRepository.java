package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.lodev.medical_manager.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
		
	User findByEmail(@Param("email")String email);
}
