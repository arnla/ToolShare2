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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Tool.getToolByPk;
import static com.toolshare.toolshare.models.User.getUserNameByPk;


public class NewRentRequestFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private User requester;
    private User owner;
    private Ad ad;
    private Tool tool;
    private Availability availability;
    private TextView mAdLink;
    private TextView mToolLink;

    private CalendarView mCalendar;
    private RelativeLayout mTimePickerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_rent_request, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) bundle.getSerializable("tool");
        availability = (Availability) bundle.getSerializable("availability");

        mAdLink = (TextView) view.findViewById(R.id.tv_rent_request_ad);
        mToolLink = (TextView) view.findViewById(R.id.tv_rent_request_tool);

        mCalendar = (CalendarView) view.findViewById(R.id.simpleCalendarView);
        mCalendar.setVisibility(View.GONE);
        mTimePickerLayout = (RelativeLayout) view.findViewById(R.id.l_rent_request_time_picker);
        mTimePickerLayout.setVisibility(View.GONE);

        setValues();

        return view;
    }

    private void setValues() {
        mAdLink.setText(ad.getTitle());
        mAdLink.setClickable(true);
        mToolLink.setText(tool.getName());
        mToolLink.setClickable(true);
    }
}
