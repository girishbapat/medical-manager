package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.ImageEntity;

public class ImageEntityDTO extends BaseEntityDTO{
	
	private byte[] photo;
	private String extension;
	private boolean hasPhoto;
	
	public ImageEntityDTO(ImageEntity imageEntity,boolean loadImage){
		super(imageEntity);
		if(loadImage){
			photo = imageEntity.getPhoto();
		}
		this.extension = imageEntity.getExtension();
		this.hasPhoto = imageEntity.isHasPhoto();
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
		this.photo = photo;
	}
	
	

}
