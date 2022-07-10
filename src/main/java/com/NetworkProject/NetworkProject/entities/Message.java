package com.NetworkProject.NetworkProject.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Message{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int senderId;
	private int receiverId;
	private String context;
	private LocalDateTime timeDate;
	private String senderFullName;
	private String receiverFullName;
	private String seen;
	private boolean ifPostExist;
	private String postContext;
	private String postValue;

}
