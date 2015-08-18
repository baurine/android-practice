package com.baurine.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baurine.retrofitdemo.api.GitHubApi;
import com.baurine.retrofitdemo.api.GitHubClient;
import com.baurine.retrofitdemo.model.GitHubUser;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private GitHubApi githubApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        githubApi = GitHubClient.getApiService();

        final TextView tvName = (TextView) findViewById(R.id.tv_name);
        Button btnGetName = (Button) findViewById(R.id.btn_getuser);
        btnGetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                githubApi.getUser("baurine", new Callback<GitHubUser>() {
                    @Override
                    public void success(GitHubUser gitHubUser, Response response) {
                        tvName.setText(gitHubUser.getName());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        tvName.setText(error.getMessage());
                    }
                });
            }
        });
    }
}
