package com.NetworkProject.NetworkProject.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.UserVsPosts;

public interface UserVsPostsRepository extends JpaRepository<UserVsPosts, Integer> {


	List<UserVsPosts> findByUserId(int userId);

	default Map<Integer, UserVsPosts> findByUserIdMap(int userId){
		return findByUserId(userId).stream().collect(Collectors.toMap(UserVsPosts::getId, u -> u));
	};
	
	
	UserVsPosts findByUserIdAndPostId(int userId, int postId);

}
