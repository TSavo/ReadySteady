package com.readysteady.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.cpn.apiomatic.rest.DataTransferObject;
import com.readysteady.location.Location;

@Entity
public class User implements DataTransferObject<String> {

	private static final long serialVersionUID = -5889559329276233923L;

	@Id
	private String id = UUID.randomUUID().toString();

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String alternatePhone;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Location location;
	private Date createdOn = new Date();
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Subscription> subscriptions = new ArrayList<>();
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<SubscriptionActivity> subscriptionActivies = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private Status status = Status.pendingVerification;
	@Enumerated(EnumType.STRING)
	private Role role = Role.user;
	@ManyToMany
	@JoinTable(name="UserMatches", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="match_id")})
	private List<User> matches;
	

	@ManyToOne
	private Domain domain;

	public enum Role {
		user, admin, owner, superUser;
	}

	public enum Status {
		pendingVerification, pendingSubscription, active, onHold, suspended;
	}

	public long getTotalSubscriptionLength() {
		long total = 0;
		for (Subscription s : subscriptions) {
			total += s.getDuration();
		}
		return total;
	}

	public long getTotalSubscriptionUsed() {
		long total = 0;
		for (SubscriptionActivity s : subscriptionActivies) {
			total += s.getDuration();
		}
		return total;
	}

	public boolean isOversubscribed() {
		return getTotalSubscriptionUsed() > getTotalSubscriptionLength();
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public List<SubscriptionActivity> getSubscriptionActivies() {
		return subscriptionActivies;
	}

	public void setSubscriptionActivies(List<SubscriptionActivity> subscriptionActivies) {
		this.subscriptionActivies = subscriptionActivies;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		final User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setStatus(Status aStatus) {
		status = aStatus;
	}

	public Status getStatus() {
		return status;
	}

	public List<User> getMatches() {
		return matches;
	}

	public void setMatches(List<User> matches) {
		this.matches = matches;
	}

}
