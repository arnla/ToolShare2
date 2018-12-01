package com.toolshare.toolshare;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;

import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAllAdsThatAreNotOwners;

public class DashboardFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private LinearLayout mAdsLeft;
    private LinearLayout mAdsRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");

        mAdsLeft = (LinearLayout) view.findViewById(R.id.ll_dash_ads_left);
        mAdsRight = (LinearLayout) view.findViewById(R.id.ll_dash_ads_right);

        loadAds();

        return view;
    }

    private void loadAds() {
        List<Ad> ads = getAllAdsThatAreNotOwners(db, bundle.getString("userEmail"));
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
            textView.setGravity(Gravity.CENTER);
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

/*            Button button = new Button(getActivity().getApplicationContext());
            button.setHeight(500);
            button.setText(ads.get(i).getTitle());
            button.setOnClickListener(new View.OnClickListener(){

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
            });*/
            if ((i % 2) == 0) {
                mAdsLeft.addView(linearLayout);
            } else {
                mAdsRight.addView(linearLayout);
            }
        }
    }
}
