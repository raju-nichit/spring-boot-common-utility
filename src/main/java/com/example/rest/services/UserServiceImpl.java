package com.example.rest.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.rest.dao.UserDao;
import com.example.rest.dto.UserDTO;
import com.example.rest.exceptions.UserException;
import com.example.rest.models.UserModel;
import com.example.rest.utility.TokenGenerator;

import static com.example.rest.constant.SpringBootConstant.NOT_FOUND;
import static com.example.rest.constant.SpringBootConstant.EXCEPTION_SERVICE_USER;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserDao userDAO;

	@Autowired
	private Environment environment;

	@Override
	public UserModel signUpService(UserModel userModel) throws UserException {
		UserModel returnModel = null;
		try {
			System.out.println("<------------inside SignUp Controller--------->");
			UserDTO userDTO = modelMapper.map(userModel, UserDTO.class);
			userDTO.setAuthToken(TokenGenerator.generateToken(userModel.getEmail()));
			userDTO = userDAO.createUser(userDTO);
			returnModel = modelMapper.map(userDTO, UserModel.class);
			returnModel.setPassword(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(
					environment.getProperty(environment.getProperty(EXCEPTION_SERVICE_USER, NOT_FOUND)));
		}
		return returnModel;
	}

	@Override
	public UserModel signInService(UserModel userModel) throws UserException {
		try {

		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void signOutService(String authToken) throws UserException {
		try {

		} catch (Exception e) {
		}

	}

	@Override
	public void updateProfileService(String authToken) throws UserException {
		try {

		} catch (Exception e) {
		}

	}

}
