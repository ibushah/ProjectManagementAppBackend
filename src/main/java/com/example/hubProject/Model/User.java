package com.example.hubProject.Model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;

    @Column
    private Boolean active;
    
    @Column
    private String userType;

	public User() {
	}

	public User(String email, String name, String password, Boolean active, String userType) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.active = active;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean isActive) {
		this.active = isActive;
	}


	
    
}
