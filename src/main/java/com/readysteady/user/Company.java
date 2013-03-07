package com.readysteady.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Node;

import com.cpn.apiomatic.annotation.Documentation;
import com.cpn.apiomatic.xml.XMLUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Documentation("Company type holds details like name, country, email, phone number, " +
		"website and members of the company")
@Entity
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6928783019739325158L;

	public static Company unmarshall(final Node node) {
		final Company c = new Company();
		final XMLUtil x = new XMLUtil(node);
		try {
			c.name = x.get("name");
			c.email = x.get("email");
			c.id = x.get("uuid");
			c.phoneNumber = x.get("phoneNumber");
			c.website = x.get("website");
			c.country = x.get("country");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return c;

	}

	@Id
	private String id = UUID.randomUUID().toString();

	private String name;
	private String country;
	private String email;
	private String phoneNumber;

	private String website;

	@OneToMany(targetEntity = User.class, mappedBy = "company")
	@JsonIgnore
	private List<User> members = new ArrayList<>();

	public Company() {
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public List<User> getMembers() {
		return members;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setMembers(final List<User> members) {
		this.members = members;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("name", name).append("country", country).append("email", email).append("phoneNumber", phoneNumber).append("website", website);
		return builder.toString();
	}

}
