package com.NetworkProject.NetworkProject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findByReceiverIdAndSenderId(int receiverId, int senderId);

	List<Message> findByReceiverId(int receiverId);
	
}
