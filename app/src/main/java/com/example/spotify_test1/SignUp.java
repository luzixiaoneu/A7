package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import entity.User;

public class SignUp extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Button register;
    private EditText userName;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user = new User();
        database = FirebaseDatabase.getInstance("https://share-playlist-project-default-rtdb.firebaseio.com/");
        ref = database.getReference().child("User");
        register = (Button)findViewById(R.id.register);
        userName = (EditText)findViewById(R.id.username);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(userName.getText().toString());
                ref.child(user.getUserName()).setValue(user);/*.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Failed to create user", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}