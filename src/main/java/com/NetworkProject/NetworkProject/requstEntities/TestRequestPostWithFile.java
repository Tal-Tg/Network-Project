package com.NetworkProject.NetworkProject.requstEntities;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRequestPostWithFile {

	private String context;
	private int userId;
	private String firstName;
	private String lastName;
//	private File file;
	private String[] files;






	

}
