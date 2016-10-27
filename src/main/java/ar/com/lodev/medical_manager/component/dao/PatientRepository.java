package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.Patient;

public interface PatientRepository extends CrudRepository<Patient, Long>{

}
