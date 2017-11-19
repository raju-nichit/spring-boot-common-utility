package com.example.rest.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.models.UserModel;
import com.example.rest.services.ResponseModel;
import com.example.rest.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	private MessageSource messageSource;

	@PostMapping("/signUp")
	@ApiOperation(value = "Save user details", response = UserModel.class, notes = "This API creates user's account. (SignUp)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Registered Successfully."),
			@ApiResponse(code = 204, message = "User is already exists or if emailId or password is blank."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "Accept-Language", value = "Accept-Language", required = false, defaultValue = "US", dataType = "string", paramType = "header") })
	public ResponseEntity<Object> singUp(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
			@Valid @RequestBody UserModel userModel) throws Exception {
		System.out.println("<------------inside SignUp Controller--------->");
		return new ResponseEntity<Object>(userService.signUpService(locale, userModel), HttpStatus.OK);
	}

	@GetMapping("/signOut")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "authToken", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "deviceToken", value = "Device  token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Accept-Language", value = "Accept-Language", required = false, defaultValue = "US", dataType = "string", paramType = "header") })
	@ApiOperation(value = "User Logout", notes = "User Logout", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "error") })
	public ResponseEntity<Object> singOut(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
			HttpServletRequest request) throws Exception {
		System.out.println("<------------inside SignUp Controller--------->");
		userService.signOutService(request);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage("User successfully logout.");
		return new ResponseEntity<Object>(responseModel, HttpStatus.OK);
	}
}
