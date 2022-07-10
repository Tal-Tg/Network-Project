package com.NetworkProject.NetworkProject.requstEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestsIfFriends {

	private int userId;
	private int friendId;
	private String accepted;


}
