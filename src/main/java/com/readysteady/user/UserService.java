package com.readysteady.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readysteady.user.session.SessionDAO;

@Service
@Transactional
public class UserService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	SessionDAO sessionDAO;

	@Autowired
	UserDAO userDAO;

	private static final String masterUserEmail = "masteruser@intercloud.net";

	public void deleteUser(final User aUser) {
		sessionDAO.removeAllSessionsForUser(aUser);
		entityManager.remove(aUser);
	}

	public User findMasterUser() {
		return userDAO.findByEmail(UserService.masterUserEmail);
	}

	public User findOrCreate(final User aUser)  {
		final User u = entityManager.find(User.class, aUser.getId());
		if (u != null) {
			return u;
		}

		userDAO.addUser(aUser);
		return aUser;
	}

	public User merge(final User creator) {
		return entityManager.merge(creator);
	}



	public void setupMasterUser()  {
		final List<User> users = entityManager.createQuery("from User where email like ?", User.class).setParameter(1, UserService.masterUserEmail).getResultList();
		if (users.size() < 1) {
			final User user = new User();
			user.setEmail(UserService.masterUserEmail);
			user.setFirstName("Master");
			user.setLastName("User");

			userDAO.addUser(user);
		}
	}
}
