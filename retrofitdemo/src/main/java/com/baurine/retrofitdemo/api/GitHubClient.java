package com.baurine.retrofitdemo.api;

import retrofit.RestAdapter;

/**
 * Created by baurine on 8/13/15.
 */
public class GitHubClient {
    private static final String ENDPOINT = "https://api.github.com";

    private static GitHubApi apiService;

    public static GitHubApi getApiService() {
        if (apiService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            apiService = restAdapter.create(GitHubApi.class);
        }

        return apiService;
    }
}
