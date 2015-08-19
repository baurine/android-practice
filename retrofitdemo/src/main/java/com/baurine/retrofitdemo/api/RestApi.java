package com.baurine.retrofitdemo.api;

import com.baurine.retrofitdemo.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by baurine on 8/19/15.
 */
public interface RestApi {

    @GET("/feeds/flowers.json")
    void getFlowers(Callback<List<Flower>> response);
}
