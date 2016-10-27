package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	
	Role findByName(String name);

}
