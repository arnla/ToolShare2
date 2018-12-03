package com.toolshare.toolshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.toolshare.toolshare.db.DbHandler;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener/*, PopupMenu.OnMenuItemClickListener*/  {

    private Bundle bundle;
    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bundle = getIntent().getExtras();
        db = new DbHandler(this);

        if (bundle.getString("fragmentName").equals("profile")) {
            loadFragment(new ProfileFragment());
        } else if (bundle.getString("fragmentName").equals("dashboard")) {
            loadFragment(new DashboardFragment());
        }else if (bundle.getString("fragmentName").equals("shareapp")) {
            loadFragment(new ShareFragment());
        }else if (bundle.getString("fragmentName").equals("aboutus")) {
            loadFragment(new AboutUsFragment());
        }

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
/*                PopupMenu popup = new PopupMenu(NavigationActivity.this, findViewById(R.id.navigation_add));
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.add_menu, popup.getMenu());
                popup.show();*/
                fragment = new AddToolOrAdFragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    private boolean loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("userEmail", this.bundle.getString("userEmail"));
        bundle.putSerializable("db", db);
        fragment.setArguments(bundle);
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }
}
