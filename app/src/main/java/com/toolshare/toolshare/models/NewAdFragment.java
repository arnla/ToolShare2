package com.toolshare.toolshare.models;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.toolshare.toolshare.R;
import com.toolshare.toolshare.db.DbHandler;

public class NewAdFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Ad ad;
    private EditText mTitle;
    private Spinner mTool;
    private EditText mDescription;
    private Button mStartDateButton;
    private Button mEndDateButton;
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
        View view = inflater.inflate(R.layout.fragment_new_ad, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = new Ad();
        ad.setOwner(bundle.getString("userEmail"));

        return view;
    }
}
