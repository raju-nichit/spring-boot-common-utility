package com.example.rest.services;

import static com.example.rest.constant.SpringBootConstant.EMAIL_LOGIN;
import static com.example.rest.constant.SpringBootConstant.EXCEPTION_SERVICE_USER;
import static com.example.rest.constant.SpringBootConstant.MESSAGE_USER_ALREADY_REGISTER_WITH_THIS_EMAIL;
import static com.example.rest.constant.SpringBootConstant.NOT_FOUND;
import static com.example.rest.constant.SpringBootConstant.SIGN_UP_SUCCESS_MESSAGE;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.rest.dao.UserDao;
import com.example.rest.dao.UserDaoImpl;
import com.example.rest.dto.UserDTO;
import com.example.rest.dto.UserSessionDTO;
import com.example.rest.exceptions.ServiceException;
import com.example.rest.exceptions.UserException;
import com.example.rest.models.UserModel;
import com.example.rest.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {

	private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageSource messageSource;

	@Override
	public ResponseModel signUpService(Locale locale, UserModel userModel) throws Exception {
		UserModel returnModel = null;
		ResponseModel responseModel = ResponseModel.getInstance();
		try {
			/**********
			 * code for loginType=email check user already registered
			 */
			UserDTO userDTO = userDAO.findUserByEmail(userModel.getEmail());
			if (userModel.getLoginType().equalsIgnoreCase(EMAIL_LOGIN)) {
				if (userDTO != null)
					throw new ServiceException(messageSource.getMessage(MESSAGE_USER_ALREADY_REGISTER_WITH_THIS_EMAIL,
							null, NOT_FOUND, locale));
			}

			// if user already singup with fb
			if (userDTO != null && (userModel.getFacebookId() != null || userModel.getInstagramId() != null)) {
				userDTO.setUserId(userDTO.getUserId());
				userDAO.upadteSocicalId(userDTO);
				returnModel = modelMapper.map(userDTO, UserModel.class);
				returnModel.setPassword(null);
				responseModel.setObject(returnModel);
				// TODO :If multiDevice device login then give proper response
				// TODO :check user previous session available or not
				responseModel.setMessage(messageSource.getMessage(MESSAGE_USER_ALREADY_REGISTER_WITH_THIS_EMAIL, null,
						NOT_FOUND, locale));
			} else {
				// save new user
				userDTO = modelMapper.map(userModel, UserDTO.class);
				userDTO.setCreateAt(new Timestamp(new Date().getTime()));
				userDTO.setAuthToken(TokenGenerator.generateToken(userModel.getEmail()));
				userDTO.setIsVerified(Boolean.FALSE);
				userDTO = userDAO.createUserRecord(userDTO);
				returnModel = modelMapper.map(userDTO, UserModel.class);
				returnModel.setPassword(null);
				responseModel.setObject(returnModel);
				responseModel.setMessage(messageSource.getMessage(SIGN_UP_SUCCESS_MESSAGE, null, NOT_FOUND, locale));
			}
			userModel.setUserId(returnModel.getUserId());
			saveUserSession(returnModel.getUserId(), userModel);
		} catch (ServiceException e) {
			LOGGER.error(e);
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(messageSource.getMessage(EXCEPTION_SERVICE_USER, null, NOT_FOUND, locale));
		}
		return responseModel;
	}

	private void saveUserSession(Integer userId, UserModel userModel) {
		UserSessionDTO userSessionDTO = new UserSessionDTO();
		userSessionDTO.setUser(modelMapper.map(userModel, UserDTO.class));
		userSessionDTO.setDeviceToken(userModel.getDeviceToken());
		userSessionDTO.setDeviceType(userModel.getDeviceType());
		userDAO.createUserSessionRecord(userSessionDTO);
	}

	@Override
	public UserModel signInService(UserModel userModel) throws UserException {
		try {

		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void signOutService(HttpServletRequest request) throws UserException {
		try {
			UserModel userModel = (UserModel) request.getAttribute("user");
			String deviceToken = request.getHeader("device_token");
			userDAO.logout(userModel.getUserId(), deviceToken);
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
