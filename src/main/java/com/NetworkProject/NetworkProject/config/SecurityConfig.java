package com.NetworkProject.NetworkProject.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.NetworkProject.NetworkProject.security.Information;


@Configuration
public class SecurityConfig {

	@Bean
	public Map<String, Information> map(){
		return new HashMap<>();
	}
}
