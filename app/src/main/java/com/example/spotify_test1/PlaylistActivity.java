package com.example.spotify_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.spotify.android.appremote.api.error.SpotifyConnectionTerminatedException;

public class PlaylistActivity extends AppCompatActivity {
    private String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Intent intent = getIntent();
        this.TOKEN = intent.getStringExtra("TOKEN_STRING");
        Toast.makeText(getApplicationContext(), TOKEN, Toast.LENGTH_LONG).show();
    }
}