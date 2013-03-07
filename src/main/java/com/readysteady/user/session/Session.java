package com.readysteady.user.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.apiomatic.rest.DataTransferObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.readysteady.user.User;

@Entity
public class Session implements DataTransferObject<String> {

	private static final long serialVersionUID = -468421830309668406L;

	@Id
	public String id = UUID.randomUUID().toString();

	@OneToOne
	@JsonIgnore
	public User user;
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date expiresAt;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = SessionData.class, mappedBy = "session", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<SessionData> sessionData = new ArrayList<>();

	public Session() {
	}

	public Session(final User aUser, final Date anExpiresAt) {
		user = aUser;
		expiresAt = anExpiresAt;
	}

	public String get(final String aKey) {
		for (final SessionData data : sessionData) {
			if (aKey.toLowerCase().equals(data.getKey().toLowerCase())) {
				return data.getValue();
			}
		}
		return null;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	@Override
	public String getId() {
		return id;
	}

	public List<SessionData> getSessionData() {
		return sessionData;
	}

	public User getUser() {
		return user;
	}

	public void setExpiresAt(final Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setSessionData(final List<SessionData> sessionData) {
		this.sessionData = sessionData;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("user", user).append("expiresAt", expiresAt).append("sessionData", sessionData);
		return builder.toString();
	}

}
