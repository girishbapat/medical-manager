package ar.com.lodev.medical_manager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User extends BaseEntity{
	
	@Column(unique=true)
	private String username;
	private String password;
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
	private List<Role> roles;
	private boolean activationState = true;
	private String email;
	
	protected User(){
		
	}
	
	public User(String username,String password,String email,List<Role> roles){
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.email = email;
	}
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivationState() {
		return activationState;
	}

	public void setActivationState(boolean activationState) {
		this.activationState = activationState;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
