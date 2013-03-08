package com.readysteady.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cpn.apiomatic.annotation.Documentation;
import com.cpn.apiomatic.logging.Logged;
import com.readysteady.DefaultExceptionHandler;
import com.readysteady.exception.UnauthorizedActionException;
import com.readysteady.mail.Mailer;
import com.readysteady.user.session.Session;

@Documentation("UserController provide support for aunthenticate rac user, CRUD operation on user," + "assigning session for user, claim check for an user and invalidating the session.")
@Controller
@RequestMapping(value = "/user")
@Transactional
public class UserController extends DefaultExceptionHandler {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserDAO userDAO;

	@Autowired
	Mailer mailer;
	@Autowired
	String fromEmailAddress;

	@Autowired
	String publicAddress;

	@Autowired
	String publicPort;

	@Autowired
	SessionManager sessionManager;

	private RoleManager roleManager;

	@Logged
	@Documentation("Api persist the user by associating a certificate to the user.")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	User addUser(@RequestBody final User aUser) throws UserAlreadyExistsException {
		final User existingUser = userDAO.findByEmail(aUser.getEmail());
		if (existingUser != null) {
			throw new UserAlreadyExistsException("There's already a user with that email registered. Perhaps you meant to retrieve a lost password?");
		}
		userDAO.addUser(aUser);
		return aUser;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/session")
	public @ResponseBody
	Session authenticate(@RequestBody final User aCredential) throws NoSuchUserException {
		User user = userDAO.findByEmailAndPassword(aCredential.getEmail(), aCredential.getPassword());
		if (user == null) {
			throw new NoSuchUserException("No user with those credentials.");
		}
		return sessionManager.createSessionForUser(user);
	}

	@Logged
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody
	User updateUser(@PathVariable("id") String aUserId, @RequestBody User aUser, @RequestHeader("X-auth-sessionId") String aSessionId) throws Exception {
		Session session = sessionManager.sessionCheck(aSessionId);
		final User existingUser = userDAO.findById(aUserId);
		if (existingUser == null) {
			throw new NoSuchUserException("User doesn't exist:" + aUser.getId());
		}
		if (!existingUser.equals(session.getUser())) {
			throw new UnauthorizedActionException("You do not have the rights to modify that user.");
		}
		aUser.setId(existingUser.getId());
		aUser.setRole(existingUser.getRole());
		return entityManager.merge(aUser);
	}

	@Logged
	@RequestMapping(method = RequestMethod.GET, value = "/{anUserId}")
	@Transactional
	public @ResponseBody
	User getUser(@PathVariable final String aUserId, @RequestHeader("X-auth-sessionId") String aSessionId) throws Exception {
		Session session = sessionManager.sessionCheck(aSessionId);
		User existinguser = userDAO.findById(aUserId);
		if (existinguser == null) {
			throw new NoSuchUserException("User doesn't exist: " + aUserId);
		}
		if(!session.getUser().equals(existinguser) && (!session.getUser().getMatches().contains(aUserId))){
			throw new UnauthorizedActionException("You do not have permission to view that user.");
		}
		return existinguser;
	}

}
