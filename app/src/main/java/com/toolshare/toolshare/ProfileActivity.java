package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    mTextMessage.setText("Add");
                    PopupMenu popup = new PopupMenu(ProfileActivity.this, findViewById(R.id.navigation_add));
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.add_menu, popup.getMenu());
                    popup.show();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("Browse");
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tool_option:
                Intent i = new Intent(ProfileActivity.this, NewToolActivity.class);
                startActivity(i);
                return true;
            case R.id.new_ad_option:
                return true;
            default:
                return false;
        }
    }
}
