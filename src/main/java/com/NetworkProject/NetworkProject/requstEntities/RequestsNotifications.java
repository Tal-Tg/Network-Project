package com.NetworkProject.NetworkProject.requstEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsNotifications {

	private int userId;
	private int otherUserId;
	private String firstName;
	private String lastName;
	private String userName;


}