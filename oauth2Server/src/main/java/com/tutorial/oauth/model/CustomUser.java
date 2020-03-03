package com.tutorial.oauth.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;

	public CustomUser(UserEntity userEntity) {
		super(userEntity.getEmailId(), userEntity.getPassword(), userEntity.getGrantedAuthorities());
		this.id = userEntity.getId();
		this.name = userEntity.getName();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

}
