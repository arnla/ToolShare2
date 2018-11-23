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
import static com.toolshare.toolshare.models.User.getUserNameByPk;


public class ViewAdFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Ad ad;
    private Tool tool;
    private Availability availability;
    private TextView mAdTitle;
    private TextView mAdOwner;
    private TextView mAdDescription;
    private TextView mAdStartDate;
    private TextView mAdEndDate;
    private TextView mToolName;
    private TextView mToolYear;
    private TextView mToolBrand;
    private TextView mToolModel;
    private Button mDeleteButton;
    private Button mEditButton;
    private Button mRentToolButton;
    private Button mMonday;
    private Button mTuesday;
    private Button mWednesday;
    private Button mThursday;
    private Button mFriday;
    private Button mSaturday;
    private Button mSunday;
    private TextView mPrice;

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
        mAdOwner = (TextView) view.findViewById(R.id.tv_ad_owner);
        mAdDescription = (TextView) view.findViewById(R.id.tv_ad_description);
        mAdStartDate = (TextView) view.findViewById(R.id.tv_ad_start_date);
        mAdEndDate = (TextView) view.findViewById(R.id.tv_ad_end_date);
        mToolName = (TextView) view.findViewById(R.id.tv_ad_tool_name);
        mToolYear = (TextView) view.findViewById(R.id.tv_ad_tool_year);
        mToolBrand = (TextView) view.findViewById(R.id.tv_ad_tool_brand);
        mToolModel = (TextView) view.findViewById(R.id.tv_ad_tool_model);
        mDeleteButton = (Button) view.findViewById(R.id.b_delete_ad);
        mEditButton = (Button) view.findViewById(R.id.b_edit_ad);
        mRentToolButton = (Button) view.findViewById(R.id.b_rent_tool);
        if (bundle.getString("userEmail").equals(ad.getOwner())) {
            mDeleteButton.setVisibility(View.VISIBLE);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAd();
                }
            });
            mEditButton.setVisibility(View.VISIBLE);
        } else {
            mRentToolButton.setVisibility(View.VISIBLE);
            mRentToolButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("tool", tool);
                    bundle.putSerializable("availability", availability);
                    Fragment fragment = new NewRentRequestFragment();
                    fragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
        mMonday = (Button) view.findViewById(R.id.b_ad_monday);
        mTuesday = (Button) view.findViewById(R.id.b_ad_tuesday);
        mWednesday = (Button) view.findViewById(R.id.b_ad_wednesday);
        mThursday = (Button) view.findViewById(R.id.b_ad_thursday);
        mFriday = (Button) view.findViewById(R.id.b_ad_friday);
        mSaturday = (Button) view.findViewById(R.id.b_ad_saturday);
        mSunday = (Button) view.findViewById(R.id.b_ad_sunday);
        mPrice = (TextView) view.findViewById(R.id.tv_ad_price);

        setAdValues();

        return view;
    }

    private void setAdValues() {
        mAdTitle.setText(ad.getTitle());
        mAdOwner.setText(mAdOwner.getText() + getUserNameByPk(db, ad.getOwner()));
        mToolName.setText(mToolName.getText() + tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());
        mAdDescription.setText(ad.getDescription());
        mPrice.setText(mPrice.getText() + "$" + Integer.toString(ad.getPrice()));
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

    private void deleteAd() {
        Ad.deleteAd(db, ad.getId());
        Toast.makeText(getActivity(), "Ad deleted", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
