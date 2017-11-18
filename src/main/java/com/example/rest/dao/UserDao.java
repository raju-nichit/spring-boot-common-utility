package com.example.rest.dao;

import com.example.rest.dto.UserDTO;
import com.example.rest.exceptions.UserException;

public interface UserDao {
	public UserDTO createUser(UserDTO userDTO) throws UserException;

}
