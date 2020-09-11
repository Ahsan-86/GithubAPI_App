package com.ahsan.githubapi_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahsan.githubapi_app.R;
import com.ahsan.githubapi_app.model.GithubRepo;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder>{
    private List<GithubRepo> repos;
    private Context context;

    public List<GithubRepo> getRepos() {
        return repos;
    }

    public void setRepos(List<GithubRepo> repos) {
        this.repos = repos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ReposAdapter(List<GithubRepo> repos, Context context) {
        this.repos = repos;
        this.context = context;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repo, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        holder.repoName.setText(repos.get(position).getName());
        if(repos.get(position).getDescription() != null)
            holder.repoDescription.setText(repos.get(position).getDescription());
        else
            holder.repoDescription.setText("no description provided.");
        holder.repoLanguage.setText(repos.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class ReposViewHolder extends RecyclerView.ViewHolder{
        TextView repoName;
        TextView repoDescription;
        TextView repoLanguage;

        public ReposViewHolder(View v){
            super(v);
            repoName = v.findViewById(R.id.repoName);
            repoDescription = v.findViewById(R.id.repoDescription);
            repoLanguage = v.findViewById(R.id.repoLanguage);
        }
    }
}
