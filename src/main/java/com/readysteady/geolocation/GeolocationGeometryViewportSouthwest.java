package com.readysteady.geolocation;

import java.io.Serializable;
import javax.persistence.OneToOne;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Autogenerated by APIOMatic.
 * 
 */
public class GeolocationGeometryViewportSouthwest
    implements Serializable
{

    @OneToOne
    private Float lat;
    @OneToOne
    private Float lng;
    private final static long serialVersionUID = 6401613888246849383L;

    public GeolocationGeometryViewportSouthwest() {
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(final Float aLat) {
        this.lat = aLat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(final Float aLng) {
        this.lng = aLng;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("lat", lat).append("lng", lng).toString();
    }

}
