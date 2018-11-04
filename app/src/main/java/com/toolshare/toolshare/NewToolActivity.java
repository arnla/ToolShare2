package com.toolshare.toolshare;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.ToolType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewToolActivity extends AppCompatActivity {

    private DbHandler db;
    private TextView mName;
    private Spinner mToolTypeSpinner;
    private Spinner mYearsSpinner;
    private Spinner mBrandSpinner;
    private TextView mModel;

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
        mToolTypeSpinner = (Spinner) findViewById(R.id.s_tool_type);
        mYearsSpinner = (Spinner) findViewById(R.id.s_tool_year);
        mBrandSpinner = (Spinner) findViewById(R.id.s_tool_brand);
        mModel = (EditText) findViewById(R.id.et_tool_model);
        loadSpinners();
    }

    private void loadSpinners() {
        // Tool type spinner
        List<ToolType> toolTypes = ToolType.getAllToolTypes(db);
        ArrayAdapter<ToolType> dataAdapter = new ArrayAdapter<ToolType>(this,
                android.R.layout.simple_spinner_item, toolTypes);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mToolTypeSpinner.setAdapter(dataAdapter);

        // Year spinner
        List<Integer> years = new ArrayList<Integer>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1975; i <= currentYear; i++) {
            years.add(i);
        }
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearsSpinner.setAdapter(yearsAdapter);

        // Brands spinner
        List<Brand> brands = Brand.getAllBrands(db);
        ArrayAdapter<Brand> brandsAdapter = new ArrayAdapter<Brand>(this,
                android.R.layout.simple_spinner_item, brands);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBrandSpinner.setAdapter(brandsAdapter);
    }
}
