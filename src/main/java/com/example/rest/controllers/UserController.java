package com.example.rest.controllers;

import static com.example.rest.constant.SpringBootConstant.NOT_FOUND;
import static com.example.rest.constant.SpringBootConstant.SIGN_UP_SUCCESS_MESSAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.exceptions.UserException;
import com.example.rest.models.UserModel;
import com.example.rest.services.ResponseModel;
import com.example.rest.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "User REST Service")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;

	@PostMapping("/signUp")
	@ApiOperation(value = "Save user details", response = UserModel.class, notes = "This API creates user's account. (SignUp)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Registered Successfully."),
			@ApiResponse(code = 204, message = "User is already exists or if emailId or password is blank."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	public ResponseEntity<Object> singUp(@RequestBody UserModel userModel) throws UserException {
		System.out.println("<------------inside SignUp Controller--------->");
		UserModel userModel2 = userService.signUpService(userModel);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(environment.getProperty(SIGN_UP_SUCCESS_MESSAGE, NOT_FOUND));
		responseModel.setObject(userModel2);
		return new ResponseEntity<Object>(responseModel, HttpStatus.OK);
	}
}
