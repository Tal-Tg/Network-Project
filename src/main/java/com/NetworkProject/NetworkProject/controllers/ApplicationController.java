package com.NetworkProject.NetworkProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NetworkProject.NetworkProject.services.ApplicationService;

@RestController
@RequestMapping("application")
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
public class ApplicationController {


	@Autowired
	private ApplicationService aplApplicationService;


	@GetMapping("users-get-search")
	public ResponseEntity<?> getUsersForSearch(@RequestHeader("authorization") String token) {
		System.out.println("getting all users search: " +aplApplicationService.getAllUserForSearch());
		return new ResponseEntity<>(aplApplicationService.getAllUserForSearch(),HttpStatus.OK);
	}

}
