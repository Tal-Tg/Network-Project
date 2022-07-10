package com.NetworkProject.NetworkProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.UserFriends;

public interface UserFriendRepository extends JpaRepository<UserFriends, Integer> {

	UserFriends findByUserIdAndFriendId(int userId, int friednId);

	void deleteByUserId(int userId);

	List<UserFriends> findByUserId(int userId);


}
