package com.NetworkProject.NetworkProject.requstEntities;

import com.NetworkProject.NetworkProject.entities.UserLike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsUnLikePosts {


	private int userId;
	private int postId;
	private int userLikeId;



}
