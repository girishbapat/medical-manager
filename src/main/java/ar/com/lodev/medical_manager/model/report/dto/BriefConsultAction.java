package ar.com.lodev.medical_manager.model.report.dto;

public class BriefConsultAction {
	
	private String action;
	private String additionalComment;
	private boolean discussed = false;
	
	public BriefConsultAction(String action,String additionalComment){
		this.action = action;
		this.additionalComment = additionalComment;
	}
	
	

	public boolean isDiscussed() {
		return discussed;
	}



	public void setDiscussed(boolean discussed) {
		this.discussed = discussed;
	}



	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAdditionalComment() {
		return additionalComment;
	}

	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}
	
	
	
}
