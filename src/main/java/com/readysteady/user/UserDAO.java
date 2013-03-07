package com.readysteady.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpn.apiomatic.rest.AbstractDAO;

@Service
@Transactional
public class UserDAO extends AbstractDAO<String, User> {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public void addUser(final User aUser) {
		entityManager.persist(aUser);
	}

	public User findByUsernameAndPassword(final String aUserName, final String aPassword) {
		try {
			return entityManager
					.createQuery("from User where username like :username and password like :password", User.class)
					.setParameter("username", aUserName).setParameter("password", aPassword).getSingleResult();
		} catch (Exception e) {
			logger.trace(e.getMessage(), e);
			return null;
		}

	}

	public User findByEmail(final String anEmail) {
		try {
			return entityManager.createQuery("from User where email like :email", User.class)
					.setParameter("email", anEmail).getResultList().get(0);
		} catch (final Exception e) {
			UserDAO.logger.trace(e.getMessage(), e);
			return null;
		}
	}

	public User findByName(final String name) {
		try {
			return entityManager.createQuery("from User where username like ?", User.class).setParameter(1, name)
					.getSingleResult();
		} catch (final Exception e) {
			UserDAO.logger.trace(e.getMessage(), e);
			return null;
		}
	}
	


	public User findByUsername(final String name) {
		try {
			return entityManager.createQuery("from User where username = ?", User.class).setParameter(1, name)
					.getSingleResult();
		} catch (final Exception e) {
			UserDAO.logger.trace(e.getMessage(), e);
			return null;
		}
	}

	public User findByOpenId(final String id) {
		return entityManager.createQuery("from User where openId like :openId", User.class).setParameter("openId", id)
				.getSingleResult();
	}

	public User mergeUser(final User existingUser) {
		return entityManager.merge(existingUser);
	}

	@Override
	public void remove(final User existingUser) {
		entityManager.remove(existingUser);
	}

	@Override
	public User findById(final String id) {
		try {
			return entityManager.find(User.class, id);
		} catch (final Exception e) {
			UserDAO.logger.trace(e.getMessage(), e);
			return null;
		}
	}

	public List<User> getList() {
		return entityManager.createQuery("from User", User.class).getResultList();
	}

	


}
