package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.ToolReview;
import com.toolshare.toolshare.models.ToolType;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        if (bundle.containsKey("toolTypesToShow")) {
            toolTypesToShow = bundle.getIntegerArrayList("toolTypesToShow");
        }

        mAdsLeft = (LinearLayout) view.findViewById(R.id.ll_dash_ads_left);
        mAdsRight = (LinearLayout) view.findViewById(R.id.ll_dash_ads_right);
        mFilterButton = (TextView) view.findViewById(R.id.tv_filter);

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View reviewView = li.inflate(R.layout.dialog_filter, null);

                // get all tool types and add to filter
                final LinearLayout llToolTypes = (LinearLayout) reviewView.findViewById(R.id.ll_tool_types);
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
                alertDialogBuilder.setView(reviewView);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Apply",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        ArrayList<Integer> toolTypesToShow = new ArrayList<Integer>();
                                        CheckBox checkbox;
                                        for (int i = 0; i < llToolTypes.getChildCount(); i++) {
                                            checkbox = (CheckBox) llToolTypes.getChildAt(i);
                                            if (checkbox.isChecked()) {
                                                toolTypesToShow.add(checkbox.getId());
                                            }
                                        }

                                        bundle.putIntegerArrayList("toolTypesToShow", toolTypesToShow);
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

    private void loadAds() {
        List<Ad> ads = getAllAdsThatAreNotOwners(db, bundle.getString("userEmail"));
        for (int i = 0; i < ads.size(); i++) {
            final Ad ad = ads.get(i);
            if (toolTypesToShow == null) {
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
            } else {
                if (toolTypesToShow.contains(ad.getTool().getTypeId())) {
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
    }
}
