package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Notification;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.RequestStatus;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;
import static android.app.Activity.RESULT_OK;
import static com.toolshare.toolshare.models.Notification.addNotification;
import static com.toolshare.toolshare.models.Notification.getAllNotificationsByOwner;
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
                title.setText("Your request has been accepted");
                break;
            case 3:
                title.setText("Your request has been rejected");
                break;
            case 4:
                title.setText("A request for your tool has been cancelled");
                break;
        }

        description.setText(notification.getRequest().getAd().getTitle());
        read.setChecked(notification.getViewingStatus() == 0);

        notificationLayout.setOnClickListener(new View.OnClickListener() {
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
            }
        });

        layout.addView(notificationView);
    }
}
