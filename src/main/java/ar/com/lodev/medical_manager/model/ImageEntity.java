package ar.com.lodev.medical_manager.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class ImageEntity extends BaseEntity{
	
	private String extension;
	@Lob
	private byte[] photo;
	private boolean hasPhoto = true;
	
	protected ImageEntity(){
		this.hasPhoto = false;
	}
	
	public ImageEntity(byte[] photo,String extension){
		this.photo = photo;
		this.extension = extension;
	}
	
	
	
	public boolean isHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		if(photo!=null){
			this.hasPhoto = true;
		}
		this.photo = photo;
	}
	
	
	
}
