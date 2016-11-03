package ar.com.lodev.medical_manager.component.service;

import java.util.List;

import ar.com.lodev.medical_manager.model.Configurations;
import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;

public interface ConfigurationsService {

	void update(List<Configurations> configurations);

	ConfigurationsDTO findByKey(String key);

	List<ConfigurationsDTO> findAll();

}
