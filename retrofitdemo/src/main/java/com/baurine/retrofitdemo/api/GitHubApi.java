package com.baurine.retrofitdemo.api;

import com.baurine.retrofitdemo.model.GitHubUser;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by baurine on 8/13/15.
 */
public interface GitHubApi {

    // 必须设置 User-Agent，否则返回 403
    // see https://developer.github.com/v3/#user-agent-required
    // @Headers("User-Agent: Retrofit-Sample-App")
    @GET("/users/{user}")
    void getUser(@Path("user") String user, Callback<GitHubUser> response);
}
