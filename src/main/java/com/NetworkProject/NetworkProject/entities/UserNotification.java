package com.NetworkProject.NetworkProject.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserNotification {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	private int userId;
	private String accepted;
	private int friendId;
	private String firstName;
	private String lastName;
	private String userName;
	private String ifSeen;
	private String contextNotif;

}
