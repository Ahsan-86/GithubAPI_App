package com.ahsan.githubapi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahsan.githubapi_app.model.GithubUser;
import com.ahsan.githubapi_app.rest.APIClient;
import com.ahsan.githubapi_app.rest.GitHubUserEndPoints;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView avatarImage;
    TextView userName;
    TextView followers;
    TextView following;
    TextView logIn;
    TextView email;
    Button btn_ownedRepos;
    String newString;
    ProgressBar progressBar;
    Bitmap myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        if(getIntent().getStringExtra("STRING_I_NEED") != null){
            newString = getIntent().getStringExtra("STRING_I_NEED");
//            Toast.makeText(UserActivity.this, getIntent().getStringExtra("STRING_I_NEED"), Toast.LENGTH_SHORT).show();
        }

        progressBar = findViewById(R.id.progressBar);
        avatarImage = findViewById(R.id.avatar);
        userName = findViewById(R.id.username);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        logIn = findViewById(R.id.logIn);
        email = findViewById(R.id.email);
        btn_ownedRepos = findViewById(R.id.btn_ownedRepos);
        btn_ownedRepos.setOnClickListener(this);

        setVisibilities();
        loadData();
    }

    private void setVisibilities() {
        avatarImage.setVisibility(View.GONE);
        userName.setVisibility(View.GONE);
        followers.setVisibility(View.GONE);
        following.setVisibility(View.GONE);
        logIn.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        btn_ownedRepos.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void reverseVisibilities() {
        avatarImage.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        followers.setVisibility(View.VISIBLE);
        following.setVisibility(View.VISIBLE);
        logIn.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        btn_ownedRepos.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void loadData() {
        if(newString != null) {
            final GitHubUserEndPoints apiService =
                    APIClient.getClient().create(GitHubUserEndPoints.class);
            Call<GithubUser> call = apiService.getUser(newString);
            call.enqueue(new Callback<GithubUser>() {
                @Override
                public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                    reverseVisibilities();
                    if(response.body().getName() != null) {
                        userName.setText("Username: " + response.body().getName());
                    }else{
                        userName.setText("Username: " + "no name provided");
                    }

                    followers.setText("Followers: " + response.body().getFollowers());
                    following.setText("Following: " + response.body().getFollowing());
                    logIn.setText("Login: " + response.body().getLogin());

                    if(response.body().getEmail() != null){
                        email.setText("Email: " + response.body().getEmail());
                    }else{
                        email.setText("Email: " + "no email provided");
                    }

                    ImageDownloader task = new ImageDownloader();
                    try{
                        myImage = task.execute(response.body().getAvatar()).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    avatarImage.setImageBitmap(myImage);
                    avatarImage.getLayoutParams().height = 200;
                    avatarImage.getLayoutParams().width = 200;
                }

                @Override
                public void onFailure(Call<GithubUser> call, Throwable t) {
                    Log.e("UserActivity", t.toString());
                }
            });
        }else{
            Toast.makeText(UserActivity.this, "No Username received!", Toast.LENGTH_SHORT).show();
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ownedRepos:
                loadOwnRepos();
        }
    }

    private void loadOwnRepos() {
        Intent intent = new Intent(UserActivity.this, RepositoriesActivity.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }
}
