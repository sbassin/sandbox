package org.sbassin.util;

import org.sbassin.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceUtils {

    public enum DistanceUnit {
        Kilometers {
            @Override
            public double getMultiplier() {
                return 1.609344;
            }
        },
        Miles {
            @Override
            public double getMultiplier() {
                return 1;
            }
        },
        NauticalMiles {
            @Override
            public double getMultiplier() {
                return 0.8684;
            }
        };

        public abstract double getMultiplier();
    }

    private static Logger logger = LoggerFactory.getLogger(DistanceUtils.class);

    /**
     * This function converts decimal degrees to radians.
     */
    private static double deg2rad(final double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double distance(final Location location1, final Location location2, final DistanceUnit unit) {
        final double theta = location1.getLongitude() - location2.getLongitude();
        double dist = Math.sin(deg2rad(location1.getLatitude())) * Math.sin(deg2rad(location2.getLatitude()))
                + Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(location1.getLatitude()))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist *= unit.getMultiplier();

        if (Double.isNaN(dist) && logger.isTraceEnabled()) {
            logger.trace(
                    "{} * {} + {} * {} * {} = {}",
                    Math.sin(deg2rad(location1.getLatitude())),
                    Math.sin(deg2rad(location2.getLatitude())),
                    Math.cos(deg2rad(location1.getLatitude())),
                    Math.cos(deg2rad(location1.getLatitude())),
                    Math.cos(deg2rad(theta)),
                    Math.sin(deg2rad(location1.getLatitude())) * Math.sin(deg2rad(location2.getLatitude()))
                            + Math.cos(deg2rad(location1.getLatitude()))
                            * Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(theta)));
            logger.trace(
                    "acos({}) = {}",
                    Math.sin(deg2rad(location1.getLatitude())) * Math.sin(deg2rad(location2.getLatitude()))
                            + Math.cos(deg2rad(location1.getLatitude()))
                            * Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(theta)),
                    Math.acos(Math.sin(deg2rad(location1.getLatitude()))
                            * Math.sin(deg2rad(location2.getLatitude()))
                            + Math.cos(deg2rad(location1.getLatitude()))
                            * Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(theta))));
            logger.trace(
                    "rad2deg({}) = {}",
                    Math.acos(Math.sin(deg2rad(location1.getLatitude()))
                            * Math.sin(deg2rad(location2.getLatitude()))
                            + Math.cos(deg2rad(location1.getLatitude()))
                            * Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(theta))),
                    rad2deg(Math.acos(Math.sin(deg2rad(location1.getLatitude()))
                            * Math.sin(deg2rad(location2.getLatitude()))
                            + Math.cos(deg2rad(location1.getLatitude()))
                            * Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(theta)))));
        }

        return dist;
    }

    /**
     * This function converts radians to decimal degrees.
     */
    private static double rad2deg(final double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
