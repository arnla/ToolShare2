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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolReview;
import com.toolshare.toolshare.models.User;

import java.util.List;

import static com.toolshare.toolshare.models.Tool.updateTool;
import static com.toolshare.toolshare.models.ToolReview.addToolReview;
import static com.toolshare.toolshare.models.ToolReview.getAllRatingsByToolId;

public class ViewProfileFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private User user;
    private TextView mOwner;
    private TextView mUserEmail;
    private TextView mUserPhone;
    private TextView mUserCity;
    private TextView mUserProvince;
    private TextView mUserCountry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        user = (User) bundle.getSerializable("user");

        mOwner = (TextView) view.findViewById(R.id.tv_user_name);
        mUserEmail = (TextView) view.findViewById(R.id.tv_user_email);
        mUserPhone = (TextView) view.findViewById(R.id.tv_user_phone);
        mUserCity = (TextView) view.findViewById(R.id.tv_user_city);
        mUserProvince = (TextView) view.findViewById(R.id.tv_user_province);
        mUserCountry = (TextView) view.findViewById(R.id.tv_user_country);

        setUserValues();

        return view;
}

    private void setUserValues() {
        mUserEmail.setText(user.getEmail());
        mOwner.setText(user.getFirstName() + " " + user.getLastName());
        mOwner.setTextColor(Color.BLUE);
        mUserPhone.setText(user.getPhone());
        mUserCity.setText(user.getCity());
        mUserProvince.setText(user.getProvince());
        mUserCountry.setText(user.getCountry());
    }
}
