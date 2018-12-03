package com.toolshare.toolshare;

import android.content.Context;
import android.graphics.Color;
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
import com.toolshare.toolshare.models.Notification;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolSchedule;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByPk;
import static com.toolshare.toolshare.models.Notification.addNotification;
import static com.toolshare.toolshare.models.Request.addRequest;
import static com.toolshare.toolshare.models.Tool.getToolByPk;
import static com.toolshare.toolshare.models.ToolSchedule.getBusyDaysByToolId;
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
    private CardView mAdLink;
    private ImageView mToolPic;
    private TextView mAdTitle;
    private TextView mToolName;
    private LinearLayout mRentRequestLayout;
    private LinearLayout mDatesLayout;
    private Button mSelectDates;
    private Button mDatesOk;
    private CalendarPickerView mCalendar;
    private Calendar calendar = Calendar.getInstance();
    private Button mSubmitRequest;
    private RadioGroup mDeliveryMethod;
    private List<Integer> daysAllowed = new ArrayList<Integer>() {};
    private List<Date> daysNotAllowed;
    private List<ToolSchedule> toolSchedules = new ArrayList<ToolSchedule>() {};
    private TextView mRequestedDates;

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
        mRequestedDates = (TextView) view.findViewById(R.id.tv_requested_dates);
        mDatesLayout = (LinearLayout) view.findViewById(R.id.ll_dates);
        mSelectDates = (Button) view.findViewById(R.id.b_select_dates);
        mDatesOk = (Button) view.findViewById(R.id.b_dates_ok);
        mAdLink = (CardView) view.findViewById(R.id.ll_ad).findViewById(R.id.cv_ad);
        mToolPic = (ImageView) mAdLink.findViewById(R.id.iv_tool_pic);
        mAdTitle = (TextView) mAdLink.findViewById(R.id.tv_ad_title);
        mToolName = (TextView) mAdLink.findViewById(R.id.tv_tool_name);
        mSubmitRequest = (Button) view.findViewById(R.id.b_rent_request_submit);
        mDeliveryMethod = (RadioGroup) view.findViewById(R.id.rg_delivery_method);
        
        mDatesLayout.setVisibility(View.GONE);
        mSelectDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRentRequestLayout.setVisibility(View.GONE);
                mDatesLayout.setVisibility(View.VISIBLE);
            }
        });

        mDatesOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatesLayout.setVisibility(View.GONE);
                mRentRequestLayout.setVisibility(View.VISIBLE);
                setSelectedDates();
            }
        });

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

        mSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest();
            }
        });

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
        setCalendar();

        return view;
    }

    private void submitRequest() {
        request.setStatusId(1);
        int requestId = addRequest(db, request);
        for (int i = 0; i < toolSchedules.size(); i++) {
            toolSchedules.get(i).setRequestId(requestId);
            insertToolSchedule(db, toolSchedules.get(i));
        }

        // send notification to owner
        Calendar today = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getOwnerId(), requestId, request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);

        Toast.makeText(getActivity(), "Request submitted", Toast.LENGTH_LONG).show();
    }

    private void setValues() {
        mAdLink.setClickable(true);
        mToolPic.setImageBitmap(tool.getPicture());
        mAdTitle.setText(ad.getTitle());
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

        daysNotAllowed = getBusyDaysByToolId(db, tool.getId());

        mCalendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                c.setTime(date);
                int dow = c.get(Calendar.DAY_OF_WEEK);
                return daysAllowed.contains(dow) && !daysNotAllowed.contains(date);
            }
        });

        // get the end date plus 1 day
        c.setTime(ad.getAvailability().getEndDate());
        c.add(Calendar.DATE, 1);
        mCalendar.init(ad.getAvailability().getStartDate(), c.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
    }

    private void setSelectedDates() {
        List<Date> dates = mCalendar.getSelectedDates();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String datesString = "";
        for (int i = 0; i < dates.size(); i++) {
            datesString += formatter.format(dates.get(i)) + "\n";
        }
        mRequestedDates.setText(datesString);
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
