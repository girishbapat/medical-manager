package ar.com.lodev.medical_manager.component.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.ImageEntityRepository;
import ar.com.lodev.medical_manager.component.service.ImageEntityService;
import ar.com.lodev.medical_manager.model.ImageEntity;
import ar.com.lodev.medical_manager.model.dto.ImageEntityDTO;

@Service
public class ImageEntityServiceImpl implements ImageEntityService{
	
	@Autowired
	private ImageEntityRepository imageEntityRepository;
	
	@Override
	@Transactional(readOnly=true)
	public ImageEntityDTO get(long imageId){
		ImageEntity image = imageEntityRepository.findOne(imageId);
		return new ImageEntityDTO(image, true);
	}

}
