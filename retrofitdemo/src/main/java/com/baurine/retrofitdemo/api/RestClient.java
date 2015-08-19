package com.baurine.retrofitdemo.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by baurine on 8/19/15.
 */
public class RestClient {
    private final static String EDNPOINT = "http://services.hanselandpetal.com";

    private static RestApi apiService;

    public static RestApi getApiService() {
        if (apiService == null) {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", "Retrofit-Sample-App");
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(EDNPOINT)
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            apiService = restAdapter.create(RestApi.class);
        }
        return apiService;
    }
}
