package com.readysteady.user;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClaimCheckDAO {

	@PersistenceContext
	EntityManager entityManager;

	public void claim(final String claimCheckId) throws AlreadyClaimedException, NoSuchClaimCheckException {
		ClaimCheck claimCheck;
		try {
			claimCheck = entityManager.find(ClaimCheck.class, claimCheckId);
		} catch (final Exception e) {
			throw new NoSuchClaimCheckException();
		}
		if (claimCheck == null) {
			throw new NoSuchClaimCheckException();
		}
		if (claimCheck.isClaimed()) {
			throw new AlreadyClaimedException();
		}
		claimCheck.setClaimed(true);
		entityManager.merge(claimCheck);
	}

	public String createForUser(final User aUser) {
		final ClaimCheck claimCheck = new ClaimCheck(aUser);
		entityManager.persist(claimCheck);
		return claimCheck.getId();
	}

	@Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
	public void expireOldClaimChecks() {
		final Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		entityManager.createQuery("delete from ClaimCheck where createdOn < ?").setParameter(1, calendar.getTime()).executeUpdate();
	}

	public ClaimCheck getClaimCheckForUser(final User aUser) {
		return entityManager.createQuery("from ClaimCheck where user = :user", ClaimCheck.class).setParameter("user", aUser).getSingleResult();
	}

	public ClaimCheck makeClaimCheckForUser(final User aUser) {
		final ClaimCheck claimCheck = new ClaimCheck(aUser);
		entityManager.persist(claimCheck);
		return claimCheck;
	}

}
