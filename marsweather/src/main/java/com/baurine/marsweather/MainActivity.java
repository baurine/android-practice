package com.baurine.marsweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvMars;
    private TextView mTvError, mTvDegree, mTvWeather;
    private MarsWeather appInstance = MarsWeather.getInstance();

    private SharedPreferences mSharedPref;
    private int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private final static String
            SHARED_PREFS_IMG_KEY = "img",
            SHARED_PREFS_DAY_KEY = "day",
            MARS_WEATHER_API = "http://marsweather.ingenology.com/v1/latest/",
            SEARCH_MARS_IMG =
                    "http://image.baidu.com/i?tn=baiduimagejson&word=planet%20site%3Awww.nasa.gov&rn=10&pn=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvMars = (ImageView) findViewById(R.id.iv_mars);
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvDegree = (TextView) findViewById(R.id.tv_degree);
        mTvWeather = (TextView) findViewById(R.id.tv_weather);

        mSharedPref = getPreferences(Context.MODE_PRIVATE);

        loadMarsWeather();
        loadMarsImage();
    }

    private void loadMarsWeather() {
        CustomJsonRequest req = new CustomJsonRequest(Request.Method.GET, MARS_WEATHER_API, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String minTemp, maxTemp, atmo;
                            int avgTemp;

                            response = response.getJSONObject("report");

                            minTemp = response.getString("min_temp");
                            minTemp = minTemp.substring(0, minTemp.indexOf("."));
                            maxTemp = response.getString("max_temp");
                            maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));
                            avgTemp = (Integer.parseInt(minTemp) + Integer.parseInt(maxTemp)) / 2;

                            atmo = response.getString("atmo_opacity");

                            showWeather(avgTemp, atmo);
                        } catch (Exception e) {
                            showError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error);
                    }
                });
        req.setPriority(Request.Priority.HIGH);
        appInstance.add(req);
    }

    private void loadMarsImage() {
        if (mSharedPref.getInt(SHARED_PREFS_DAY_KEY, 0) != today) {
            searchRandomImage();
        } else {
            loadImage(mSharedPref.getString(SHARED_PREFS_IMG_KEY, ""));
        }
    }

    private void showError(Exception e) {
        mTvDegree.setVisibility(View.GONE);
        mTvWeather.setVisibility(View.GONE);
        mTvError.setVisibility(View.VISIBLE);
        e.printStackTrace();
    }

    private void showWeather(int degree, String atmo) {
        mTvError.setVisibility(View.GONE);
        mTvDegree.setVisibility(View.VISIBLE);
        mTvWeather.setVisibility(View.VISIBLE);
        mTvDegree.setText(degree + "°");
        mTvWeather.setText(atmo);
    }

    private void searchRandomImage() {
        CustomJsonRequest req = new CustomJsonRequest(Request.Method.GET, SEARCH_MARS_IMG, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray images = response.getJSONArray("data");
                            int index = new Random().nextInt(images.length());
                            JSONObject item = images.getJSONObject(index);

                            String imgUrl = item.getString("objURL");
                            saveImageUrl(imgUrl);
                            loadImage(imgUrl);
                        } catch (Exception e) {
                            imageError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageError(error);
                    }
                });
        req.setPriority(Request.Priority.LOW);
        appInstance.add(req);
    }

    private void saveImageUrl(String imgUrl) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(SHARED_PREFS_DAY_KEY, today);
        editor.putString(SHARED_PREFS_IMG_KEY, imgUrl);
        editor.apply();
    }

    private void loadImage(String imgUrl) {
        ImageRequest req = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mIvMars.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageError(error);
                    }
                });
        appInstance.add(req);
    }

    private void imageError(Exception e) {
        int mainColor = Color.parseColor("#FF5722");
        mIvMars.setBackgroundColor(mainColor);
        e.printStackTrace();
    }
}
