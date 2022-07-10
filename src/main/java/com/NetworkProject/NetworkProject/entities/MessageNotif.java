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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageNotif {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;


	private int receiverId;
	private String context;
	private String seen;

}
