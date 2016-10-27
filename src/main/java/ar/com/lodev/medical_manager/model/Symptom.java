package ar.com.lodev.medical_manager.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.com.lodev.medical_manager.model.dto.SymptomDTO;

@Entity
public class Symptom extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="session_id")
	private MedicalSession session;
	private String description;
	private String answer1;
	private String answer2;
	private int priority = 1;
	
	protected Symptom(){
		
	}
	
	public Symptom(MedicalSession session,String description,
			String answer1,String answer2,int priority){
		this.session = session;
		this.description = description;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.priority = priority;
	}
	
	public void update(SymptomDTO dto){
		this.description = dto.getDescription();
		this.answer1 = dto.getAnswer1();
		this.answer2 = dto.getAnswer2();
		this.priority = dto.getPriority();
	}
	
	
	
	public MedicalSession getSession() {
		return session;
	}

	public void setSession(MedicalSession session) {
		this.session = session;
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
