package org.sbassin.util;

import org.sbassin.model.Location;

public class DistanceUtils {

    public enum DistanceUnit {
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
        },
        Kilometers {
            @Override
            public double getMultiplier() {
                return 1.609344;
            }
        };

        public abstract double getMultiplier();
    }

    public static double distance(Location location1, Location location2, DistanceUnit unit) {
        double theta = location1.getLongitude() - location2.getLongitude();
        double dist = Math.sin(deg2rad(location1.getLatitude())) * Math.sin(deg2rad(location2.getLatitude()))
                + Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(location1.getLatitude()))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        return dist * unit.getMultiplier();
    }

    /**
     * This function converts decimal degrees to radians.
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees.
     */
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
