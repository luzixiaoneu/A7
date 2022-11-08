package com.example.spotify_test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class MessageActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

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

        imageView1.setImageResource(R.drawable.cat);
        imageView1.setTag(R.drawable.cat);
        imageView2.setImageResource(R.drawable.dog);
        imageView2.setTag(R.drawable.dog);
        imageView3.setImageResource(R.drawable.parrot);
        imageView3.setTag(R.drawable.parrot);

        ref = FirebaseDatabase.getInstance().getReference();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ref.child("images").push().setValue(getDrawableId(imageView1));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Uri uri = Uri.parse("android.resource://spotify_test1/drawable/image_name");
                InputStream stream = getContentResolver().openInputStream(uri);
                ref.child("images").push().setValue(getDrawableId(imageView2));
                //UploadTask uploadTask;*/
            }
        });
    }

    public int getDrawableId(ImageView iv) {
        /*int i = (Integer) iv.getTag();
        String s=String.valueOf(i);
        return s;*/

        return (Integer) iv.getTag();
    }

    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(View view) {

        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, ReceiveNotificationActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);


        // Build notification
        // Need to define a channel ID after Android Oreo
        String channelId = getString(R.string.channel_id);
        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
                //"Notification icons must be entirely white."
                .setSmallIcon(R.drawable.cat)
                .setContentTitle("New mail from " + "test@test.com")
                .setContentText("Subject")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // hide the notification after its selected
                .setAutoCancel(true)
                .addAction(R.drawable.cat, "Call", pIntent)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, notifyBuild.build());

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}