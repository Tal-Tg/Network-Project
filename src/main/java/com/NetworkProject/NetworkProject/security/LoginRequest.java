package com.NetworkProject.NetworkProject.security;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data

@NoArgsConstructor
public class LoginRequest {


	private String userName;
	private String password;
//	private ClientType type;





	public LoginRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
//		this.type = type;
	}
	public String getUserName() {
		return userName;
	}
	public void setEmail(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public ClientType getType() {
//		return type;
//	}
//	public void setType(ClientType type) {
//		this.type = type;
//	}


}
