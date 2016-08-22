package com.example.android.sunshine.app;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by chetna_priya on 8/15/2016.
 */
public class WearDataObject implements Serializable
{

    private String maxTemp, minTemp;
    private String pressure;
    private String humidity;
    private String description;
    private String dayString;

    public WearDataObject(String dayString, String description, String maxTemp, String minTemp, String pressure, String humidity){

        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dayString = dayString;
    }

    public WearDataObject(String maxTemp, String minTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDayString() {
        return dayString;
    }

    public String getDescription() {
        return description;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }


}
