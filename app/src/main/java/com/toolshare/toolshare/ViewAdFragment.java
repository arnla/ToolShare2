package com.toolshare.toolshare;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;

import org.w3c.dom.Text;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Tool.getToolByPk;


public class ViewAdFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Ad ad;
    private Tool tool;
    private Availability availability;
    private TextView mAdTitle;
    private TextView mAdDescription;
    private TextView mAdStartDate;
    private TextView mAdEndDate;
    private TextView mToolName;
    private TextView mToolYear;
    private TextView mToolBrand;
    private TextView mToolModel;
    private Button mDeleteButton;
    private Button mEditButton;
    private Button mMonday;
    private Button mTuesday;
    private Button mWednesday;
    private Button mThursday;
    private Button mFriday;
    private Button mSaturday;
    private Button mSunday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_ad, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) getToolByPk(db, ad.getToolId());
        availability = (Availability) getAvailabilityByAdId(db, ad.getId());

        mAdTitle = (TextView) view.findViewById(R.id.tv_ad_title);
        mAdDescription = (TextView) view.findViewById(R.id.tv_ad_description);
        mAdStartDate = (TextView) view.findViewById(R.id.tv_ad_start_date);
        mAdEndDate = (TextView) view.findViewById(R.id.tv_ad_end_date);
        mToolName = (TextView) view.findViewById(R.id.tv_ad_tool_name);
        mToolYear = (TextView) view.findViewById(R.id.tv_ad_tool_year);
        mToolBrand = (TextView) view.findViewById(R.id.tv_ad_tool_brand);
        mToolModel = (TextView) view.findViewById(R.id.tv_ad_tool_model);
        mDeleteButton = (Button) view.findViewById(R.id.b_delete_ad);
        mEditButton = (Button) view.findViewById(R.id.b_edit_ad);
        mMonday = (Button) view.findViewById(R.id.b_ad_monday);
        mTuesday = (Button) view.findViewById(R.id.b_ad_tuesday);
        mWednesday = (Button) view.findViewById(R.id.b_ad_wednesday);
        mThursday = (Button) view.findViewById(R.id.b_ad_thursday);
        mFriday = (Button) view.findViewById(R.id.b_ad_friday);
        mSaturday = (Button) view.findViewById(R.id.b_ad_saturday);
        mSunday = (Button) view.findViewById(R.id.b_ad_sunday);

        setAdValues();

        return view;
    }

    private void setAdValues() {
        mAdTitle.setText(ad.getTitle());
        mToolName.setText(tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());
        mAdDescription.setText(ad.getDescription());
        mAdStartDate.setText(availability.getStartDate().toString());
        mAdEndDate.setText(availability.getEndDate().toString());
        setAvailabilityDay(mMonday, availability.isAvailableMonday());
        setAvailabilityDay(mTuesday, availability.isAvailableTuesday());
        setAvailabilityDay(mWednesday, availability.isAvailableWednesday());
        setAvailabilityDay(mThursday, availability.isAvailableThursday());
        setAvailabilityDay(mFriday, availability.isAvailableFriday());
        setAvailabilityDay(mSaturday, availability.isAvailableSaturday());
        setAvailabilityDay(mSunday, availability.isAvailableSunday());
    }

    private void setAvailabilityDay(Button btn, boolean available) {
        if (available) {
            btn.setBackgroundColor(Color.BLACK);
            btn.setTextColor(Color.WHITE);
        }
    }
}
