package com.baurine.marsweather;

import android.app.Application;
import android.nfc.Tag;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MarsWeather extends Application {

    private static final String TAG = MarsWeather.class.getName();

    private RequestQueue mRequestQueue;
    private static MarsWeather mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static MarsWeather getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(TAG);
    }
}
