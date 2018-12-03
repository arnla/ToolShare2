package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Tool;
import com.toolshare.toolshare.models.ToolAddress;
import com.toolshare.toolshare.models.ToolType;
import com.toolshare.toolshare.models.User;

import java.util.ArrayList;
import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAllAdsThatAreNotOwners;
import static com.toolshare.toolshare.models.Tool.getAllToolsByOwner;
import static com.toolshare.toolshare.models.ToolType.getAllToolTypes;


public class ToolListFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private LinearLayout mToolsLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool_list, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");

        mToolsLayout = (LinearLayout) view.findViewById(R.id.ll_tools);

        loadTools();

        return view;
    }

    private void loadTools() {
        List<Tool> tools = getAllToolsByOwner(db, bundle.getString("userEmail"));

        for (int i = 0; i < tools.size(); i++) {
            final Tool tool = tools.get(i);
            LayoutInflater li = LayoutInflater.from(getActivity());
            View toolView = li.inflate(R.layout.layout_tool, null);

            ImageView image = toolView.findViewById(R.id.iv_tool_pic);
            TextView name = toolView.findViewById(R.id.tv_name);
            CardView toolLink = toolView.findViewById(R.id.cv_tool);

            image.setImageBitmap(tool.getPicture());
            name.setText(tool.getName());

            toolLink.setClickable(true);
            toolLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("tool", tool);
                    Fragment fragment = new ViewAdFragment();
                    fragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            mToolsLayout.addView(toolView);
        }
    }
}
