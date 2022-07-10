package com.NetworkProject.NetworkProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {


	User findByUserNameAndPassword(String userName, String password);

	User findById(int id);

	User getById(int id);

}


