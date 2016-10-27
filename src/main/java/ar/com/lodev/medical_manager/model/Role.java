package ar.com.lodev.medical_manager.model;

import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity{
	
	public static final String PRACTICE_ADMIN = "PRACTICE_ADMIN";
	public static final String DOCTOR = "DOCTOR";
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
