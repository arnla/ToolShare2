package com.toolshare.toolshare;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Card;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolAddress;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Tool.getToolByPk;
import static com.toolshare.toolshare.models.User.getUser;
import static com.toolshare.toolshare.models.User.getUserNameByPk;


public class ViewAdFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Ad ad;
    private Tool tool;
    private User user;
    private Availability availability;
    private TextView mAdTitle;
    private TextView mAdOwner;
    private TextView mAdDescription;
    private TextView mAdStartDate;
    private TextView mAdEndDate;
    private CardView mToolLink;
    private TextView mToolName;
    private ImageView mToolImage;
    private Button mDeleteButton;
    private Button mRentToolButton;
    private Button mMonday;
    private Button mTuesday;
    private Button mWednesday;
    private Button mThursday;
    private Button mFriday;
    private Button mSaturday;
    private Button mSunday;
    private TextView mPrice;
    private TextView mLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_ad, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) getToolByPk(db, ad.getToolId());
        availability = (Availability) getAvailabilityByAdId(db, ad.getId());
        user = (User) getUser(db, tool.getOwner());

        mAdTitle = (TextView) view.findViewById(R.id.tv_ad_title);
        mAdOwner = (TextView) view.findViewById(R.id.tv_ad_owner);
        mAdOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewProfileFragment();
                fragment.setArguments(bundle);
                bundle.putSerializable("user", user);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mAdDescription = (TextView) view.findViewById(R.id.tv_ad_description);
        mAdStartDate = (TextView) view.findViewById(R.id.tv_ad_start_date);
        mAdEndDate = (TextView) view.findViewById(R.id.tv_ad_end_date);
        mToolLink = (CardView) view.findViewById(R.id.ll_tool).findViewById(R.id.cv_tool);
        mToolName = (TextView) mToolLink.findViewById(R.id.tv_name);
        mToolImage = (ImageView) mToolLink.findViewById(R.id.iv_tool_pic);
        mDeleteButton = (Button) view.findViewById(R.id.b_delete_ad);
        mRentToolButton = (Button) view.findViewById(R.id.b_rent_tool);
        if (bundle.getString("userEmail").equals(ad.getOwner())) {
            mDeleteButton.setVisibility(View.VISIBLE);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAd();
                }
            });
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
        mLocation = (TextView) view.findViewById(R.id.tv_location);

        mToolLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("tool", tool);
                Fragment fragment = new ViewToolFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        setAdValues();

        return view;
    }

    private void setAdValues() {
        mAdTitle.setText(ad.getTitle());
        mAdOwner.setText(mAdOwner.getText() + getUserNameByPk(db, ad.getOwner()));
        mAdOwner.setTextColor(Color.BLUE);
        mToolName.setText(tool.getName());
        mToolImage.setImageBitmap(tool.getPicture());
        mAdDescription.setText(ad.getDescription());
        DecimalFormat df = new DecimalFormat("#.00");
        mPrice.setText("$" + df.format(ad.getPrice()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        mAdStartDate.setText(formatter.format(ad.getAvailability().getStartDate()));
        mAdEndDate.setText(formatter.format(ad.getAvailability().getEndDate()));
        setAvailabilityDay(mMonday, availability.isAvailableMonday());
        setAvailabilityDay(mTuesday, availability.isAvailableTuesday());
        setAvailabilityDay(mWednesday, availability.isAvailableWednesday());
        setAvailabilityDay(mThursday, availability.isAvailableThursday());
        setAvailabilityDay(mFriday, availability.isAvailableFriday());
        setAvailabilityDay(mSaturday, availability.isAvailableSaturday());
        setAvailabilityDay(mSunday, availability.isAvailableSunday());
        ToolAddress toolAddress = ToolAddress.getToolAddressByToolId(db, tool.getId());
        mLocation.setText(toolAddress.getZipCode());
    }

    private void setAvailabilityDay(Button btn, boolean available) {
        if (available) {
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn.setTextColor(Color.WHITE);
        }
    }

    private void deleteAd() {
/*        Ad.deleteAd(db, ad.getId());
        Toast.makeText(getActivity(), "Ad deleted", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();*/
    }
}
