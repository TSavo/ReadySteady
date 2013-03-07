package com.readysteady.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cpn.apiomatic.rest.DataTransferObject;

@Entity
public class SubscriptionActivity implements DataTransferObject<String> {

	@Id
	private String id = UUID.randomUUID().toString();
	private Date createdOn = new Date();
	private Date startedOn = new Date();
	private Date endedOn;
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getStartedOn() {
		return startedOn;
	}

	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}

	public Date getEndedOn() {
		return endedOn;
	}

	public long getDuration() {
		if (endedOn == null) {
			return System.currentTimeMillis() - startedOn.getTime();
		}
		return endedOn.getTime() - startedOn.getTime();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
