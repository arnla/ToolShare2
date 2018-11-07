package com.toolshare.toolshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Brand;
import com.toolshare.toolshare.models.Tool;

import org.w3c.dom.Text;


public class ViewToolFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Tool tool;
    private TextView mToolName;
    private TextView mToolYear;
    private TextView mToolBrand;
    private TextView mToolModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_tool, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        tool = (Tool) bundle.getSerializable("tool");

        mToolName = (TextView) view.findViewById(R.id.tv_tool_name);
        mToolYear = (TextView) view.findViewById(R.id.tv_tool_year);
        mToolBrand = (TextView) view.findViewById(R.id.tv_tool_brand);
        mToolModel = (TextView) view.findViewById(R.id.tv_tool_model);

        mToolName.setText(tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());

        return view;
    }
}
