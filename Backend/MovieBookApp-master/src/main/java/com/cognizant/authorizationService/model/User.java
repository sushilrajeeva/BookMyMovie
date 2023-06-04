package com.cognizant.authorizationService.model;

import javax.persistence.Entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;





@Entity
@Table(name="Users")
public class User {
	@Id
	@Column(unique=true)
	private String userName;
	
	@NotBlank(message="This field is mandatory")
	private String userFirstName;
	
	@NotBlank(message="This field is mandatory")
	private String userLastName;
	
	@NotBlank
	@Column(unique=true)
	@Email(message="Please Enter valid Email Address")
	private String email;
	
	@NotBlank
    private String userPassword;
	
	@NotBlank
	private String userConfirmedPassword;
	
	//@Size(max=10)
	private String contactNumber;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="USER_ROLE",
	joinColumns= {@JoinColumn(name="USER_ID")},
	inverseJoinColumns= {
			@JoinColumn(name="ROLE_ID")
	}
	)
	private Set<Role> role;

	public User(String userName, String userFirstName, String userLastName, String email, String userPassword,
			String userConfirmedPassword, String contactNumber, Set<Role> role) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.email = email;
		this.userPassword = userPassword;
		this.userConfirmedPassword = userConfirmedPassword;
		this.contactNumber = contactNumber;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserConfirmedPassword() {
		return userConfirmedPassword;
	}

	public void setUserConfirmedPassword(String userConfirmedPassword) {
		this.userConfirmedPassword = userConfirmedPassword;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}

