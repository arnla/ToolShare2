package com.toolshare.toolshare;

import android.content.Context;
import android.media.Image;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button mDeleteButton;
    private Button mEditButton;
    private ImageView mImage;
    private TextView mNoRatings;
    private RatingBar mRating;

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
        mDeleteButton = (Button) view.findViewById(R.id.b_delete_tool);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTool();
            }
        });
        mEditButton = (Button) view.findViewById(R.id.b_edit_tool);
        mImage = (ImageView) view.findViewById(R.id.iv_tool_picture);
        mNoRatings = (TextView) view.findViewById(R.id.tv_no_ratings);
        mRating = (RatingBar) view.findViewById(R.id.rb_tool_rating);

        setToolValues();

        return view;
    }

    private void setToolValues() {
        mToolName.setText(tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());
        mImage.setImageBitmap(tool.getPicture());
        if (tool.getRating() == 0) {
            mNoRatings.setVisibility(View.VISIBLE);
        } else {
            mRating.setRating(tool.getRating());
        }
    }

    private void deleteTool() {
        tool.deleteTool(db, tool.getId());
        Toast.makeText(getActivity(), "Tool deleted", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
