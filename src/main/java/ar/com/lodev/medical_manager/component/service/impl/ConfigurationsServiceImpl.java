package ar.com.lodev.medical_manager.component.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.lodev.medical_manager.component.dao.ConfigurationsRepository;
import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.model.Configurations;
@Service
public class ConfigurationsServiceImpl extends BaseService implements ConfigurationsService {

	@Autowired
	private ConfigurationsRepository configurationRepository;
	
	@Override
	public Configurations findByKey(String key) {
		return configurationRepository.findByKey(key);
	}

	@Override
	public List<Configurations> findAll() {
		// TODO Auto-generated method stub
		return configurationRepository.findAll();
	}

}
