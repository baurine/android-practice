package com.baurine.retrofitdemo.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by baurine on 8/13/15.
 */
public class GitHubClient {
    private static final String ENDPOINT = "https://api.github.com";

    private static GitHubApi apiService;

    public static GitHubApi getApiService() {
        if (apiService == null) {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // 必须设置 User-Agent，否则返回 403
                    // see https://developer.github.com/v3/#user-agent-required
                    request.addHeader("User-Agent", "Retrofit-Sample-App");
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            apiService = restAdapter.create(GitHubApi.class);
        }

        return apiService;
    }
}
