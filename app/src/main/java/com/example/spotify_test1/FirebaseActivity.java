package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        userName = (EditText) findViewById(R.id.username);
        register = (Button) findViewById(R.id.register);
        ref = FirebaseDatabase.getInstance().getReference().child("User");
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

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        return;
                    }
                    for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (userName.getText().toString().isEmpty()){
                            showAToast("Please fill in username field");
                        }
                        else if (!name.equals(user.getUserName())) {
                            //Toast.makeText(FirebaseActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                            Log.d("User status ", "User doesn't exist");
                        } else {
                            Intent start = new Intent(FirebaseActivity.this, StickerMessage.class);
                            startActivity(start);
                            Log.d("User status ", "Successfully logged in!");
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

    public void showAToast(String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
