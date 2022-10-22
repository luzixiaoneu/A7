package com.example.spotify_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import entity.IP;
import entity.JsonPlaceHolderApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "78698dcc31fd4845919e96fc61514de8";
    private static final String REDIRECT_URI = "http://localhost:8888/";
    private SpotifyAppRemote mSpotifyAppRemote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        TextView ipInfo = findViewById(R.id.ip);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotifyLogin();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<IP> call = jsonPlaceHolderApi.getIp();
        call.enqueue(new Callback<IP>() {
            @Override
            public void onResponse(Call<IP> call, Response<IP> response) {
                if (response.isSuccessful()) {

                    ipInfo.setText(response.body().getIpAddress());
                }
                else ipInfo.setText("Error");
            }

            @Override
            public void onFailure(Call<IP> call, Throwable t) {
                ipInfo.setText(t.getMessage());
            }
        });
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
                    Log.d("good ", response.getAccessToken());
                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.d("error ", response.getError());
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.d("wtf ", response.getType().toString());
                    // Handle other cases
            }
        }
    }


}