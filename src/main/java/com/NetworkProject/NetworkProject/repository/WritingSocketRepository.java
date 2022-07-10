package com.NetworkProject.NetworkProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.WritingSocket;

public interface WritingSocketRepository extends JpaRepository<WritingSocket, Integer> {


	List<WritingSocket> findByReceiverId(int receiverId);

	List<WritingSocket> findBySenderId(int senderId);


}
