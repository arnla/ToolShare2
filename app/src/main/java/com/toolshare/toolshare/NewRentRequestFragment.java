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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Tool.getToolByPk;
import static com.toolshare.toolshare.models.User.getUserNameByPk;


public class NewRentRequestFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Request request;
    private User requester;
    private User owner;
    private Ad ad;
    private Tool tool;
    private Availability availability;
    private TextView mAdLink;
    private TextView mToolLink;
    private Button mStartDateButton;
    private Button mEndDateButton;
    private LinearLayout mRentRequestLayout;

    private String dateButtonClicked = "";

    private CalendarView mCalendar;
    private RelativeLayout mTimePickerLayout;
    private Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_rent_request, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) bundle.getSerializable("tool");
        availability = (Availability) bundle.getSerializable("availability");
        request = new Request();
        request.setRequesterId(bundle.getString("userEmail"));
        request.setOwnerId(ad.getOwner());
        request.setAdId(ad.getId());

        mRentRequestLayout = (LinearLayout) view.findViewById(R.id.ll_rent_request);
        mCalendar = (CalendarView) view.findViewById(R.id.simpleCalendarView);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                Date date = new Date(calendar.getTimeInMillis());

                if (dateButtonClicked.equals("start")) {
                    request.setRequestedStartDate(date);
                    mStartDateButton.setText("Start Date: " + request.getRequestedStartDate().toString());
                } else {
                    request.setRequestedEndDate(date);
                    mEndDateButton.setText("End Date: " + request.getRequestedEndDate().toString());
                }
                mCalendar.setVisibility(View.GONE);
                mRentRequestLayout.setVisibility(View.VISIBLE);
            }
        });
        mCalendar.setVisibility(View.GONE);
        mTimePickerLayout = (RelativeLayout) view.findViewById(R.id.l_rent_request_time_picker);
        mTimePickerLayout.setVisibility(View.GONE);

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

        mStartDateButton = (Button) view.findViewById(R.id.b_rent_request_start_date);
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateButtonClicked = "start";
                mRentRequestLayout.setVisibility(View.GONE);
                mCalendar.setVisibility(View.VISIBLE);
            }
        });
        mEndDateButton = (Button) view.findViewById(R.id.b_rent_request_end_date);
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateButtonClicked = "end";
                mRentRequestLayout.setVisibility(View.GONE);
                mCalendar.setVisibility(View.VISIBLE);
            }
        });

        setValues();

        return view;
    }

    private void setValues() {
        mAdLink.setText(ad.getTitle());
        mAdLink.setTextColor(Color.BLUE);
        mAdLink.setClickable(true);
        mToolLink.setText(tool.getName());
        mToolLink.setTextColor(Color.BLUE);
        mToolLink.setClickable(true);
    }
}
