package com.ahsan.githubapi_app.model;

import com.google.gson.annotations.SerializedName;

public class GithubRepo {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("language")
    private String language;

    private GithubRepo() {}

    public GithubRepo(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
