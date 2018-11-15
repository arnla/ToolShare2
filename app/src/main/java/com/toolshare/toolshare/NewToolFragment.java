package com.toolshare.toolshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewToolFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private EditText mName;
    private Spinner mToolTypeSpinner;
    private Spinner mYearsSpinner;
    private Spinner mBrandSpinner;
    private EditText mModel;
    private Button mCreateToolButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_tool, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");

        mName = (EditText) view.findViewById(R.id.et_tool_name);
        mToolTypeSpinner = (Spinner) view.findViewById(R.id.s_tool_type);
        mYearsSpinner = (Spinner) view.findViewById(R.id.s_tool_year);
        mBrandSpinner = (Spinner) view.findViewById(R.id.s_tool_brand);
        mModel = (EditText) view.findViewById(R.id.et_tool_model);
        loadSpinners();
        mCreateToolButton = (Button) view.findViewById(R.id.b_create_tool);
        mCreateToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTool();
            }
        });

        return view;
    }

    private void loadSpinners() {
        // Tool type spinner
        List<ToolType> toolTypes = ToolType.getAllToolTypes(db);
        ArrayAdapter<ToolType> dataAdapter = new ArrayAdapter<ToolType>(getActivity(),
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
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearsSpinner.setAdapter(yearsAdapter);

        // Brands spinner
        List<Brand> brands = Brand.getAllBrands(db);
        ArrayAdapter<Brand> brandsAdapter = new ArrayAdapter<Brand>(getActivity(),
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

        Tool tool = new Tool(owner, toolType.getId(), brand.getId(), name, year, model);
        tool.addTool(db);
        Toast.makeText(getActivity(), "New tool added", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
