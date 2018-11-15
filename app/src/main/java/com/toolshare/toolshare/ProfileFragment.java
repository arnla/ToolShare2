package com.toolshare.toolshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.User;

import java.util.List;


public class ProfileFragment extends Fragment {

    private Bundle bundle;
    private TextView mUsername;
    private LinearLayout mMyTools;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profile = inflater.inflate(R.layout.fragment_profile, null);
        bundle = getArguments();

        mUsername = (TextView) profile.findViewById(R.id.tv_username);
        setUsername();

        mMyTools = (LinearLayout) profile.findViewById(R.id.ll_my_tools);

        loadTools();
        return profile;
    }

    private void setUsername() {
        User user = new User();
        user = user.getUser((DbHandler) bundle.getSerializable("db"), bundle.getString("userEmail"));
        mUsername.setText(user.getFirstName() + " " + user.getLastName());
    }

    private void loadTools() {
        mMyTools.removeAllViews();
        List<Tool> tools = Tool.getAllToolsByOwner((DbHandler) getArguments().getSerializable("db"), getArguments().getString("userEmail"));

        for (int i = 0; i < tools.size(); i++) {
            addButton(mMyTools, tools.get(i));
        }
    }

    private void addButton(LinearLayout layout, final Tool tool) {
        Button button = new Button(getActivity().getApplicationContext());
        button.setHeight(15000);
        layout.addView(button);
        button.setText(tool.getName());
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                bundle.putSerializable("tool", tool);
                Fragment fragment = new ViewToolFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
