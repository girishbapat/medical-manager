package ar.com.lodev.medical_manager.component.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.Configurations;

public interface ConfigurationsRepository extends CrudRepository<Configurations, Long> {

	List<Configurations> findAll();
	Configurations findByKeyCol(String keyCol);
}
