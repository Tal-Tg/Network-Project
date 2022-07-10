package com.NetworkProject.NetworkProject.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.Post;
import com.NetworkProject.NetworkProject.entities.UserVsPosts;


public interface PostRepository extends JpaRepository<Post, Integer>{


	Post findById(int id);

	List<Post> findByUserId(int userId);
	
	@Override
	List<Post> findAll();

	Post getByUserId(int userId);

}
