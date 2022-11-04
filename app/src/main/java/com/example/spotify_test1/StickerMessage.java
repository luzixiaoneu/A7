package com.example.spotify_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StickerMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}