package ar.com.lodev.medical_manager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * It's the place where a {@link Patient} starts a {@link MedicalSession}
 * @author leandro
 *
 */
@Entity
public class PracticePlace extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User admin;
	@Column(unique=true)
	private String practiceId;
	private String description;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="image_profile_id")
	private ImageEntity imageProfile;
	@OneToMany(mappedBy="practicePlace")
	private List<DoctorPracticePlaceAssociation> doctorsAssociated;
	//this password is used for doctors to request an association to the practicePlace
	//on hold at the moment
	private String interactingPassword;
	
	protected PracticePlace(){
		
	}
	
	public PracticePlace(User admin,String practiceId,String description,String interactingPassword){
		this.practiceId = practiceId;
		this.description = description;
		this.interactingPassword = interactingPassword;
		this.admin = admin;
		this.imageProfile = new ImageEntity();
	}
	
	
	
	public List<DoctorPracticePlaceAssociation> getDoctorsAssociated() {
		return doctorsAssociated;
	}

	public void setDoctorsAssociated(
			List<DoctorPracticePlaceAssociation> doctorsAssociated) {
		this.doctorsAssociated = doctorsAssociated;
	}

	public ImageEntity getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(ImageEntity imageProfile) {
		this.imageProfile = imageProfile;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public String getPracticeId() {
		return practiceId;
	}
	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInteractingPassword() {
		return interactingPassword;
	}
	public void setInteractingPassword(String interactingPassword) {
		this.interactingPassword = interactingPassword;
	}
	
	
	
}
