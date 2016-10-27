package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.BaseEntity;

public class BaseEntityDTO {
	
	private Long id;

	public BaseEntityDTO(){
		
	}
	
	public BaseEntityDTO(BaseEntity baseEntity){
		this.id = baseEntity.getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
