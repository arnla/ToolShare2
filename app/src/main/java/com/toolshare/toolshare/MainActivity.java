package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

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

        maboutus = findViewById(R.id.aboutus);

        mshareapp = findViewById(R.id.shareapp);
    }

}
