package com.NetworkProject.NetworkProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.UserLike;

public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {

	UserLike findByUserId(int userId);

	
	List<UserLike> deleteById(int postId);
}
