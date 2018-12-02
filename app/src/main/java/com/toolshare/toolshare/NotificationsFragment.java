package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.LinearLayout;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Notification;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import java.util.List;


public class NotificationsFragment extends Fragment {
    private Button mCardInfo;
    private Bundle bundle;
    private DbHandler db;
    private Request request;
    private int status;
    private Button mAccepted;
    private Button mDeclined;
    private Button mCancelled;
    private LinearLayout mMyNotifications;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notificationsView = inflater.inflate(R.layout.fragment_notifications, null);

       /* bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        request = (Request) bundle.getSerializable("request");
        status = request.getStatusId();

        mMyNotifications.removeAllViews();
        List<Notification> notifications = Notification.getAllNotificationsByOwner((DbHandler) getArguments().getSerializable("db"), getArguments().getString("userEmail"));



        for (int i = 0; i < notifications.size(); i++) {
            AddNotification(mMyNotifications, notifications.get(i));
        }

        if (status == 2){
            rentalPayment();
        } else if (status == 3){
            declinedRequest();
        } else if (status == 4){
            cancelledRequest();
        }

        mCardInfo = (Button) notificationsView.findViewById(R.id.b_card_info);
        mCardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddCardFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return notifications;
    }


    public void rentalPayment(){


    }

    public void declinedRequest(){

    }

    public void cancelledRequest(){
*/
       return notificationsView;
    }
}
