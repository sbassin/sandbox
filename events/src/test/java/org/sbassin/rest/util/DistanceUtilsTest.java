package org.sbassin.rest.util;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sbassin.model.Location;
import org.sbassin.rest.util.DistanceUtils.DistanceUnit;

import com.google.common.collect.Lists;

@RunWith(Parameterized.class)
public class DistanceUtilsTest {
    @Parameters
    public static Collection<Object[]> addedNumbers() {
        Collection<Object[]> values = Lists.newArrayList();

        values.add(new Object[] { new Location(39.9245, 105.061), new Location(39.9144, 105.072),
                DistanceUnit.Miles, 0.7548674079583159 });
        values.add(new Object[] { new Location(39.5102, 104.722), new Location(39.5199, 104.772),
                DistanceUnit.Miles, Double.NaN });

        return values;
    }

    private DistanceUnit distanceUnit;
    private double expectedDistance;
    private Location location1;
    private Location location2;

    public DistanceUtilsTest(Location location1, Location location2, DistanceUnit distanceUnit,
            double expectedDistance) {
        super();
        this.location1 = location1;
        this.location2 = location2;
        this.distanceUnit = distanceUnit;
        this.expectedDistance = expectedDistance;
    }

    @Test
    public void test() {
        double actualDistance = DistanceUtils.distance(location1, location2, distanceUnit);
        if (Double.isNaN(expectedDistance)) {
            assertTrue(Double.isNaN(actualDistance));
        } else {
            assertEquals(expectedDistance, actualDistance, 0.001d);
        }
    }
}
