package ar.com.lodev.medical_manager.model.report.dto;

public class BriefConsultIssue {
	
	private String issueBox;
	private String additionalComment;
	private boolean discussed = false;
	
	public BriefConsultIssue(String issueBox,String additionalComment){
		this.issueBox = issueBox;
		this.additionalComment = additionalComment;
	}
	
	
	
	public boolean isDiscussed() {
		return discussed;
	}



	public void setDiscussed(boolean discussed) {
		this.discussed = discussed;
	}



	public String getIssueBox() {
		return issueBox;
	}

	public void setIssueBox(String issueBox) {
		this.issueBox = issueBox;
	}

	public String getAdditionalComment() {
		return additionalComment;
	}

	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}
	
	

}
