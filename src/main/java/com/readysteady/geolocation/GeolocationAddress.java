package com.readysteady.geolocation;

import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Autogenerated by APIOMatic.
 * 
 */
public class GeolocationAddress
    implements Serializable
{

    @OneToOne
    @JsonProperty("long_name")
    private String longName;
    @OneToOne
    @JsonProperty("short_name")
    private String shortName;
    @OneToOne
    private List<String> types;
    private final static long serialVersionUID = 8841447473493400114L;

    public GeolocationAddress() {
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(final String aLongName) {
        this.longName = aLongName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String aShortName) {
        this.shortName = aShortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(final List<String> someTypes) {
        this.types = someTypes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("longName", longName).append("shortName", shortName).append("types", types).toString();
    }

}