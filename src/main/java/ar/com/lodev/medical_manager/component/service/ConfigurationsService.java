package ar.com.lodev.medical_manager.component.service;

import ar.com.lodev.medical_manager.model.Configurations;

public interface ConfigurationsService {
	
	Configurations findByKey(String key);
	Iterable<Configurations>findAll();

}
