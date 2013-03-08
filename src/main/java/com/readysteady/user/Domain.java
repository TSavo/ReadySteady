package com.readysteady.user;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.readysteady.location.Location;

@Entity
public class Domain {

	@Id
	private String id = UUID.randomUUID().toString();
	@OneToOne
	private Location location;
	@OneToOne
	private User owner;
	@OneToMany(mappedBy="domain")
	private List<User> users;
}
