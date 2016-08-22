package com.example.android.sunshine.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by chetna_priya on 8/15/2016.
 */
public class DataLayerListenerService extends WearableListenerService {


    private static final String TODAY_WEATHER_PATH = "/today_weather";
    private static final String WEATHER_ASSET_TODAY_KEY = "weather_asset_today";
    private static final String WETHER_DESC_TODAY_KEY = "weather_desc_today";
    private static final String MAX_TEMP_TODAY_KEY = "max_temp_today";
    private static final String MIN_TEMP_TODAY_KEY = "min_temp_today";
    private static final String HUMIDITY_TODAY_KEY = "humidity_today";
    private static final String PRESSURE_TODAY_KEY = "pressure_today";
    private static final String TAG = DataLayerListenerService.class.getSimpleName();
    private static final String FRIENDLY_DAY_STRING = "day_string";
    private GoogleApiClient mGoogleApiClient;
    private WearDataObject wearDataObject;

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged(): " + dataEvents);

        for (DataEvent event : dataEvents) {
                String path = event.getDataItem().getUri().getPath();
                if (TODAY_WEATHER_PATH.equals(path)) {
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Wearable.API)
                            .build();

                    ConnectionResult connectionResult =
                            mGoogleApiClient.blockingConnect(30, TimeUnit.SECONDS);

                    if (!connectionResult.isSuccess()) {
                        Log.e(TAG, "Failed to connect to GoogleApiClient.");
                        return;
                    }

                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    Asset photoAsset = dataMapItem.getDataMap()
                            .getAsset(WEATHER_ASSET_TODAY_KEY);
                    String dayString = dataMapItem.getDataMap().getString(FRIENDLY_DAY_STRING);
                    String maxTemp = dataMapItem.getDataMap().getString(MAX_TEMP_TODAY_KEY);
                    String minTemp = dataMapItem.getDataMap().getString(MIN_TEMP_TODAY_KEY);
                    String weatherDesc =  dataMapItem.getDataMap().getString(WETHER_DESC_TODAY_KEY);
                    String humidity = dataMapItem.getDataMap().getString(HUMIDITY_TODAY_KEY);
                    String pressure = dataMapItem.getDataMap().getString(PRESSURE_TODAY_KEY);
                    if(humidity != null && pressure != null && weatherDesc != null
                            && maxTemp != null && minTemp != null)
                        wearDataObject= new WearDataObject(dayString,weatherDesc, maxTemp, minTemp,
                            pressure, humidity);
                    else if(maxTemp != null && minTemp != null)
                        wearDataObject= new WearDataObject(maxTemp, minTemp);
                    Log.d(TAG, "MAX TEMP: "+maxTemp);
                    Log.d(TAG, "MIN TEMP: "+minTemp);
                    Log.d(TAG, "DESCRIPTION: "+weatherDesc);
                    Log.d(TAG, "HUMIDITY: "+humidity);
                    Log.d(TAG, "PRESSURE: "+pressure);/*
                    mTextView.setText(mTextView.getText()+"\n"+"Max Temp: "+maxTemp
                            +"\nMin Temp: "+minTemp+"\nHumidity: "+humidity+"\nPressure: "+pressure);*/
                    // Loads image on background thread.
                    if(photoAsset != null)
                        new LoadBitmapAsyncTask().execute(photoAsset);
                    else
                        broadcastUpdatedWeather(null);

                }
        }
    }
    private class LoadBitmapAsyncTask extends AsyncTask<Asset, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Asset... params) {

            if (params.length > 0) {

                Asset asset = params[0];

                InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                        mGoogleApiClient, asset).await().getInputStream();

                if (assetInputStream == null) {
                    Log.w(TAG, "Requested an unknown Asset.");
                    return null;
                }
                return BitmapFactory.decodeStream(assetInputStream);

            } else {
                Log.e(TAG, "Asset must be non-null");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

           /* if (bitmap != null)*/ {
                Log.d(TAG, "Setting background image on second page..");
                broadcastUpdatedWeather(bitmap);
            }
        }
    }

    private void broadcastUpdatedWeather(Bitmap bitmap) {
        Intent intent = new Intent();
        if(bitmap != null)
            intent.putExtra(getResources().getString(R.string.bitmap_resource_key), bitmap);
        intent.putExtra(getResources().getString(R.string.weather_object_key), wearDataObject);
        intent.setAction(getResources().getString(R.string.broadcast_weather));
        mGoogleApiClient.disconnect();
        wearDataObject = null;
        Log.d(TAG, "BROADCAST THE DATA CHANGESSSSSSSSSSSSS");
        sendBroadcast(intent);
    }
}
