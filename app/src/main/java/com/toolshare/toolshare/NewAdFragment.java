package com.toolshare.toolshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.toolshare.toolshare.LoginActivity;
import com.toolshare.toolshare.R;
import com.toolshare.toolshare.RegisterActivity;
import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Tool;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NewAdFragment extends Fragment implements View.OnClickListener {

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
    private CalendarView mCalendarStart;
    private CalendarView mCalendarEnd;
    private LinearLayout mAdLinearLayout;
    private Button mCreateAdButton;
    private Calendar calendar = Calendar.getInstance();
    private EditText mPrice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_ad, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = new Ad();
        ad.setOwner(bundle.getString("userEmail"));
        ad.setAvailability(new Availability());
        ad.getAvailability().setAvailableSunday(false);
        ad.getAvailability().setAvailableMonday(false);
        ad.getAvailability().setAvailableTuesday(false);
        ad.getAvailability().setAvailableWednesday(false);
        ad.getAvailability().setAvailableThursday(false);
        ad.getAvailability().setAvailableFriday(false);
        ad.getAvailability().setAvailableSaturday(false);

        mAdLinearLayout = (LinearLayout) view.findViewById(R.id.l_new_ad);
        mAdLinearLayout.setVisibility(View.VISIBLE);
        mCalendarStart = (CalendarView) view.findViewById(R.id.cv_start_date);
        mCalendarStart.setMinDate(System.currentTimeMillis());
        mCalendarStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                Date date = new Date(calendar.getTimeInMillis());
                ad.getAvailability().setStartDate(date);
                mStartDateButton.setText("Start Date: " + ad.getAvailability().getStartDate().toString());
                mCalendarEnd.setMinDate(ad.getAvailability().getStartDate().getTime());
                mCalendarStart.setVisibility(View.GONE);
                mAdLinearLayout.setVisibility(View.VISIBLE);
            }
        });
        mCalendarStart.setVisibility(View.GONE);
        mCalendarEnd = (CalendarView) view.findViewById(R.id.cv_end_date);
        mCalendarEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                Date date = new Date(calendar.getTimeInMillis());
                ad.getAvailability().setEndDate(date);
                mEndDateButton.setText("End Date: " + ad.getAvailability().getEndDate().toString());
                mCalendarEnd.setVisibility(View.GONE);
                mAdLinearLayout.setVisibility(View.VISIBLE);
            }
        });
        mCalendarEnd.setVisibility(View.GONE);
        mTitle = (EditText) view.findViewById(R.id.et_ad_title);
        mTool = (Spinner) view.findViewById(R.id.s_ad_tool);
        mDescription = (EditText) view.findViewById(R.id.et_ad_description);
        mStartDateButton = (Button) view.findViewById(R.id.b_ad_start_date);
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdLinearLayout.setVisibility(View.GONE);
                mCalendarStart.setVisibility(View.VISIBLE);
            }
        });
        mEndDateButton = (Button) view.findViewById(R.id.b_ad_end_date);
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdLinearLayout.setVisibility(View.GONE);
                mCalendarEnd.setVisibility(View.VISIBLE);
            }
        });
        mMonday = (Button) view.findViewById(R.id.b_monday);
        mMonday.setOnClickListener(this);
        mTuesday = (Button) view.findViewById(R.id.b_tuesday);
        mTuesday.setOnClickListener(this);
        mWednesday = (Button) view.findViewById(R.id.b_wednesday);
        mWednesday.setOnClickListener(this);
        mThursday = (Button) view.findViewById(R.id.b_thursday);
        mThursday.setOnClickListener(this);
        mFriday = (Button) view.findViewById(R.id.b_friday);
        mFriday.setOnClickListener(this);
        mSaturday = (Button) view.findViewById(R.id.b_saturday);
        mSaturday.setOnClickListener(this);
        mSunday = (Button) view.findViewById(R.id.b_sunday);
        mSunday.setOnClickListener(this);
        mCreateAdButton = (Button) view.findViewById(R.id.b_create_ad);
        mCreateAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAd();
            }
        });
        mPrice = (EditText) view.findViewById(R.id.et_ad_price);

        loadSpinner();

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sunday:
                ad.getAvailability().setAvailableSunday(repeatDayClicked(ad.getAvailability().isAvailableSunday(), v));
                break;
            case R.id.b_monday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableMonday(), v));
                break;
            case R.id.b_tuesday:
                ad.getAvailability().setAvailableTuesday(repeatDayClicked(ad.getAvailability().isAvailableTuesday(), v));
                break;
            case R.id.b_wednesday:
                ad.getAvailability().setAvailableWednesday(repeatDayClicked(ad.getAvailability().isAvailableWednesday(), v));
                break;
            case R.id.b_thursday:
                ad.getAvailability().setAvailableThursday(repeatDayClicked(ad.getAvailability().isAvailableThursday(), v));
                break;
            case R.id.b_friday:
                ad.getAvailability().setAvailableFriday(repeatDayClicked(ad.getAvailability().isAvailableFriday(), v));
                break;
            case R.id.b_saturday:
                ad.getAvailability().setAvailableSaturday(repeatDayClicked(ad.getAvailability().isAvailableSaturday(), v));
                break;
        }
    }

    private boolean repeatDayClicked(boolean repeat, View v) {
        Button btn = (Button) v;
        if (repeat == false) {
            btn.setBackgroundColor(Color.BLACK);
            btn.setTextColor(Color.WHITE);
        } else {
            btn.setBackgroundColor(Color.WHITE);
            btn.setTextColor(Color.BLACK);
        }
        return !repeat;
    }

    private void loadSpinner() {
        List<Tool> tools = Tool.getAllToolsByOwner(db, bundle.getString("userEmail"));
        ArrayAdapter<Tool> dataAdapter = new ArrayAdapter<Tool>(getActivity(),
                android.R.layout.simple_spinner_item, tools);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTool.setAdapter(dataAdapter);
    }

    private void insertAd() {
        ad.setTitle(mTitle.getText().toString());
        Tool tool = (Tool) mTool.getSelectedItem();
        ad.setToolId(tool.getId());
        ad.setDescription(mDescription.getText().toString());
        Calendar today = Calendar.getInstance();
        ad.setPostDate(today.getTime());
        ad.setExpirationDate(ad.getAvailability().getEndDate());
        ad.setPrice(Integer.parseInt(mPrice.getText().toString()));

        ad.getAvailability().setAdId(ad.addAd(db));
        ad.getAvailability().addAvailability(db);
        Toast.makeText(getActivity(), "New ad added", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
