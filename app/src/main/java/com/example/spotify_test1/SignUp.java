package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.User;

public class SignUp extends AppCompatActivity {
    //private FirebaseDatabase database;
    private DatabaseReference ref;
    private Button register;
    private EditText editEmail;
    private EditText editPassword;
    private User user;
    private Toast toast;
    private FirebaseAuth mAuth;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //user = new User();
        mAuth = FirebaseAuth.getInstance();
        register = (Button)findViewById(R.id.register);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        editPassword.setText("password");
        editName = (EditText) findViewById(R.id.name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String name = editName.getText().toString();
                signUp(name, email, password);
            }
        });
    }

    private void signUp(String name, String email, String password){
        // Code snippet from firebase password authentication

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            addUserToDatabase(name, email, mAuth.getCurrentUser().getUid());
                            Log.d("Status ", "createUserWithEmail:success");
                            Intent intent = new Intent(SignUp.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserToDatabase(String name, String email, String uid){
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("user").child(uid).setValue(new User(name, email, uid));
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