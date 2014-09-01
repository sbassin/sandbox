package org.sbassin.rest.types;

import java.io.Serializable;

public class StatisticsTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private double max;

    private double mean;

    private double min;

    private long sampleSize;

    private double standardDeviation;

    private String units;

    private double variance;

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public long getSampleSize() {
        return sampleSize;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public String getUnits() {
        return units;
    }

    public double getVariance() {
        return variance;
    }

    public void setMax(final double max) {
        this.max = max;
    }

    public void setMean(final double mean) {
        this.mean = mean;
    }

    public void setMin(final double min) {
        this.min = min;
    }

    public void setSampleSize(final long sampleSize) {
        this.sampleSize = sampleSize;
    }

    public void setStandardDeviation(final double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public void setUnits(final String units) {
        this.units = units;
    }

    public void setVariance(final double variance) {
        this.variance = variance;
    }
}
