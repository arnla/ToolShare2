package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolAddress;
import com.toolshare.toolshare.models.ToolReview;
import com.toolshare.toolshare.models.ToolType;
import com.toolshare.toolshare.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAllAdsThatAreNotOwners;
import static com.toolshare.toolshare.models.Tool.updateTool;
import static com.toolshare.toolshare.models.ToolReview.addToolReview;
import static com.toolshare.toolshare.models.ToolReview.getAllRatingsByToolId;
import static com.toolshare.toolshare.models.ToolType.getAllToolTypes;

public class DashboardFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private LinearLayout mAdsLeft;
    private LinearLayout mAdsRight;
    private TextView mFilterButton;
    private List<Integer> toolTypesToShow;
    private int maxDistance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        if (bundle.containsKey("toolTypesToShow")) {
            toolTypesToShow = bundle.getIntegerArrayList("toolTypesToShow");
        }
        if (bundle.containsKey("maxDistanceKm")) {
            maxDistance = bundle.getInt("maxDistanceKm");
        } else {
            maxDistance = -1;
        }

        mAdsLeft = (LinearLayout) view.findViewById(R.id.ll_dash_ads_left);
        mAdsRight = (LinearLayout) view.findViewById(R.id.ll_dash_ads_right);
        mFilterButton = (TextView) view.findViewById(R.id.tv_filter);

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View filterView = li.inflate(R.layout.dialog_filter, null);

                // get all tool types and add to filter
                final LinearLayout llToolTypes = (LinearLayout) filterView.findViewById(R.id.ll_tool_types);
                final RadioGroup mDistance = (RadioGroup) filterView.findViewById(R.id.rg_distance);
                List<ToolType> toolTypes = getAllToolTypes(db);
                for (int i = 0; i < toolTypes.size(); i++) {
                    CheckBox toolType = new CheckBox(getActivity().getApplicationContext());
                    toolType.setId(toolTypes.get(i).getId());
                    toolType.setText(toolTypes.get(i).getType());
                    llToolTypes.addView(toolType);
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(filterView);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Apply",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        ArrayList<Integer> toolTypesToShow = new ArrayList<Integer>();
                                        CheckBox checkbox;
                                        for (int i = 1; i < llToolTypes.getChildCount(); i++) {
                                            checkbox = (CheckBox) llToolTypes.getChildAt(i);
                                            if (checkbox.isChecked()) {
                                                toolTypesToShow.add(checkbox.getId());
                                            }
                                        }

                                        int maxDistanceKm = 0;
                                        switch (mDistance.getCheckedRadioButtonId()) {
                                            case R.id.rb_any:
                                                maxDistanceKm = -1;
                                                break;
                                            case R.id.rb_10:
                                                maxDistanceKm = 10;
                                                break;
                                            case R.id.rb_25:
                                                maxDistanceKm = 25;
                                                break;
                                            case R.id.rb_50:
                                                maxDistanceKm = 50;
                                                break;
                                        }

                                        bundle.putIntegerArrayList("toolTypesToShow", toolTypesToShow);
                                        bundle.putInt("maxDistanceKm", maxDistanceKm);
                                        Fragment fragment = new DashboardFragment();
                                        fragment.setArguments(bundle);

                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        transaction.replace(R.id.fragment_container, fragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        loadAds();

        return view;
    }

    private List<Ad> loadAdsByToolTypes(List<Ad> ads) {
        if (toolTypesToShow == null) {
            return ads;
        } else {
            List<Ad> newAds = new ArrayList<Ad>();
            Ad ad;
            for (int i = 0; i < ads.size(); i++) {
                ad = ads.get(i);
                if (toolTypesToShow.contains(ad.getTool().getTypeId())) {
                    newAds.add(ad);
                }
            }
            return newAds;
        }
    }

    private List<Ad> loadAdsByLocation(List<Ad> ads) {
        List<Ad> newAds = new ArrayList<Ad>();
        Geocoder coder = new Geocoder(getActivity().getApplicationContext());
        Location userLocation = new Location("User Location");
        List<Address> address;
        User user = User.getUser(db, bundle.getString("userEmail"));
        String userAddressString = user.getStreetAddress() + " " + user.getCity() + " " + user.getProvince() + " " + user.getZipCode() + " " + user.getCountry();
        try {
            address = coder.getFromLocationName(userAddressString, 1);
            if (address == null) {
                return ads;
            }
            Address userAddress = address.get(0);
            userLocation.setLatitude(userAddress.getLatitude());
            userLocation.setLongitude(userAddress.getLongitude());
        } catch (Exception e) {}

        Ad ad;
        Tool tool;
        ToolAddress toolAddresses;
        Location toolLocation = new Location("Tool Location");
        float distance;
        for (int i = 0; i < ads.size(); i++) {
            ad = ads.get(i);
            tool = ad.getTool();
            toolAddresses = ToolAddress.getToolAddressByToolId(db, tool.getId());
            String toolAddressString = toolAddresses.getStreetAddress() + " " + toolAddresses.getCity() + " " + toolAddresses.getProvince() + " "
                    + toolAddresses.getZipCode() + " " + toolAddresses.getCountry();
            try {
                address = coder.getFromLocationName(toolAddressString, 1);
                if (address == null) {
                    newAds.add(ad);
                } else {
                    Address toolAddress = address.get(0);
                    toolLocation.setLatitude(toolAddress.getLatitude());
                    toolLocation.setLongitude(toolAddress.getLongitude());

                    distance = userLocation.distanceTo(toolLocation);
                    if ((distance / 1000) <= maxDistance) {
                        newAds.add(ad);
                    }
                }
            } catch (Exception e) {}
        }

        return newAds;
    }

    private void loadAds() {
        List<Ad> ads = getAllAdsThatAreNotOwners(db, bundle.getString("userEmail"));
        if ((toolTypesToShow != null) && (toolTypesToShow.size() != 0)) {
            ads = loadAdsByToolTypes(ads);
        }
        if (maxDistance != -1) {
            ads = loadAdsByLocation(ads);
        }
        for (int i = 0; i < ads.size(); i++) {
            final Ad ad = ads.get(i);
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500);
            linearLayoutParams.setMargins(0,10,0,10);
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 350);
            imageViewParams.setMargins(5,20,5,0);

            LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
            linearLayout.setLayoutParams(linearLayoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(Color.GRAY);

            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            imageView.setImageBitmap(ad.getTool().getPicture());
            imageView.setLayoutParams(imageViewParams);

            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setText(ad.getTitle());
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setLayoutParams(textViewParams);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            linearLayout.setClickable(true);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("ad", ad);
                    Fragment fragment = new ViewAdFragment();
                    fragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            if ((i % 2) == 0) {
                mAdsLeft.addView(linearLayout);
            } else {
                mAdsRight.addView(linearLayout);
            }
        }
    }
}
