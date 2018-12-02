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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static com.toolshare.toolshare.models.User.getUser;




import java.util.List;


public class NotificationsFragment extends Fragment {
    private Button mCardInfo;
    private Bundle bundle;
    private DbHandler db;
    private Request request;
    private Button mAccepted;
    private Button mDeclined;
    private Button mCancelled;
    private LinearLayout mMyNotifications;
    private Notification notification;
    private TextView mNotificationStatus;
    private int status;
    private Ad ad;
    private Tool tool;
    private User requester;
    private User owner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notificationsView = inflater.inflate(R.layout.fragment_notifications, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        request = (Request) bundle.getSerializable("request");
        ad = (Ad) bundle.getSerializable("ad");
        tool = (Tool) bundle.getSerializable("tool");

        requester = User.getUser(db, bundle.getString("userEmail"));
        owner = User.getUser(db, ad.getOwner());

        notification = new Notification();
        // the below variables that are commented out as they display to be null.
        /*notification.setRequesterId(bundle.getString("userEmail"));
        notification.setOwnerId(ad.getOwner());
        notification.setStatusId(request.getStatusId()); */
        notification.setViewingStatus(0);

        mMyNotifications = (LinearLayout) notificationsView.findViewById(R.id.ll_my_notifications);

        mMyNotifications.removeAllViews();
        List<Notification> notifications = Notification.getAllNotificationsByRequester((DbHandler) getArguments().getSerializable("db"), getArguments().getString("userEmail"));

        for (int i = 0; i < notifications.size(); i++) {
            addNotifications(mMyNotifications, notifications.get(i));
        }

        return notificationsView;
    }


    private void addNotifications(LinearLayout layout, final Notification notification) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(800, 500);
        linearLayoutParams.setMargins(10,0,10,0);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(800, 150);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(800, 350);
        imageViewParams.setMargins(10,10,10,10);

        LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.GRAY);

        /*ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(tool.getPicture());
        imageView.setLayoutParams(imageViewParams); */

        TextView textView = new TextView(getActivity().getApplicationContext());
        status = notification.getStatusId();
        //if(status == )
        textView.setText(status);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(textViewParams);

        //linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setClickable(true);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("notification", notification);
                Fragment fragment = new ViewToolFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        layout.addView(linearLayout);
    }




    public void rentalPayment(){


    }

    public void declinedRequest(){

    }

    public void cancelledRequest() {
    }

}
