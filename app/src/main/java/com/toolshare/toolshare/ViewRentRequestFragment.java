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
import android.widget.ImageView;
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
import java.text.DecimalFormat;
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
    private CardView mAdLink;
    private ImageView mToolPic;
    private TextView mAdTitle;
    private TextView mToolName;
    private TextView mDeliveryMethod;
    private Button mAccept;
    private Button mReject;
    private Button mCancel;
    private TextView mStatus;
    private TextView mRequestedDays;
    private Calendar today = Calendar.getInstance();
    private TextView mTotal;
    private TextView mPrice;

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

        mAdLink = (CardView) view.findViewById(R.id.ll_ad).findViewById(R.id.cv_ad);
        mToolPic = (ImageView) mAdLink.findViewById(R.id.iv_tool_pic);
        mAdTitle = (TextView) mAdLink.findViewById(R.id.tv_ad_title);
        mToolName = (TextView) mAdLink.findViewById(R.id.tv_tool_name);
        mStatus = (TextView) view.findViewById(R.id.tv_rent_request_status);
        mTotal = (TextView) view.findViewById(R.id.tv_total);
        mPrice = (TextView) view.findViewById(R.id.tv_price);
        mRequestedDays = (TextView) view.findViewById(R.id.tv_requested_days);
        mDeliveryMethod = (TextView) view.findViewById(R.id.tv_delivery_pickup);

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


        mAccept = (Button) view.findViewById(R.id.b_rent_request_accept);
        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
            }
        });
        mReject = (Button) view.findViewById(R.id.b_rent_request_reject);
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequest();
            }
        });
        mCancel = (Button) view.findViewById(R.id.b_rent_request_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRequest();
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
        Notification notification = new Notification(request.getRequesterId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
        Toast.makeText(getActivity(), "Request accepted!", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    private void rejectRequest() {
        request.setStatusId(3);
        Request.updateRequest(db, request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getRequesterId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
        Toast.makeText(getActivity(), "Request rejected!", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    private void cancelRequest() {
        request.setStatusId(4);
        Request.updateRequest(db, request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Notification notification = new Notification(request.getOwnerId(), request.getId(), request.getStatusId(), 0, df.format(today.getTime()));
        addNotification(db, notification);
        Toast.makeText(getActivity(), "Request cancelled!", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    private void setValues() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = getDaysByRequestId(db, request.getId());
        String datesString = "";
        for (int i = 0; i < dates.size(); i++) {
            datesString += formatter.format(dates.get(i)) + "\n";
        }
        mRequestedDays.setText(datesString);
        float total = dates.size() * ad.getPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        mTotal.setText("$" + df.format(total));
        mPrice.setText("$" + df.format(ad.getPrice()));
        mStatus.setText(RequestStatus.getStatusByPk(db, request.getStatusId()));
        mToolPic.setImageBitmap(tool.getPicture());
        mAdTitle.setText(ad.getTitle());
        mToolName.setText(tool.getName());
        mDeliveryMethod.setText(request.getDeliveryMethod());

/*        if (request.getRequesterId().equals(bundle.getString("userEmail")) || (request.getStatusId() != 1)) {
            mCancel.setVisibility(View.VISIBLE);
            mAccept.setVisibility(View.GONE);
            mReject.setVisibility(View.GONE);
        } else {
            mCancel.setVisibility(View.GONE);
            mAccept.setVisibility(View.VISIBLE);
            mReject.setVisibility(View.VISIBLE);
        }*/

        if (request.getStatusId() != 1) {
            mCancel.setVisibility(View.GONE);
            mAccept.setVisibility(View.GONE);
            mReject.setVisibility(View.GONE);
        } else {
            if (request.getRequesterId().equals(bundle.getString("userEmail"))) {
                mCancel.setVisibility(View.VISIBLE);
                mAccept.setVisibility(View.GONE);
                mReject.setVisibility(View.GONE);
            } else {
                mCancel.setVisibility(View.GONE);
                mAccept.setVisibility(View.VISIBLE);
                mReject.setVisibility(View.VISIBLE);
            }
        }

    }
}
