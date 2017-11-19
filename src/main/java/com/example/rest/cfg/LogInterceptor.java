package com.example.rest.cfg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.rest.constant.SpringBootConstant;
import com.example.rest.dao.UserDao;
import com.example.rest.exceptions.InterceptorException;

@Component

public class LogInterceptor implements HandlerInterceptor {

	@Autowired
	private UserDao userDAO;
	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		log.info("Request Completed!");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		log.info("Method executed");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
			throws InterceptorException {
		log.info("Before process request");

		log.info("Request URI:\t" + request.getRequestURI());
		String path = request.getRequestURI();
		if (!path.contains(SpringBootConstant.SWAGGER_URL) && !path.contains(SpringBootConstant.LOGIN_URL)
				&& !path.contains(SpringBootConstant.FORGOT_URL) && !path.contains(SpringBootConstant.RECOVER_PASS_URL)
				&& !path.contains(SpringBootConstant.SIGN_UP_URL) && !path.contains(SpringBootConstant.CONFIG_UI)
				&& !path.contains("swagger-resources")) {
			log.info("Not need execute interceptor");
			throw new InterceptorException("Invalid authToken");

		}

		return true;
	}

}