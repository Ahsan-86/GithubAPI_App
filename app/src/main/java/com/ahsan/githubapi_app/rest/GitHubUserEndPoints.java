package com.ahsan.githubapi_app.rest;

import com.ahsan.githubapi_app.model.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserEndPoints {
    @GET("users/{user}")
    Call<GithubUser> getUser(@Path("user") String user);
}
