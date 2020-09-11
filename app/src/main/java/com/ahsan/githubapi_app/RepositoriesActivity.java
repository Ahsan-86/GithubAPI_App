package com.ahsan.githubapi_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ahsan.githubapi_app.adapter.ReposAdapter;
import com.ahsan.githubapi_app.model.GithubRepo;
import com.ahsan.githubapi_app.rest.APIClient;
import com.ahsan.githubapi_app.rest.GithubRepoEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoriesActivity extends AppCompatActivity {

    String recievedUserName;
    TextView tv_UserName;
    RecyclerView rv_Repos;
    List<GithubRepo> repos = new ArrayList<>();
    ReposAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        if(getIntent().getStringExtra("username") != null){
            recievedUserName = getIntent().getStringExtra("username");
//            Toast.makeText(UserActivity.this, getIntent().getStringExtra("username"), Toast.LENGTH_SHORT).show();
        }

        tv_UserName = findViewById(R.id.tvUsername);
        tv_UserName.setText("User: "+recievedUserName);

        rv_Repos = findViewById(R.id.rvRepos);
        rv_Repos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReposAdapter(repos, getApplicationContext());
        rv_Repos.setAdapter(adapter);

        loadRepositories();
    }

    private void loadRepositories() {
        GithubRepoEndPoints apiSerview =
                APIClient.getClient().create(GithubRepoEndPoints.class);

        Call<List<GithubRepo>> call = apiSerview.getRepo(recievedUserName);
        call.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                repos.clear();
                repos.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Log.e("Repos", t.toString());
            }
        });
    }
}
