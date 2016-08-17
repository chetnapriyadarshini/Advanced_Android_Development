package com.example.android.sunshine.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    public static class WeatherDataReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            WearDataObject wearDataObject = (WearDataObject) intent.getSerializableExtra
                    (context.getString(R.string.weather_object_key));
            if(wearDataObject.getHumidity() == null){
                //  Only temprature has been updated we keep other fields as it is
                mTextView.setText("MaxTemp: "+wearDataObject.getMaxTemp()+"\n"+
                        "MinTemp: "+wearDataObject.getMinTemp());
            }else {
                mTextView.setText("MaxTemp: " + wearDataObject.getMaxTemp() + "\n" +
                        "MinTemp: " + wearDataObject.getMinTemp() + "\n" +
                        "Humidity: " + wearDataObject.getHumidity() + "\n" +
                        "Pressure: " + wearDataObject.getPressure());
            }

        }
    }
}
