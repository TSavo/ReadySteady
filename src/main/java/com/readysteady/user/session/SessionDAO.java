package com.readysteady.user.session;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpn.apiomatic.logging.Logged;
import com.cpn.apiomatic.rest.AbstractDAO;
import com.readysteady.user.User;

@Service
@Transactional
public class SessionDAO extends AbstractDAO<String, Session> {

	@Logged
	public Session addDataToSession(final Session session, final String aKey, final String aValue) {
		for (final SessionData data : session.getSessionData()) {
			if (aKey.equals(data.getKey())) {
				data.setValue(aValue);
				entityManager.merge(data);
				return entityManager.find(Session.class, session.getId());
			}
		}
		final SessionData data = new SessionData(aKey, aValue);
		data.setSession(session);
		entityManager.persist(data);
		session.getSessionData().add(data);
		return entityManager.merge(session);
	}

	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void clearExpiredSessions() {
		entityManager.createQuery("delete from Session where expiresAt < current_date()").executeUpdate();
	}

	
	@Logged
	public Session createSessionForUser(final User aUser, final Date anExpiresAt) {
		// removeAllSessionsForUser(aUser);
		final Session session = new Session(aUser, anExpiresAt);
		entityManager.persist(session);
		return session;
	}

	@Logged
	public Session getSessionForUser(final User aUser) {
		return entityManager.createQuery("from Session where user = ? and expiresAt > current_date()", Session.class).setParameter(1, aUser).getSingleResult();
	}

	@Logged
	public void removeAllSessionsForUser(final User aUser) {
		final List<Session> expiredSessions = entityManager.createQuery("from Session where user = ?", Session.class).setParameter(1, aUser).getResultList();
		for (final Session s : expiredSessions) {
			entityManager.remove(s);
		}
	}
}
