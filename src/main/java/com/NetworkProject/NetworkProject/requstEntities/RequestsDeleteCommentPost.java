package com.NetworkProject.NetworkProject.requstEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestsDeleteCommentPost {


	private int postId;
	private int commentId;
	private int userId;

}
