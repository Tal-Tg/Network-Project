package com.NetworkProject.NetworkProject.requstEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsSharePost {

	private int postId;
	private int senderId;
	private int receiverId;


}
