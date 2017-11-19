package com.example.rest.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.constant.SpringBootConstant;
import com.example.rest.dto.UserDTO;
import com.example.rest.dto.UserSessionDTO;
import com.example.rest.exceptions.UserException;

@Repository
public class UserDaoImpl implements UserDao {
	private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
	@Autowired
	SessionFactory sf;

	@Override
	public UserDTO findUserByAuthToken(String authToken) throws UserException {
		try {
			return (UserDTO) sf.getCurrentSession().createCriteria(UserDTO.class)
					.add(Restrictions.eq("auth_token", authToken)).uniqueResult();
		} catch(Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDTO createUserRecord(UserDTO userDTO) throws UserException {
		try {
			LOGGER.info("<--------INSIDE createUdserRecordDAO--------->");
			sf.getCurrentSession().save(userDTO);
		} catch (UserException e) {
			LOGGER.error(e);
			throw e;
		}
		return userDTO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createUserSessionRecord(UserSessionDTO userSessionDTO) throws UserException {
		try {
			sf.getCurrentSession().save(userSessionDTO);
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	@Override
	@Transactional
	public UserDTO findUserByEmail(String email) throws UserException {
		try {
			LOGGER.info("<--------INSIDE createUdserRecordDAO--------->");
			return (UserDTO) sf.getCurrentSession().createCriteria(UserDTO.class).add(Restrictions.eq("email", email))
					.setMaxResults(SpringBootConstant.ONE).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	@Override
	@Transactional
	public void upadteSocicalId(UserDTO userDTO) throws UserException {
		StringBuilder updateString = new StringBuilder();
		updateString.append("update user set ");
		if (userDTO.getFacebookId() != null)
			updateString.append("facebook_id=:facebook_id");
		else
			updateString.append("instagram_id=:instagram_id");

		updateString.append(" where user_id=:user_id");
		try {
			Query query = sf.getCurrentSession().createSQLQuery(updateString.toString());
			if (userDTO.getFacebookId() != null)
				query.setParameter("facebook_id", userDTO.getFacebookId());
			else
				query.setParameter("instagram_id", userDTO.getInstagramId());
			query.setParameter("user_id", userDTO.getUserId());
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}
	}

	@Override
	@Transactional
	public void logout(Integer userId, String deviceToken) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" delete from user_session where user_id= :user_id and ");
		queryString.append(" device_token= :device_token ");
		try {
			Query query = sf.getCurrentSession().createSQLQuery(queryString.toString());
			query.setParameter("use_id", userId);
			query.setParameter("device_token", deviceToken);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		}

	}

}
