package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import entity.User;
public class FirebaseActivity extends AppCompatActivity {
    private EditText userName;
    //private User user;
    private DatabaseReference ref;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        userName = (EditText) findViewById(R.id.username);
        register = (Button) findViewById(R.id.register);
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://share-playlist-project-default-rtdb.firebaseio.com/User");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(FirebaseActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });
    }

    public void loginButtonClick(View view){
        String name = userName.getText().toString();
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        return;
                    }
                    for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (name.equals(user.getUserName())) {
                            Intent start = new Intent(FirebaseActivity.this, MainActivity.class);
                            startActivity(start);
                            Toast.makeText(getApplicationContext(), "User successfully logged in!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext()
                            , "Failed to write value into firebase. " , Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "User doesn't exist!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}