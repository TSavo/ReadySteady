package com.readysteady.geolocation;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeolocationTest {

	@Test
	public void test() {
		Geolocation geo = GeolocationService.getGeolocation("406 N State St apt 2 - Ann Arbor, MI");
		System.out.println(geo);
	}

}
