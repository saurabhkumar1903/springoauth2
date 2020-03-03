package com.tutorial.oauth.dao;

import com.tutorial.oauth.model.UserEntity;

public interface OAuthDAOService {

	public UserEntity getUserDetails(String emailId);

}
