package com.toolshare.toolshare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUsFragment extends Fragment {


    private Bundle bundle;
    //    private TextView mUsername;
//    private LinearLayout mMyTools;
//    private LinearLayout mMyAds;
//    private LinearLayout mRentRequests;
//    private LinearLayout mMyRequests;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aboutus, null);
        bundle = getArguments();

//        mMyTools = (LinearLayout) profile.findViewById(R.id.ll_my_tools);
//        mMyAds = (LinearLayout) profile.findViewById(R.id.ll_my_ads);
//        mRentRequests = (LinearLayout) profile.findViewById(R.id.ll_rent_requests);
//        mMyRequests = (LinearLayout) profile.findViewById(R.id.ll_my_requests);
//
//        loadTools();
//        loadAds();
//        loadRentRequests();
//        loadMyRequests();
        return view;
    }
}
