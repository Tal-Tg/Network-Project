package com.NetworkProject.NetworkProject.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.Post;
import com.NetworkProject.NetworkProject.entities.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {


	List<UserNotification> findByUserId(int userId);

	UserNotification findById(int id);
	
	Map<Integer, UserNotification> getById(int id);
	

	List<UserNotification> getByFriendId(int friendId);
	
	default Map<Integer , UserNotification> getByFriendIdMap(int friendId){
		return getByFriendId(friendId).stream().collect(Collectors.toMap(UserNotification::getId, u -> u));
	}

}
