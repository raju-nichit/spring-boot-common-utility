package com.example.rest.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.dto.UserDTO;
import com.example.rest.exceptions.UserException;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sf;

	@Override
	@Transactional
	public UserDTO createUser(UserDTO userDTO) throws UserException {
		try {
			sf.openSession().save(userDTO);
		} catch (UserException e) {
			e.printStackTrace();
			throw e;
		}
		return userDTO;
	}

}
