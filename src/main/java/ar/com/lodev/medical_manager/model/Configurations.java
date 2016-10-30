package ar.com.lodev.medical_manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Configurations extends BaseEntity {
	@Column(unique=true)
	private String key;
	private String value;

	protected Configurations(){
		
	}
	

	public Configurations(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
