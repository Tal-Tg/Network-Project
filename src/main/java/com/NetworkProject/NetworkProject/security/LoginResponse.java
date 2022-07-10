package com.NetworkProject.NetworkProject.security;

import com.NetworkProject.NetworkProject.enums.ClientType;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data

@NoArgsConstructor
public class LoginResponse {

	private Integer id;
	private String firstName;
	private String lastName;
	private String userName;
	private String token;
	private ClientType type;


	public LoginResponse(Integer id, String firstName, String lastName, String userName, String token,
			ClientType type) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.token = token;
		this.type = type;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "LoginResponse [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", token=" + token + ", type=" + type + "]";
	}








}
