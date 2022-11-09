package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entity.User;

public class StickerActivity extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    Button userView;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String email = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_sticker);
        getSupportActionBar().setTitle(email);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        userView = findViewById(R.id.userView);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StickerActivity.this, UserViewActivity.class);
                startActivity(intent);
            }
        });
        ref = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        // display other users of the app in recyclerview when current user logs in
        ref.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(), "Change!!!!!", Toast.LENGTH_SHORT);
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    User currentUser = userSnapshot.getValue(User.class);
                    if(currentUser.getUid().equals(mAuth.getCurrentUser().getUid())){
                        if (!currentUser.isImg1()) {
                            img1.setVisibility(View.INVISIBLE);
                        }
                        else img1.setVisibility(View.VISIBLE);
                        if (!currentUser.isImg2()) {
                            img2.setVisibility(View.INVISIBLE);
                        }
                        else img2.setVisibility(View.VISIBLE);
                        if (!currentUser.isImg3()) {
                            img3.setVisibility(View.INVISIBLE);
                        }
                        else img3.setVisibility(View.VISIBLE);
                        if (!currentUser.isImg4()) {
                            img4.setVisibility(View.INVISIBLE);
                        }
                        else img4.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}