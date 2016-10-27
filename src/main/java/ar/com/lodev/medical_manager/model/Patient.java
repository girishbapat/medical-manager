package ar.com.lodev.medical_manager.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Patient extends BaseEntity{
	
	private String name;
	private String lastname;
	private Date dateOfBirth;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	private String gcmId;
	private String email;
	
	protected Patient(){
		
	}
	
	public Patient(String name,String lastname,Date dateOfBirth,String gcmId,String email){
		this.name = name;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.gcmId = gcmId;
		this.email = email;
	}
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName(){
		return this.name + " " + this.lastname;
	}
	
	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
