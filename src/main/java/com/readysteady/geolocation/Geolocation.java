package com.readysteady.geolocation;

import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToOne;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Autogenerated by APIOMatic.
 * 
 */
public class Geolocation
    implements Serializable
{

    @OneToOne
    private List<GeolocationResult> results;
    @OneToOne
    private String status;
    private final static long serialVersionUID = 6173609260853618472L;

    public Geolocation() {
    }

    public List<GeolocationResult> getResults() {
        return results;
    }

    public void setResults(final List<GeolocationResult> someResults) {
        this.results = someResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String aStatus) {
        this.status = aStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("results", results).append("status", status).toString();
    }

}
