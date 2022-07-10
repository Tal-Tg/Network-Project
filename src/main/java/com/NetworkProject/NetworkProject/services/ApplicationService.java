package com.NetworkProject.NetworkProject.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NetworkProject.NetworkProject.entities.User;
import com.NetworkProject.NetworkProject.repository.UserRepository;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private UserRepository userRepository;



	public List<User> getAllUserForSearch(){
		List<User> usersForSearch = userRepository.findAll();
		return usersForSearch;
	}
}
