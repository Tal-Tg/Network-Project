package com.NetworkProject.NetworkProject.security;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NetworkProject.NetworkProject.enums.ClientType;


@Service
public class TokenManager {

	@Autowired
	private Map<String,Information> tokens;


	public String createToken(ClientType type,int clientId ) {
		String token = UUID.randomUUID().toString();

		Information information = new Information(type,clientId,LocalDateTime.now());
		tokens.put(token,information);
		return token;
	}

	public boolean isTokenExistAndUser(String token) {
		try {
			return tokens.get(token).getType().equals(ClientType.USER);
		} catch (Exception e) {
			throw new SecurityException("Un Authorizedd");
		}
	}

	public void removeToken(String token) {
		try {
			Information information = tokens.get(token);
			tokens.remove(token, information);
		} catch (Exception e) {
			throw new SecurityException("Un Authorizedd");
		}
	}


//	@Scheduled(fixedRate = 1_000*60)
//	public void deleteExpired() {
//		tokens.values().removeIf((i) -> i.getTime().isAfter(LocalDateTime.now().plusMinutes(30)));
//	}

}
