package com.example.spotify_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import entity.Artist;
import entity.JsonPlaceHolderUser;
import entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlaylistActivity extends AppCompatActivity {
    private String TOKEN;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Intent intent = getIntent();
        this.TOKEN = intent.getStringExtra("TOKEN_STRING");
        //Toast.makeText(getApplicationContext(), TOKEN, Toast.LENGTH_LONG).show();
        fetchUserId();
    }

    private void fetchUserId(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderUser user = retrofit.create(JsonPlaceHolderUser.class);
        Call<User> call = user.getUser("Bearer " + TOKEN);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    userID = response.body().getId();
                    Log.d("user id: ", userID);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error", "onFailure: " +  t.getMessage());
            }
        });
    }

    public String getUserID(){
        return userID;
    }
}