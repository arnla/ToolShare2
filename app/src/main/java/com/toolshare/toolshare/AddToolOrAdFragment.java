package com.toolshare.toolshare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.toolshare.toolshare.db.DbHandler;


public class AddToolOrAdFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private ImageButton mNewToolButton;
    private ImageButton mNewAdButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profile = inflater.inflate(R.layout.fragment_add_tool_or_ad, null);
        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        mNewToolButton = (ImageButton) profile.findViewById(R.id.b_new_tool);
        mNewToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewToolFragment();
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mNewAdButton = (ImageButton) profile.findViewById(R.id.b_new_ad);
        mNewAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewAdFragment();
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
}
