package com.toolshare.toolshare;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Tool;

import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAllAdsByOwner;
import static com.toolshare.toolshare.models.Tool.getAllToolsByOwner;


public class AdsListFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private LinearLayout mAdsLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ads_list, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");

        mAdsLayout = (LinearLayout) view.findViewById(R.id.ll_ads);

        loadAds();

        return view;
    }

    private void loadAds() {
        List<Ad> ads = getAllAdsByOwner(db, bundle.getString("userEmail"));

        for (int i = 0; i < ads.size(); i++) {
            final Ad ad = ads.get(i);
            LayoutInflater li = LayoutInflater.from(getActivity());
            View adView = li.inflate(R.layout.layout_ad, null);

            ImageView image = adView.findViewById(R.id.iv_tool_pic);
            TextView title = adView.findViewById(R.id.tv_ad_title);
            TextView tool = adView.findViewById(R.id.tv_tool_name);
            CardView adLink = adView.findViewById(R.id.cv_ad);

            image.setImageBitmap(ad.getTool().getPicture());
            title.setText(ad.getTitle());
            tool.setText(ad.getTool().getName());

            adLink.setClickable(true);
            adLink.setOnClickListener(new View.OnClickListener() {
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

            mAdsLayout.addView(adView);
        }
    }
}
