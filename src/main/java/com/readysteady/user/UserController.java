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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cpn.apiomatic.annotation.Documentation;
import com.cpn.apiomatic.logging.Logged;
import com.readysteady.DefaultExceptionHandler;
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
	ClaimCheckDAO claimCheckDAO;

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

	@Logged
	@Documentation("Api persist the user by associating a certificate to the user.")
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/xml" })
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
		return sessionManager.createSessionForUser(authenticator.authenticate(aCredential));
	}

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@Logged
	public @ResponseBody
	User findByName(@PathVariable final String name) {
		return userDAO.findByName(name);
	}

	public void sendClaimCheck(final User aUser) {
		final ClaimCheck claimCheck = claimCheckDAO.makeClaimCheckForUser(aUser);
		final String body = "You (or someone with your email) signed up for our Private Cloud. Please click on the following link to activate your account http://" + publicAddress + (publicPort == "80" ? "" : ":" + publicPort) + "/Provision/claimCheck/" + claimCheck.getId();
		mailer.sendMail("tsavo@clearpathnet.com", aUser.getEmail(), "Welcome to ClearPath Networks Private Cloud!", body);
		// return claimCheckDAO.getClaimCheckForUser(aUser).getId();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/session/{sessionId}")
	public @ResponseBody
	void signOut(@PathVariable final String sessionId) {
		sessionManager.invalidateSession(sessionId);
	}

	@Logged
	@RequestMapping(method = RequestMethod.DELETE, value = "/{anEmail}")
	@Transactional
	public @ResponseBody
	void deleteUser(@PathVariable final String anEmail) throws Exception {
		if (StringUtils.isEmpty(anEmail)) {
			throw new InvalidUserArgumentsException("Invalid Argument:" + anEmail);
		}
		final User existingUser = userDAO.findByEmail(anEmail);
		if (existingUser == null) {
			throw new NoSuchUserException("User doesn't exist:" + anEmail);
		}
		userDAO.remove(existingUser);
	}

	@Logged
	@RequestMapping(value = { "", "/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody
	User updateUser(@RequestBody User aUser) throws Exception {
		final User existingUser = userDAO.findById(aUser.getId());
		if (existingUser == null) {
			throw new NoSuchUserException("User doesn't exist:" + aUser.getId());
		}
		return userDAO.mergeUser(aUser);
	}

	@Logged
	@RequestMapping(method = RequestMethod.GET)
	@Transactional
	public @ResponseBody
	List<User> getUsers() {
		return userDAO.getList();
	}

	@Logged
	@RequestMapping(method = RequestMethod.GET, value = "/{anEmail}")
	@Transactional
	public @ResponseBody
	User getUser(@PathVariable final String anEmail) throws Exception {
		if (StringUtils.isEmpty(anEmail)) {
			throw new InvalidUserArgumentsException("Invalid Arguments:" + anEmail);
		}
		User existinguser = userDAO.findByEmail(anEmail);
		if (existinguser == null) {
			throw new NoSuchUserException("User doesn't exist:" + anEmail);
		}
		return existinguser;
	}

}
