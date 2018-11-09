package com.toolshare.toolshare.models;

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

import com.toolshare.toolshare.LoginActivity;
import com.toolshare.toolshare.R;
import com.toolshare.toolshare.RegisterActivity;
import com.toolshare.toolshare.db.DbHandler;

import java.sql.Date;
import java.sql.Time;
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
    private CalendarView mCalendar;
    private LinearLayout mAdLinearLayout;
    private String dateButtonClicked;
    private Button mCreateAdButton;
    private TimePicker mTimePicker;
    private Button mStartTimeButton;
    private Button mEndTimeButton;
    private String timeButtonClicked;
    private Button mTimeOkButton;
    private RelativeLayout mTimePickerRelativeLayout;
    private int selectedHour;
    private int selectedMinute;
    private Calendar calendar = Calendar.getInstance();

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
        mCalendar = (CalendarView) view.findViewById(R.id.simpleCalendarView);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                Date date = new Date(calendar.getTimeInMillis());

                if (dateButtonClicked.equals("start")) {
                    ad.getAvailability().setStartDate(date);
                    mStartDateButton.setText(mStartDateButton.getText() + ": " + ad.getAvailability().getStartDate().toString());
                } else {
                    ad.getAvailability().setEndDate(date);
                    mEndDateButton.setText(mEndDateButton.getText() + ": " + ad.getAvailability().getEndDate().toString());
                }
                mCalendar.setVisibility(View.GONE);
                mAdLinearLayout.setVisibility(View.VISIBLE);
            }
        });
        mCalendar.setVisibility(View.GONE);
        mTitle = (EditText) view.findViewById(R.id.et_ad_title);
        mTool = (Spinner) view.findViewById(R.id.s_ad_tool);
        mDescription = (EditText) view.findViewById(R.id.et_ad_description);
        mStartDateButton = (Button) view.findViewById(R.id.b_ad_start_date);
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateButtonClicked = "start";
                mAdLinearLayout.setVisibility(View.GONE);
                mCalendar.setVisibility(View.VISIBLE);
            }
        });
        mEndDateButton = (Button) view.findViewById(R.id.b_ad_end_date);
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateButtonClicked = "end";
                mAdLinearLayout.setVisibility(View.GONE);
                mCalendar.setVisibility(View.VISIBLE);
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
        mTimePickerRelativeLayout = (RelativeLayout) view.findViewById(R.id.l_time_picker);
        mTimeOkButton = (Button) view.findViewById(R.id.b_time_picker_ok);
        mTimeOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeButtonClicked.equals("start")) {
                    ad.getAvailability().setEndTime(new Time(selectedHour, selectedMinute, 0));
                }
                mAdLinearLayout.setVisibility(View.VISIBLE);
                mTimePickerRelativeLayout.setVisibility(View.GONE);
            }
        });
        mTimePicker = (TimePicker) view.findViewById(R.id.simpleTimePicker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                selectedHour = hourOfDay;
                selectedMinute = minute;
            }
        });
        mTimePickerRelativeLayout.setVisibility(View.GONE);
        mStartTimeButton = (Button) view.findViewById(R.id.b_availability_start_time);
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeButtonClicked = "start";
                mAdLinearLayout.setVisibility(View.GONE);
                mTimePickerRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        mEndTimeButton = (Button) view.findViewById(R.id.b_availability_end_time);
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeButtonClicked = "end";
                mAdLinearLayout.setVisibility(View.GONE);
                mTimePickerRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        loadSpinner();

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sunday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableSunday(), v));
                break;
            case R.id.b_monday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableMonday(), v));
                break;
            case R.id.b_tuesday:
                ad.getAvailability().setAvailableTuesday(repeatDayClicked(ad.getAvailability().isAvailableTuesday(), v));
                break;
            case R.id.b_wednesday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableWednesday(), v));
                break;
            case R.id.b_thursday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableThursday(), v));
                break;
            case R.id.b_friday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableFriday(), v));
                break;
            case R.id.b_saturday:
                ad.getAvailability().setAvailableMonday(repeatDayClicked(ad.getAvailability().isAvailableSaturday(), v));
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
        ad.setId(Ad.getNextId(db));
        ad.setTitle(mTitle.getText().toString());
        Tool tool = (Tool) mTool.getSelectedItem();
        ad.setToolId(tool.getId());
        ad.setDescription(mDescription.getText().toString());
        java.util.Date today = new java.util.Date();
        ad.setPostDate(today);
        ad.getAvailability().setStartTime(new java.sql.Time(today.getTime()));
        ad.getAvailability().setEndTime(new java.sql.Time(today.getTime()));
        ad.setExpirationDate(ad.getAvailability().getEndDate());

        Ad finalAd = ad;
    }
}
