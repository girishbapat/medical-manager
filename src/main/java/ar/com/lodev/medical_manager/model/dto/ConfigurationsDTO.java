package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.Configurations;

public class ConfigurationsDTO extends BaseEntityDTO {
private String key;
private String value;
private String practiceId;
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
public ConfigurationsDTO(String key, String value) {
	super();
	this.key = key;
	this.value = value;
}
public ConfigurationsDTO() {
	super();
}

public ConfigurationsDTO(Configurations configuration){
	super();
	this.setId(configuration.getId());
	this.key=configuration.getKeyCol();
	this.value=configuration.getValueCol();
	this.practiceId=configuration.getPracticeId();
}
public String getPracticeId() {
	return practiceId;
}
public void setPracticeId(String practiceId) {
	this.practiceId = practiceId;
}

}
