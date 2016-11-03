package ar.com.lodev.medical_manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.com.lodev.medical_manager.model.dto.ConfigurationsDTO;

@Entity
public class Configurations extends BaseEntity {
	private String keyCol;
	private String valueCol;

	protected Configurations(){
		
	}
	
	
	public Configurations(ConfigurationsDTO configuration){
		super();
		this.setId(configuration.getId());
		this.keyCol=configuration.getKey();
		this.valueCol=configuration.getValue();
	}


	public Configurations(String keyCol, String valueCol) {
		super();
		this.keyCol = keyCol;
		this.valueCol = valueCol;
	}

	@Column(unique=true,nullable = false)
	public String getKeyCol() {
		return keyCol;
	}

	public void setKeyCol(String keyCol) {
		this.keyCol = keyCol;
	}
	@Column( nullable = true)
	public String getValueCol() {
		return valueCol;
	}

	public void setValueCol(String valueCol) {
		this.valueCol = valueCol;
	}
	
}
