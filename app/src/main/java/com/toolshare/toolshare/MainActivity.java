package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Notification;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;

    private ImageButton mProfileButton;
    private ImageButton mRentToolsButton;
    private ImageButton maboutus;
    private ImageButton mRentOutToolsButton;
    private ImageButton mshareapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = getIntent().getExtras();
        String userEmail = bundle.getString("userEmail");
        DbHandler db = (DbHandler) bundle.getSerializable("db");

        mProfileButton = findViewById(R.id.profile_button);
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                i.putExtra("fragmentName", "profile");
                startActivity(i);
            }
        });
        mRentToolsButton = findViewById(R.id.rent_tools_button);
        mRentToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                i.putExtra("fragmentName", "dashboard");
                startActivity(i);
            }
        });
        mRentOutToolsButton = findViewById(R.id.rent_out_tools_button);
        mRentOutToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                i.putExtra("fragmentName", "add");
                startActivity(i);
            }
        });

        maboutus = findViewById(R.id.shareapp);
        maboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                i.putExtra("fragmentName", "shareapp");
                startActivity(i);
            }
        });

        mshareapp = findViewById(R.id.aboutus);
        mshareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                i.putExtra("fragmentName", "aboutus");
                startActivity(i);
            }
        });

        List<Notification> notificationList = Notification.getAllNotificationsByOwner(new DbHandler(this), bundle.getString("userEmail"));
        if (notificationList.size() > 0) {
            for (int i = 0; i < notificationList.size(); i++) {
                if (notificationList.get(i).getViewingStatus() == 0) {
                    Toast.makeText(MainActivity.this, "New notifications!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
