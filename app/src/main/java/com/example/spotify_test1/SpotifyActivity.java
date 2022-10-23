package com.example.spotify_test1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import entity.Artist;
import entity.JsonPlaceHolderArtists;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SpotifyActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "78698dcc31fd4845919e96fc61514de8";
    private static final String REDIRECT_URI = "http://localhost:8888/";
    private String TOKEN = "";
    private TextView info;
    private ImageView icon;

    private TextView name;
    private SpotifyAppRemote mSpotifyAppRemote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        info = findViewById(R.id.info);
        info.setText("Please Login First!");
        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotifyLogin();
            }
        });

        Button fetchEminem = findViewById(R.id.eminem);
        fetchEminem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
            }
        });
    }

    private void fetch() {
        if (TOKEN.equals("")) {
            info.setText("Please Signin First!");
            openDialog();
            return;
        }
                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderArtists artists = retrofit.create(JsonPlaceHolderArtists.class);
        Call<Artist> call = artists.getArtist("Bearer " + TOKEN);
        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                Log.d("code", "" + response.code());
                if (response.isSuccessful()) {
                    Log.d("passed", "onResponse: " + response.body().getFirstImageUrl());
                    Glide.with(getApplicationContext()).load(response.body().getFirstImageUrl()).into(icon);
                    name.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                Log.d("Error", "onFailure: " +  t.getMessage());
            }
        });


    }

    private void openDialog() {
        AlertDialog dialog = new AlertDialog.Builder(SpotifyActivity.this)
                .setTitle("Please Login first")
                .setMessage("Please Login to Spotify first before fetching data!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();

        dialog.show();
    }

    private void spotifyLogin() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, 1337, request);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == 1337) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            Log.d("result", "onActivityResult: ");
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    TOKEN = response.getAccessToken();
                    info.setText("Successfully Logged In!");
                    Log.d("good ", response.getAccessToken());
                    break;

                // Auth flow returned an error
                case ERROR:
                    info.setText("Unable to login to Spotify!");
                    Log.d("error ", response.getError());
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    info.setText("Unexpected Error logging in");
                    Log.d("default ", response.getType().toString());
                    // Handle other cases
            }
        }
    }


}