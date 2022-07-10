package com.NetworkProject.NetworkProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NetworkProject.NetworkProject.entities.MessageNotif;

public interface MessageNotifRepository extends JpaRepository<MessageNotif, Integer >{


	List<MessageNotif> findByReceiverId(int recevierId);

}
