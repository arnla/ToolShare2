package com.toolshare.toolshare;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.ToolType;

import java.util.List;

public class NewToolActivity extends AppCompatActivity {

    private DbHandler db;
    private Spinner mToolTypeSpinner;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_add:
                    PopupMenu popup = new PopupMenu(NewToolActivity.this, findViewById(R.id.navigation_add));
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.add_menu, popup.getMenu());
                    popup.show();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tool);

        db = new DbHandler(this);
        mToolTypeSpinner = (Spinner) findViewById(R.id.spinner_tool_type);
        loadSpinner();
    }

    private void loadSpinner() {
        // Spinner Drop down elements
        ToolType toolType = new ToolType();
        List<String> toolTypes = toolType.getAllToolTypes(db);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, toolTypes);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mToolTypeSpinner.setAdapter(dataAdapter);
    }
}
