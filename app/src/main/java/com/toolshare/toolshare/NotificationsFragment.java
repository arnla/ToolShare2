package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Notification;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.RequestStatus;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolReview;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;
import static com.toolshare.toolshare.models.Notification.addNotification;
import static com.toolshare.toolshare.models.Notification.getAllNotificationsByOwner;
import static com.toolshare.toolshare.models.Notification.updateNotification;
import static com.toolshare.toolshare.models.Tool.updateTool;
import static com.toolshare.toolshare.models.ToolReview.addToolReview;
import static com.toolshare.toolshare.models.ToolReview.getAllRatingsByToolId;
import static com.toolshare.toolshare.models.User.getUser;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NotificationsFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private LinearLayout mNotificationLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");

        mNotificationLayout = (LinearLayout) view.findViewById(R.id.ll_notifications);


        loadNotifications();

        return view;
    }

    private void loadNotifications() {
        mNotificationLayout.removeAllViews();
        List<Notification> notifications = getAllNotificationsByOwner(db, bundle.getString("userEmail"));
        Collections.reverse(notifications);

        for (int i = 0; i < notifications.size(); i++) {
            addNotification(mNotificationLayout, notifications.get(i));
        }
    }

    private void addNotification(LinearLayout layout, final Notification notification) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View notificationView = li.inflate(R.layout.layout_notification, null);

        TextView title = (TextView) notificationView.findViewById(R.id.tv_title);
        TextView description = (TextView) notificationView.findViewById(R.id.tv_description);
        RadioButton read = (RadioButton)  notificationView.findViewById(R.id.rb_read_unread);
        CardView notificationLayout = (CardView) notificationView.findViewById(R.id.cv_notification);

        switch (notification.getStatusId()) {
            case 1:
                title.setText("New rent request");
                break;
            case 2:
                title.setText("Request accepted");
                break;
            case 3:
                title.setText("Request rejected");
                break;
            case 4:
                title.setText("Request cancelled");
                break;
        }

        description.setText(notification.getRequest().getAd().getTitle());
        read.setChecked(notification.getViewingStatus() == 0);

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View notificationView = li.inflate(R.layout.dialog_notification, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(notificationView);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setNeutralButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Fragment fragment = new NotificationsFragment();
                                        fragment.setArguments(bundle);

                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        transaction.replace(R.id.fragment_container, fragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();

                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                final TextView nTitle = (TextView) notificationView.findViewById(R.id.tv_title);
                final TextView nRequest = (TextView) notificationView.findViewById(R.id.tv_request);
                final TextView nRequesterOwner = (TextView) notificationView.findViewById(R.id.tv_owner);
                final TextView nRequesterOwnerLabel = (TextView) notificationView.findViewById(R.id.tv_requester_owner);

                switch (notification.getStatusId()) {
                    case 1:
                        nTitle.setText("New rent request");
                        nRequesterOwnerLabel.setText("Requester: ");
                        nRequesterOwner.setText(notification.getRequest().getRequester().getFirstName() + " " + notification.getRequest().getRequester().getLastName());
                        break;
                    case 2:
                        nTitle.setText("Request accepted");
                        nRequesterOwnerLabel.setText("Owner: ");
                        nRequesterOwner.setText(notification.getRequest().getOwner().getFirstName() + " " + notification.getRequest().getOwner().getLastName());
                        break;
                    case 3:
                        nTitle.setText("Request rejected");
                        nRequesterOwnerLabel.setText("Owner: ");
                        nRequesterOwner.setText(notification.getRequest().getOwner().getFirstName() + " " + notification.getRequest().getOwner().getLastName());
                        break;
                    case 4:
                        nTitle.setText("Request cancelled");
                        nRequesterOwnerLabel.setText("Requester: ");
                        nRequesterOwner.setText(notification.getRequest().getRequester().getFirstName() + " " + notification.getRequest().getRequester().getLastName());
                        break;
                }

                nRequest.setText(notification.getRequest().getAd().getTitle());
                nRequest.setTextColor(Color.BLUE);
                nRequesterOwner.setTextColor(Color.BLUE);

                nRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new ViewRentRequestFragment();
                        bundle.putSerializable("request", notification.getRequest());
                        fragment.setArguments(bundle);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        alertDialog.cancel();
                    }
                });

                // toggle read/unread flag
                if (notification.getViewingStatus() == 0) {
                    notification.setViewingStatus(1);
                    updateNotification(db, notification);
                }

                // show it
                alertDialog.show();
            }
        });

        layout.addView(notificationView);
    }
}
