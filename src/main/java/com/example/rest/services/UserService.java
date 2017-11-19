package com.example.rest.services;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.example.rest.exceptions.UserException;
import com.example.rest.models.UserModel;

public interface UserService {
	public ResponseModel signUpService(Locale locale, UserModel userModel) throws UserException, Exception;

	public UserModel signInService(UserModel userModel) throws UserException;

	public void signOutService(HttpServletRequest request) throws UserException;

	public void updateProfileService(String authToken) throws UserException;

}
