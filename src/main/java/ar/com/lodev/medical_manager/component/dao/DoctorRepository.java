package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Long>{
	
	Doctor findByUserId(long userId);

}
