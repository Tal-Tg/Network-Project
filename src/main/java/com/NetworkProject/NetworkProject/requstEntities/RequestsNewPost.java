package com.NetworkProject.NetworkProject.requstEntities;

import java.util.Arrays;

import com.NetworkProject.NetworkProject.enums.SharePostWithCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestsNewPost {

	private String context;
	private int userId;
	private String firstName;
	private String lastName;
	private String[] files;
	private SharePostWithCondition sharePostCondition;


}
