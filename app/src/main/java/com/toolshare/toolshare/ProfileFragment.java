package com.toolshare.toolshare;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Request;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import java.util.List;

import static com.toolshare.toolshare.models.RequestStatus.getStatusByPk;


public class ProfileFragment extends Fragment {

    private Bundle bundle;
    private TextView mUsername;
    private Button mToolsButton;
    private Button mAdsButton;
    private Button mMyRequestsButton;
    private Button mRequestsForMeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profile = inflater.inflate(R.layout.fragment_profile, null);
        bundle = getArguments();

        mUsername = (TextView) profile.findViewById(R.id.tv_username);
        setUsername();

        mToolsButton = (Button) profile.findViewById(R.id.b_tools);
        mAdsButton = (Button) profile.findViewById(R.id.b_ads);
        mMyRequestsButton = (Button) profile.findViewById(R.id.b_my_requests);
        mRequestsForMeButton = (Button) profile.findViewById(R.id.b_requests_for_me);

        mToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ToolListFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AdsListFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mMyRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ToolListFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mRequestsForMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ToolListFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return profile;
    }

    private void setUsername() {
        User user = new User();
        user = user.getUser((DbHandler) bundle.getSerializable("db"), bundle.getString("userEmail"));
        mUsername.setText(user.getFirstName() + " " + user.getLastName());
    }

    private void addRequestButton(LinearLayout layout, final Request request) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(425, 500);
        linearLayoutParams.setMargins(10,0,10,0);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(425, 150);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(425, 350);
        imageViewParams.setMargins(10,10,10,10);

        LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.GRAY);

        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(request.getAd().getTool().getPicture());
        imageView.setLayoutParams(imageViewParams);

        TextView textView = new TextView(getActivity().getApplicationContext());
        textView.setText(getStatusByPk((DbHandler) bundle.getSerializable("db"), request.getStatusId()));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(textViewParams);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setClickable(true);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("request", request);
                Fragment fragment = new ViewRentRequestFragment();
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
}
