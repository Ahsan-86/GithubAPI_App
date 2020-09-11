package com.ahsan.githubapi_app.rest;

import com.ahsan.githubapi_app.model.GithubRepo;
import com.ahsan.githubapi_app.model.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubRepoEndPoints {
    @GET("users/{user}/repos")
    Call<List<GithubRepo>> getRepo(@Path("user") String name);
}
