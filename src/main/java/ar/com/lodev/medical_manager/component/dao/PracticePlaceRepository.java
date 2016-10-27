package ar.com.lodev.medical_manager.component.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.lodev.medical_manager.model.PracticePlace;

public interface PracticePlaceRepository extends CrudRepository<PracticePlace, Long>{

	List<PracticePlace> findByPracticeIdContaining(String practiceId);
	
	PracticePlace findByPracticeId(String practiceId);
	
	PracticePlace findByAdminId(long adminId);
	
	@Query("SELECT p FROM PracticePlace p WHERE p.practiceId LIKE %:practiceId%")
	Page<PracticePlace> search(@Param("practiceId")String practiceId,Pageable pageable);
	
	@Query("SELECT COUNT(p) FROM PracticePlace p WHERE p.practiceId LIKE %:practiceId%")
	long countSearch(@Param("practiceId")String practiceId);
}
