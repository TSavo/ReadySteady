package com.readysteady.user;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpn.apiomatic.logging.Logged;
import com.readysteady.user.session.Session;
import com.readysteady.user.session.SessionDAO;

@Service
public class SessionManager {

	@Autowired
	SessionDAO sessionDAO;

	public Session createSessionForUser(User aUser) {
		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, 2);
		return sessionDAO.createSessionForUser(aUser, cal.getTime());
	}
	@Logged
	public Session getSessionById(final String aSessionId){
		return sessionDAO.findById(aSessionId);
	}
	public void invalidateUserSession(User aUser) {
		sessionDAO.removeAllSessionsForUser(aUser);
	}

	public void invalidateSession(Session aSession){
		sessionDAO.remove(aSession);
	}
	public void invalidateSession(String sessionId) {
		invalidateSession(sessionDAO.find(sessionId));
	}
	public Session sessionCheck(String aSessionId) throws InvalidSessionException {
		Session session = getSessionById(aSessionId);
		if(session == null){
			throw new InvalidSessionException("Invalid session.");
		}
		return session;
	}
}
