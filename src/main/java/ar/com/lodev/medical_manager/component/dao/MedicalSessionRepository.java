package ar.com.lodev.medical_manager.component.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.lodev.medical_manager.model.MedicalSession;

public interface MedicalSessionRepository extends CrudRepository<MedicalSession, Long>{
	
	@Query("SELECT m FROM MedicalSession m WHERE m.practicePlace.id = :practiceId AND (m.patient.name LIKE %:name% OR m.patient.lastname LIKE %:name% OR m.patient.name || ' ' || m.patient.lastname LIKE %:name%)")
	Page<MedicalSession> listSessionsFromPracticePlace(@Param("practiceId")long practiceId,
			@Param("name")String namePatient,Pageable pageable);
	
	@Query("SELECT COUNT(m) FROM MedicalSession m WHERE m.practicePlace.id = :practiceId AND (m.patient.name LIKE %:name% OR m.patient.lastname LIKE %:name% OR m.patient.name || ' ' || m.patient.lastname LIKE %:name%)")
	long countSessionsFromPracticePlace(@Param("practiceId")long practiceId,@Param("name")String namePatient);
	
	@Query("SELECT m FROM MedicalSession m WHERE m.open = 1")
	List<MedicalSession> findOpeningSessions();
	
	@Query("SELECT m FROM MedicalSession m WHERE m.doctor.id=:doctorId and m.practicePlace.id=:practicePlaceId")
	List<MedicalSession> listDoctorForPracticePlaceFromMedicalSessions(@Param("doctorId")long doctorId, @Param("practicePlaceId")long practicePlaceId);
}
