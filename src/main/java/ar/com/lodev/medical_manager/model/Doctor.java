package ar.com.lodev.medical_manager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Doctor extends BaseEntity{
	
	private String name;
	private String lastname;
	private String description;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="image_profile_id")
	private ImageEntity imageProfile;
	@OneToMany(mappedBy="doctor")
	private List<DoctorPracticePlaceAssociation> practicePlaceAssociations;
	@ManyToOne
	@JoinColumn(name="practice_association_logged_id")
	private DoctorPracticePlaceAssociation practiceLogged;
	private String welcomeMessage;
	
	protected Doctor() {
		
	}
	
	public Doctor(String name,String lastname,User user){
		this.name = name;
		this.lastname = lastname;
		this.user = user;
		this.imageProfile = new ImageEntity();
	}
	
	public String getFullName(){
		return name +", "+lastname;
	}
	

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public DoctorPracticePlaceAssociation getPracticeLogged() {
		return practiceLogged;
	}

	public void setPracticeLogged(DoctorPracticePlaceAssociation practiceLogged) {
		this.practiceLogged = practiceLogged;
	}

	public List<DoctorPracticePlaceAssociation> getPracticePlaceAssociations() {
		return practicePlaceAssociations;
	}

	public void setPracticePlaceAssociations(
			List<DoctorPracticePlaceAssociation> practicePlaceAssociations) {
		this.practicePlaceAssociations = practicePlaceAssociations;
	}

	public ImageEntity getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(ImageEntity imageProfile) {
		this.imageProfile = imageProfile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	
}
