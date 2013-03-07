package com.readysteady.user.session;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.apiomatic.rest.DataTransferObject;

@Entity
public class SessionData implements DataTransferObject<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -44098721827737286L;
	@Id
	private String id = UUID.randomUUID().toString();
	@Column(name = "keyName")
	private String key;
	private String value;
	@ManyToOne
	private Session session;

	public SessionData() {
	}

	public SessionData(final String aKey, final String aValue) {
		key = aKey;
		value = aValue;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public Session getSession() {
		return session;
	}

	public String getValue() {
		return value;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public void setSession(final Session session) {
		this.session = session;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("key", key).append("value", value);
		return builder.toString();
	}

}
