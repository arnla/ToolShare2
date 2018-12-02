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
import com.toolshare.toolshare.models.Notification;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.RequestStatus;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolSchedule;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Notification.addNotification;
import static com.toolshare.toolshare.models.Request.addRequest;
import static com.toolshare.toolshare.models.ToolSchedule.getDaysByRequestId;
import static com.toolshare.toolshare.models.ToolSchedule.updateToolScheduleStatus;

public class ViewRentRequestFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private Request request;
    private User requester;
    private User owner;
    private Ad ad;
    private Tool tool;
    private TextView mAdLink;
    private TextView mToolLink;
    private LinearLayout mRentRequestLayout;
    private RadioGroup mDeliveryMethod;
    private Button mAccept;
    private Button mReject;
    private Button mCancel;
    private TextView mStatus;
    private TextView mRequestedDays;
    private Calendar today = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_rent_request, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        request = (Request) bundle.getSerializable("request");
        requester = request.getRequester();
        owner = request.getOwner();
        ad = request.getAd();
        tool = Tool.getToolByPk(db, ad.getToolId());

        mRentRequestLayout = (LinearLayout) view.findViewById(R.id.ll_rent_request);

        mAdLink = (TextView) view.findViewById(R.id.tv_rent_request_ad);
        mAdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewAdFragment();
                bundle.putSerializable("ad", ad);
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
                bundle.putSerializable("tool", tool);
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mStatus = (TextView) view.findViewById(R.id.tv_rent_request_status);
        mStatus.setText("Status: " + RequestStatus.getStatusByPk(db, request.getStatusId()));

        mDeliveryMethod = (RadioGroup) view.findViewById(R.id.rg_delivery_method);
        for (int i = 0; i < mDeliveryMethod.getChildCount(); i++) {
            ((RadioButton) mDeliveryMethod.getChildAt(i)).setEnabled(false);
        }

        mRequestedDays = (TextView) view.findViewById(R.id.tv_requested_days);

        mAccept = (Button) view.findViewById(R.id.b_rent_request_accept);
        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
                Toast.makeText(getActivity(), "Request accepted!", Toast.LENGTH_LONG).show();
            }
        });
        mReject = (Button) view.findViewById(R.id.b_rent_request_reject);
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequest();
                Toast.makeText(getActivity(), "Request rejected!", Toast.LENGTH_LONG).show();
            }
        });
        mCancel = (Button) view.findViewById(R.id.b_rent_request_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRequest();
                Toast.makeText(getActivity(), "Request cancelled!", Toast.LENGTH_LONG).show();
            }
        });

        if (request.getRequesterId().equals(bundle.getString("userEmail"))) {
            mAccept.setVisibility(View.GONE);
            mReject.setVisibility(View.GONE);
        } else {
            mCancel.setVisibility(View.GONE);
        }

        setValues();

        return view;
    }

    private void acceptRequest() {
        request.setStatusId(2);
        Request.updateRequest(db, request);
        updateToolScheduleStatus(db, request.getId(), "Busy");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getOwnerId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
    }

    private void rejectRequest() {
        request.setStatusId(3);
        Request.updateRequest(db, request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getOwnerId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
    }

    private void cancelRequest() {
        request.setStatusId(4);
        Request.updateRequest(db, request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getOwnerId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
    }

    private void setValues() {
        mAdLink.setText(request.getAd().getTitle());
        mAdLink.setTextColor(Color.BLUE);
        mAdLink.setClickable(true);
        mToolLink.setText(tool.getName());
        mToolLink.setTextColor(Color.BLUE);
        mToolLink.setClickable(true);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = getDaysByRequestId(db, request.getId());
        String datesString = "";
        for (int i = 0; i < dates.size(); i++) {
            datesString += formatter.format(dates.get(i)) + "\n";
        }
        mRequestedDays.setText(datesString);
    }
}
