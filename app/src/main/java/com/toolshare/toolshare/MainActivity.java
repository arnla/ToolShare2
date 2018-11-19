package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;

    private Button mProfileButton;
    private Button mRentToolsButton;
    private Button mRentOutToolsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = getIntent().getExtras();

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
    }

}
