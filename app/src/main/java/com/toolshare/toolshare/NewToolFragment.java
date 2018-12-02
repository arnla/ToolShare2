package com.toolshare.toolshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolAddress;
import com.toolshare.toolshare.models.ToolType;
import com.toolshare.toolshare.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.toolshare.toolshare.models.ToolAddress.addToolAddress;
import static com.toolshare.toolshare.models.User.getUser;


public class NewToolFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private EditText mName;
    private Spinner mToolTypeSpinner;
    private Spinner mYearsSpinner;
    private Spinner mBrandSpinner;
    private EditText mModel;
    private Button mCreateToolButton;
    private Button mAddImage;
    private ImageView mImage;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap image;
    private LinearLayout mAddressLayout;
    private EditText mStreetAddress;
    private EditText mCity;
    private EditText mProvince;
    private EditText mZipCode;
    private EditText mCountry;
    private CheckBox mSameAddress;

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
        mCreateToolButton = (Button) view.findViewById(R.id.b_create_tool);
        mImage = (ImageView) view.findViewById(R.id.iv_image);
        mStreetAddress = (EditText) view.findViewById(R.id.street_address);
        mCity = (EditText) view.findViewById(R.id.city);
        mProvince = (EditText) view.findViewById(R.id.province);
        mZipCode = (EditText) view.findViewById(R.id.zip_code);
        mCountry = (EditText) view.findViewById(R.id.country);
        mSameAddress = (CheckBox) view.findViewById(R.id.cb_same_address);
        mAddressLayout = (LinearLayout) view.findViewById(R.id.ll_address);

        mCreateToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTool();
            }
        });

        Bitmap addImageIcon = BitmapFactory.decodeResource(getResources(), R.drawable.add_tool_image);
        mImage.setImageBitmap(addImageIcon);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        mAddressLayout.setVisibility(View.GONE);
        mSameAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSameAddress.isChecked()) {
                    mAddressLayout.setVisibility(View.GONE);
                } else {
                    mAddressLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        loadSpinners();
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image = photo;
            mImage.setImageBitmap(photo);
        }
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

        if (image == null) {
            Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
            image = photo;
        }
        Tool tool = new Tool(owner, toolType.getId(), brand.getId(), name, year, model, image);
        int id = tool.addTool(db);

        User user = getUser(db, owner);
        ToolAddress toolAddress;
        if (mSameAddress.isChecked()) {
            toolAddress = new ToolAddress(id, user.getStreetAddress(), user.getCity(), user.getProvince(), user.getZipCode(), user.getCountry());
        } else {
            toolAddress = new ToolAddress(id, mStreetAddress.getText().toString(), mCity.getText().toString(), mProvince.getText().toString(),
                    mZipCode.getText().toString(), mCountry.getText().toString());
        }
        addToolAddress(db, toolAddress);

        Toast.makeText(getActivity(), "New tool added", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
