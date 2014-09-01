package org.sbassin.rest.types;

import java.io.Serializable;

public class LocationTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private float latitude;

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

    public void setLongitude(final float longitude) {
        this.longitude = longitude;
    }
}
