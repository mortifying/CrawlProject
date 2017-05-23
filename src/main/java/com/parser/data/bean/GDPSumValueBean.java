package com.parser.data.bean;

/**
 * Created by root on 2017/5/5.
 */
public class GDPSumValueBean {
    private String GDPSumValue;
    private String region;
    private String year;
    private String quarter;
    private double GDPValue;

    public String getGDPSumValue() {
        return GDPSumValue;
    }

    public void setGDPSumValue(String GDPSumValue) {
        this.GDPSumValue = GDPSumValue;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public double getGDPValue() {
        return GDPValue;
    }

    public void setGDPValue(double GDPValue) {
        this.GDPValue = GDPValue;
    }
}
