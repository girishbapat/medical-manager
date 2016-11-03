package ar.com.lodev.medical_manager.component.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.lodev.medical_manager.component.dao.ConfigurationsRepository;
import ar.com.lodev.medical_manager.component.service.ConfigurationsService;
import ar.com.lodev.medical_manager.model.Configurations;
import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;

@Service
public class ConfigurationsServiceImpl extends BaseService implements ConfigurationsService {

	@Autowired
	private ConfigurationsRepository configurationRepository;

	@Override
	public ConfigurationsDTO findByKey(String keyCol) {
		Configurations foundConfigurations = configurationRepository.findByKeyCol(StringUtils.trim(keyCol));
		ConfigurationsDTO configDTO=null;
		if(foundConfigurations!=null){
		 configDTO=new ConfigurationsDTO(foundConfigurations );
		}
		return configDTO;
	}

	@Override
	public List<ConfigurationsDTO> findAll() {
		 Iterable<Configurations> findAll = configurationRepository.findAll();
		 List<ConfigurationsDTO> configDtoList=new ArrayList<ConfigurationsDTO>();
		 for (Configurations configuration : findAll) {
			 ConfigurationsDTO configDTO=new ConfigurationsDTO(configuration);
				configDtoList.add(configDTO);
		}
		 return configDtoList;
	}

	@Override
	public void update(List<Configurations> configurations) {
		configurationRepository.save(configurations);
	}

}
