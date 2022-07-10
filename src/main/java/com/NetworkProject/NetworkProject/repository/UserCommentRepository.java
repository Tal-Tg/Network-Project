package com.NetworkProject.NetworkProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.UserComment;

public interface UserCommentRepository extends JpaRepository<UserComment, Integer> {

	UserComment findById(int id);

	
}
