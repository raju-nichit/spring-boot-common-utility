package com.example.rest.services;

import com.example.rest.exceptions.UserException;
import com.example.rest.models.UserModel;

public interface UserService {
	public UserModel signUpService(UserModel userModel) throws UserException;

	public UserModel signInService(UserModel userModel) throws UserException;

	public void signOutService(String authToken) throws UserException;

	public void updateProfileService(String authToken) throws UserException;

}
