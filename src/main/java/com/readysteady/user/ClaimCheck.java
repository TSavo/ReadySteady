package com.readysteady.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ClaimCheck {

	@Id
	private String id = UUID.randomUUID().toString();
	@OneToOne(cascade = CascadeType.MERGE)
	private User user;
	private boolean claimed = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn = new Date();

	public ClaimCheck() {
	}

	public ClaimCheck(final User aUser) {
		user = aUser;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(final boolean claimed) {
		this.claimed = claimed;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
