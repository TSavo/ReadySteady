package com.readysteady.user;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMaintaince {

	@PersistenceContext
	EntityManager entityManager;

	@Scheduled(fixedDelay = 300000)
	@Transactional
	public void updateSubscriptions() {
		List<User> users = entityManager.createQuery("from User where Status = :status", User.class).setParameter("status", User.Status.active).getResultList();
		for (User u : users) {
			if (u.isOversubscribed()) {
				u.setStatus(User.Status.pendingSubscription);
				entityManager.merge(u);
				entityManager.createQuery("update SubscriptionActivity set endedOn = :now where user = :user and endedOn is null").setParameter("now", new Date()).setParameter("user", u).executeUpdate();
			}
		}
	}
}
