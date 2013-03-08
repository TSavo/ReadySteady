package com.readysteady.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Match {

	@Id
	private String id = UUID.randomUUID().toString();
	@OneToOne
	private User user1;
	@OneToOne
	private User user2;
	@OneToOne
	private User matcher;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn = new Date();
	@ManyToMany
	@JoinTable(name = "MatchNotes", joinColumns = { @JoinColumn(name = "match_id") }, inverseJoinColumns = { @JoinColumn(name = "note_id") })
	private List<Note> notes = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public User getMatcher() {
		return matcher;
	}
	public void setMatcher(User matcher) {
		this.matcher = matcher;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

}
