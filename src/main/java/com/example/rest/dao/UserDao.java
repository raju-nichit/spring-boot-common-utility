package com.example.rest.dao;

import com.example.rest.dto.UserDTO;
import com.example.rest.dto.UserSessionDTO;
import com.example.rest.exceptions.UserException;

public interface UserDao {

	public UserDTO findUserByAuthToken(String authToken) throws UserException;

	public UserDTO createUserRecord(UserDTO userDTO) throws UserException;

	public void createUserSessionRecord(UserSessionDTO userSessionDTO) throws UserException;

	public UserDTO findUserByEmail(String emailId) throws UserException;

	public void upadteSocicalId(UserDTO userModel) throws UserException;

	public void logout(Integer userId, String deviceToken);

}
