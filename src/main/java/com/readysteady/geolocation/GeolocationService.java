package com.readysteady.geolocation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.cpn.apiomatic.rest.RestCommand;

public class GeolocationService {

	public static Geolocation getGeolocation(String anAddress) {
		try {
			return new RestCommand<String, Geolocation>("http://maps.googleapis.com/maps/api/geocode/json?sensor=true&address=" + URLEncoder.encode(anAddress, org.apache.commons.lang.CharEncoding.UTF_8), Geolocation.class).get();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
