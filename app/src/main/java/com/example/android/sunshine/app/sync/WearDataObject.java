package com.example.android.sunshine.app.sync;

/**
 * Created by chetna_priya on 8/15/2016.
 */
public class WearDataObject
{

    private float maxTemp, minTemp;
    private float pressure;
    private float humidity;
    private String description;
    private int weatherId;

    public WearDataObject(int weatherId, String description, float maxTemp, float minTemp, float pressure, float humidity){

        this.weatherId = weatherId;
        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.pressure = pressure;
        this.humidity = humidity;
    }


    public int getWeatherId() {
        return weatherId;
    }

    public String getDescription() {
        return description;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }


}
