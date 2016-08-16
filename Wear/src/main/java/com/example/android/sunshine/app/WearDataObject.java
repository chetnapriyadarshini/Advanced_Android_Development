package com.example.android.sunshine.app;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by chetna_priya on 8/15/2016.
 */
public class WearDataObject implements Serializable
{

    private String maxTemp, minTemp;
    private float pressure;
    private float humidity;
    private String description;

    public WearDataObject(String description, String maxTemp, String minTemp, float pressure, float humidity){

        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.pressure = pressure;
        this.humidity = humidity;
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

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }


}
