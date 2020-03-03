package com.tutorial.oauth.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
	private String id;
	private String name;
	private String emailId;
	private String password;

	public UserEntity(String name, String emailId, String password) {
		this.name = name;
		this.emailId = emailId;
		this.password = password;
	}

	public UserEntity() {

	}

	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

}
