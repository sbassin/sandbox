package org.sbassin.rest.converters;

import org.sbassin.model.Location;
import org.sbassin.rest.types.LocationTO;

import com.google.common.base.Converter;

public class LocationConverter extends Converter<Location, LocationTO> {

    @Override
    protected Location doBackward(final LocationTO transfer) {
        final Location location = new Location();

        location.setLatitude(transfer.getLatitude());
        location.setLongitude(transfer.getLongitude());
        return location;
    }

    @Override
    protected LocationTO doForward(final Location location) {
        final LocationTO transfer = new LocationTO();

        transfer.setLatitude(location.getLatitude());
        transfer.setLongitude(location.getLongitude());
        return transfer;
    }

}
