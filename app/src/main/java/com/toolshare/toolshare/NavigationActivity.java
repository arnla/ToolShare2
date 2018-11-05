package com.toolshare.toolshare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.toolshare.toolshare.db.DbHandler;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener  {

    private Bundle bundle;
    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bundle = getIntent().getExtras();
        db = new DbHandler(this);

        //loading the default fragment
        loadFragment(new ProfileFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new ProfileFragment();
                break;

            case R.id.navigation_add:
                PopupMenu popup = new PopupMenu(NavigationActivity.this, findViewById(R.id.navigation_add));
                popup.setOnMenuItemClickListener(NavigationActivity.this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.add_menu, popup.getMenu());
                popup.show();
                break;

            case R.id.navigation_dashboard:
                break;

            case R.id.navigation_notifications:
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.new_tool_option:
/*                Intent i = new Intent(NavigationActivity.this, NewToolActivity.class);
                i.putExtra("userEmail", bundle.getString("userEmail"));
                startActivity(i);*/
                fragment = new NewToolFragment();
                return loadFragment(fragment);
            case R.id.new_ad_option:
                return true;
            default:
                return false;
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
