package com.tutorial.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tutorial.oauth.dao.OAuthDAOService;
import com.tutorial.oauth.model.CustomUser;
import com.tutorial.oauth.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	OAuthDAOService oAuthDAOService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = null;

		try {
			userEntity = oAuthDAOService.getUserDetails(username);

			if (userEntity != null && userEntity.getId() != null && !"".equalsIgnoreCase(userEntity.getId())) {
				CustomUser customUser = new CustomUser(userEntity);
				return customUser;
			} else {
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

	}

	public void create(UserEntity user) {
		jdbcTemplate.update("INSERT INTO  `user`" + "(NAME, EMAIL_ID, PASSWORD)" + "VALUES(?,?, ?);", user.getName(),
				user.getEmailId(), user.getPassword());
	}

	public boolean exist(String emailId) {
		int count = jdbcTemplate.queryForObject("SELECT count(1) FROM USER WHERE EMAIL_ID=?", new String[] { emailId },
				Integer.class);
		if (count > 0)
			return true;
		else
			return false;
	}

}
