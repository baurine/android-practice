package com.baurine.retrofitdemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baurine.retrofitdemo.adapter.FlowerAdapter;
import com.baurine.retrofitdemo.api.RestApi;
import com.baurine.retrofitdemo.api.RestClient;
import com.baurine.retrofitdemo.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestApi api = RestClient.getApiService();

        api.getFlowers(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                FlowerAdapter adapter = new FlowerAdapter(getApplicationContext(),
                        R.layout.item_flower, flowers);
                setListAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
