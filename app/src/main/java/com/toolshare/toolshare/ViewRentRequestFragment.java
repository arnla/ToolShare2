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
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import java.util.Calendar;
import java.util.Date;

import static com.toolshare.toolshare.models.Request.addRequest;

public class ViewRentRequestFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private Request request;
    private User requester;
    private User owner;
    private TextView mAdLink;
    private TextView mToolLink;
    private LinearLayout mRentRequestLayout;
    private RadioGroup mDeliveryMethod;
    private TextView mStartDate;
    private TextView mEndDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_rent_request, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        request = (Request) bundle.getSerializable("request");
        requester = request.getRequester();
        owner = request.getOwner();

        mRentRequestLayout = (LinearLayout) view.findViewById(R.id.ll_rent_request);

        mAdLink = (TextView) view.findViewById(R.id.tv_rent_request_ad);
        mAdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewAdFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mToolLink = (TextView) view.findViewById(R.id.tv_rent_request_tool);
        mToolLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewToolFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mDeliveryMethod = (RadioGroup) view.findViewById(R.id.rg_delivery_method);
        for (int i = 0; i < mDeliveryMethod.getChildCount(); i++) {
            ((RadioButton) mDeliveryMethod.getChildAt(i)).setEnabled(false);
        }

        mStartDate = (TextView) view.findViewById(R.id.tv_rent_request_start_date);
        mEndDate = (TextView) view.findViewById(R.id.tv_rent_request_end_date);

        setValues();

        return view;
    }

    private void setValues() {
        mAdLink.setText(request.getAd().getTitle());
        mAdLink.setTextColor(Color.BLUE);
        mAdLink.setClickable(true);
        mToolLink.setText((Tool.getToolByPk(db, request.getAd().getToolId())).getName());
        mToolLink.setTextColor(Color.BLUE);
        mToolLink.setClickable(true);
        mStartDate.setText(request.getRequestedStartDate().toString());
        mEndDate.setText(request.getRequestedEndDate().toString());
    }
}
