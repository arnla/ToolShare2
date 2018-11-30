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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Availability;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolSchedule;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Request.addRequest;
import static com.toolshare.toolshare.models.Tool.getToolByPk;
import static com.toolshare.toolshare.models.ToolSchedule.insertToolSchedule;
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
    private LinearLayout mToolLink;
    private TextView mToolName;
    private ImageView mToolImage;
    private LinearLayout mRentRequestLayout;
    private LinearLayout mDatesLayout;
    private Button mSelectDates;
    private Button mDatesOk;
    private CalendarPickerView mCalendar;
    private Calendar calendar = Calendar.getInstance();
    private Button mSubmitRequest;
    private RadioGroup mDeliveryMethod;
    private List<Integer> daysAllowed = new ArrayList<Integer>() {};
    private List<Integer> daysNotAllowed = new ArrayList<Integer>() {};
    private List<ToolSchedule> toolSchedules = new ArrayList<ToolSchedule>() {};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_rent_request, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) bundle.getSerializable("tool");
        requester = User.getUser(db, bundle.getString("userEmail"));
        owner = User.getUser(db, ad.getOwner());
        availability = (Availability) bundle.getSerializable("availability");
        request = new Request();
        request.setRequesterId(bundle.getString("userEmail"));
        request.setOwnerId(ad.getOwner());
        request.setAdId(ad.getId());

        mRentRequestLayout = (LinearLayout) view.findViewById(R.id.ll_rent_request);
        mCalendar = (CalendarPickerView) view.findViewById(R.id.cv_dates);
        setCalendar();

        mDatesLayout = (LinearLayout) view.findViewById(R.id.ll_dates);
        mDatesLayout.setVisibility(View.GONE);
        mSelectDates = (Button) view.findViewById(R.id.b_select_dates);
        mSelectDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRentRequestLayout.setVisibility(View.GONE);
                mDatesLayout.setVisibility(View.VISIBLE);
            }
        });

        mDatesOk = (Button) view.findViewById(R.id.b_dates_ok);
        mDatesOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatesLayout.setVisibility(View.GONE);
                mRentRequestLayout.setVisibility(View.VISIBLE);
                setSelectedDates();
            }
        });

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
        mToolLink = (LinearLayout) view.findViewById(R.id.ll_tool);
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
        mToolName = (TextView) view.findViewById(R.id.tv_rent_request_tool);
        mToolImage = (ImageView) view.findViewById(R.id.iv_tool_picture);
        mToolImage.setImageBitmap(tool.getPicture());

        mSubmitRequest = (Button) view.findViewById(R.id.b_rent_request_submit);
        mSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest();
            }
        });

        mDeliveryMethod = (RadioGroup) view.findViewById(R.id.rg_delivery_method);
        mDeliveryMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_delivery:
                        request.setDeliveryMethod("Delivery");
                        break;
                    case R.id.rb_pickup:
                        request.setDeliveryMethod("Pickup");
                        break;
                }
            }
        });

        setValues();

        return view;
    }

    private void submitRequest() {
        request.setStatusId(1);
        int requestId = addRequest(db, request);
        for (int i = 0; i < toolSchedules.size(); i++) {
            toolSchedules.get(i).setRequestId(requestId);
            insertToolSchedule(db, toolSchedules.get(i));
        }
        Toast.makeText(getActivity(), "Request submitted", Toast.LENGTH_LONG).show();
    }

    private void setValues() {
        mAdLink.setText(ad.getTitle());
        mAdLink.setTextColor(Color.BLUE);
        mAdLink.setClickable(true);
        mToolName.setText(tool.getName());
    }

    private void setCalendar() {
        final Calendar c = Calendar.getInstance();

        //set the days allows mtwtfs
        if (ad.getAvailability().isAvailableSunday()) {
            daysAllowed.add(Calendar.SUNDAY);
        }

        if (ad.getAvailability().isAvailableMonday()) {
            daysAllowed.add(Calendar.MONDAY);
        }

        if (ad.getAvailability().isAvailableTuesday()) {
            daysAllowed.add(Calendar.TUESDAY);
        }

        if (ad.getAvailability().isAvailableWednesday()) {
            daysAllowed.add(Calendar.WEDNESDAY);
        }

        if (ad.getAvailability().isAvailableThursday()) {
            daysAllowed.add(Calendar.THURSDAY);
        }

        if (ad.getAvailability().isAvailableFriday()) {
            daysAllowed.add(Calendar.FRIDAY);
        }

        if (ad.getAvailability().isAvailableSaturday()) {
            daysAllowed.add(Calendar.SATURDAY);
        }

        mCalendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                c.setTime(date);
                int dow = c.get(Calendar.DAY_OF_WEEK);
                return daysAllowed.contains(dow);
            }
        });

        // get the end date plus 1 day
        c.setTime(ad.getAvailability().getEndDate());
        c.add(Calendar.DATE, 1);
        mCalendar.init(ad.getAvailability().getStartDate(), c.getTime())
                .withSelectedDate(ad.getAvailability().getStartDate())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
    }

    private void setSelectedDates() {
        List<Date> dates = mCalendar.getSelectedDates();
        for (int i = 0; i < dates.size(); i++) {
            ToolSchedule toolSchedule = new ToolSchedule();
            toolSchedule.setStatus("Pending");
            toolSchedule.setDate(dates.get(i));
            toolSchedule.setToolId(tool.getId());
            toolSchedule.setTool(tool);
            toolSchedules.add(toolSchedule);
        }
    }
}
