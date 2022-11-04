package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entity.User;

public class SignUp extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Button register;
    private EditText userName;
    private User user;
    private Toast toast;

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

                if (userName.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill in username field", Toast.LENGTH_SHORT).show();
                }else {

                    user.setUserName(userName.getText().toString());
                    ref.child(user.getUserName()).setValue(user);
                    showAToast("User successfully created");
                    finish();

                }
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