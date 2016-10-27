package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;

public interface DoctorPracticePlaceRepository extends CrudRepository<DoctorPracticePlaceAssociation, Long>{
	
	DoctorPracticePlaceAssociation findByDoctorIdAndPracticePlaceId(long doctorId,long practicePlaceId);
	
	@Query("SELECT p FROM DoctorPracticePlaceAssociation p WHERE p.practicePlace.id = :practicePlaceId ORDER BY p.status DESC")
	Page<DoctorPracticePlaceAssociation> listByPracticePlace(@Param("practicePlaceId")long practicePlaceId,Pageable pageable);
	
	@Query("SELECT COUNT(p) FROM DoctorPracticePlaceAssociation p WHERE p.practicePlace.id = :practicePlaceId")
	long countByPracticePlace(@Param("practicePlaceId")long practicePlaceId);
	
	@Query("SELECT p FROM DoctorPracticePlaceAssociation p WHERE p.doctor.id = :doctorId AND p.status = :status")
	Page<DoctorPracticePlaceAssociation> listByDoctor(@Param("doctorId")long doctorId,@Param("status") PracticePlaceDoctorRequestStatus status,Pageable pageable);
	
	@Query("SELECT COUNT(p) FROM DoctorPracticePlaceAssociation p WHERE p.doctor.id = :doctorId AND p.status = :status")
	long countByByDoctor(@Param("doctorId")long doctorId,@Param("status") PracticePlaceDoctorRequestStatus status);
	
}
