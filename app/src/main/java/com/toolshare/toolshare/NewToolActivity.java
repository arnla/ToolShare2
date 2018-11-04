package com.toolshare.toolshare;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewToolActivity extends AppCompatActivity {

    private DbHandler db;
    private Bundle bundle;
    private EditText mName;
    private Spinner mToolTypeSpinner;
    private Spinner mYearsSpinner;
    private Spinner mBrandSpinner;
    private EditText mModel;
    private Button mCreateToolButton;

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
        bundle = getIntent().getExtras();
        mName = (EditText) findViewById(R.id.et_tool_name);
        mToolTypeSpinner = (Spinner) findViewById(R.id.s_tool_type);
        mYearsSpinner = (Spinner) findViewById(R.id.s_tool_year);
        mBrandSpinner = (Spinner) findViewById(R.id.s_tool_brand);
        mModel = (EditText) findViewById(R.id.et_tool_model);
        loadSpinners();
        mCreateToolButton = (Button) findViewById(R.id.b_create_tool);
        mCreateToolButton.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View view) {
                insertTool();
            }
        });
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

    private void insertTool() {
        String owner = bundle.getString("userEmail");
        ToolType toolType = (ToolType) mToolTypeSpinner.getSelectedItem();
        String name = mName.getText().toString();
        int year = (int) mYearsSpinner.getSelectedItem();
        String model = mModel.getText().toString();
        Brand brand = (Brand) mBrandSpinner.getSelectedItem();

        Tool tool = new Tool(db, owner, toolType.getId(), name, year, model, brand.getId());
        tool.addTool(db);
        Toast.makeText(NewToolActivity.this, "New tool added", Toast.LENGTH_LONG).show();
    }
}
