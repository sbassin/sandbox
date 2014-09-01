package org.sbassin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.common.base.MoreObjects;

@Embeddable
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "lat", precision = 12, scale = 0)
    private float latitude;

    @Column(name = "long", precision = 12, scale = 0)
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLatitude(final float longitude) {
        this.latitude = longitude;
    }

    public Location(Double latitude, Double longitude) {
        this();
        this.latitude = latitude.floatValue();
        this.longitude = longitude.floatValue();
    }

    public Location() {
        super();
    }

    public void setLongitude(final float longitude) {
        this.longitude = longitude;
    }
    
    public String toString() {
        return MoreObjects.toStringHelper(this).add("latitude", latitude).add("longitude", longitude).toString();
    }
}
