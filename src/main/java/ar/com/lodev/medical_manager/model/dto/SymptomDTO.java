package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.Symptom;

public class SymptomDTO extends BaseEntityDTO{
	
	private String description;
	private String answer1;
	private String answer2;
	private int priority;
	
	public SymptomDTO(Symptom symptom){
		super(symptom);
		this.description = symptom.getDescription();
		this.answer1 = symptom.getAnswer1();
		this.answer2 = symptom.getAnswer2();
		this.priority = symptom.getPriority();
	}
	
	

	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	
	

}
