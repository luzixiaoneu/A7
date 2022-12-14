package com.example.spotify_test1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class MessageActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private TextView historyTV;
    private Button history;
    private int catStickerCount = 0;
    private int dogStickerCount = 0;
    private int parrotStickerCount = 0;
    private int turtleStickerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activity);
        String name = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("uid");
        createNotificationChannel();

        getSupportActionBar().setTitle(name);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        history = (Button) findViewById(R.id.history_button);
        historyTV = (TextView) findViewById(R.id.history_textview);

        imageView1.setImageResource(R.drawable.cat);
        imageView1.setTag(R.drawable.cat);
        imageView2.setImageResource(R.drawable.dog);
        imageView2.setTag(R.drawable.dog);
        imageView3.setImageResource(R.drawable.parrot);
        imageView4.setTag(R.drawable.parrot);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("user").child(uid).child("img1").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                // ...
                                Toast.makeText(getApplicationContext(), "Sending Cat Sticker!", Toast.LENGTH_SHORT).show();
                                catStickerCount++;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });


            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("user").child(uid).child("img2").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                // ...
                                Toast.makeText(getApplicationContext(), "Sending Dog Sticker!", Toast.LENGTH_SHORT).show();
                                dogStickerCount++;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("user").child(uid).child("img3").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                // ...
                                Toast.makeText(getApplicationContext(), "Sending Parrot Sticker!", Toast.LENGTH_SHORT).show();
                                parrotStickerCount++;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("user").child(uid).child("img4").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                // ...
                                Toast.makeText(getApplicationContext(), "Sending Turtle Sticker!", Toast.LENGTH_SHORT).show();
                                turtleStickerCount++;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyTV.setText("Cat stickers received: " + catStickerCount + "\n"
                        + "Dog stickers received: " + dogStickerCount + "\n"
                        + "Parrot stickers received: " + parrotStickerCount + "\n"
                        + "Turtle stickers received: " + turtleStickerCount);
            }
        });

        mDatabase.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intent intent = new Intent(MessageActivity.this, StickerActivity.class);

                PendingIntent pIntent = PendingIntent.getActivity(MessageActivity.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);


                String channelId = getString(R.string.channel_id);
                NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(MessageActivity.this, channelId)
                        //"Notification icons must be entirely white."
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("You have received a notification")
                        .setContentText("An Image has been sent to you")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        // hide the notification after its selected
                        .setAutoCancel(true)
                        .addAction(R.drawable.notification, "Call", pIntent)
                        .setContentIntent(pIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MessageActivity.this);
                // // notificationId is a unique int for each notification that you must define
                notificationManager.notify(0, notifyBuild.build());

                //Toast.makeText(getApplicationContext(), "Change", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}