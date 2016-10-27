package ar.com.lodev.medical_manager.model.report.dto;

import java.util.ArrayList;
import java.util.List;

public class BriefConsultReport {
	
	private String dateSession;
	private String patientName;
	private List<BriefConsultIssue> issues = new ArrayList<BriefConsultIssue>();
	private List<BriefConsultAction> actions = new ArrayList<BriefConsultAction>();
	private String otherComment;
	
	public BriefConsultReport(){
		
	}
	
	public void setDummyData(){
		this.dateSession = "12/12/2018 12:10";
		this.patientName = "Pepe";
		BriefConsultIssue issue1 = new BriefConsultIssue("Description dummy lalal lalal lalal lalallalal lalal lalal", "Comment dummy dummy dummy dummy dummy dummy dummy dummy");
		BriefConsultIssue issue2 = new BriefConsultIssue("Description dummy", "Comment dummy");
		BriefConsultIssue issue3 = new BriefConsultIssue("Description dummy", "Comment dummy");
		this.issues.add(issue1);
		this.issues.add(issue2);
		this.issues.add(issue3);
		
		BriefConsultAction action1 = new BriefConsultAction("Description dummy", "Comment dummy");
		BriefConsultAction action2 = new BriefConsultAction("Description dummy", "Comment dummy");
		BriefConsultAction action3 = new BriefConsultAction("Description dummy", "Comment dummy");
		BriefConsultAction action4 = new BriefConsultAction("Description dummy", "Comment dummy");
		BriefConsultAction action5 = new BriefConsultAction("Description dummy", "Comment dummy");
		this.actions.add(action1);
		this.actions.add(action2);
		this.actions.add(action3);
		this.actions.add(action4);
		this.actions.add(action5);
		
		this.otherComment = "Other comment dummy";
	}

	public String getDateSession() {
		return dateSession;
	}

	public void setDateSession(String dateSession) {
		this.dateSession = dateSession;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public List<BriefConsultIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<BriefConsultIssue> issues) {
		this.issues = issues;
	}

	public List<BriefConsultAction> getActions() {
		return actions;
	}

	public void setActions(List<BriefConsultAction> actions) {
		this.actions = actions;
	}

	public String getOtherComment() {
		return otherComment;
	}

	public void setOtherComment(String otherComment) {
		this.otherComment = otherComment;
	}
	
	

}
