package com.NetworkProject.NetworkProject.requstEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestsCommentOnPost {


	private int userId;
	private int postId;
	private String context;

}
