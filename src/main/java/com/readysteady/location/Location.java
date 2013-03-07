package com.readysteady.location;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location {

	@Id
	String id = UUID.randomUUID().toString();
	String address;
	String normalizedAddress;
	float latitude;
	float longitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNormalizedAddress() {
		return normalizedAddress;
	}

	public void setNormalizedAddress(String normalizedAddress) {
		this.normalizedAddress = normalizedAddress;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
