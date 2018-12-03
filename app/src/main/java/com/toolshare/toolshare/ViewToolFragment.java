package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.toolshare.toolshare.models.ToolReview;
import com.toolshare.toolshare.models.User;

import org.w3c.dom.Text;

import java.util.List;

import static android.view.View.VISIBLE;
import static com.toolshare.toolshare.models.Tool.updateTool;
import static com.toolshare.toolshare.models.ToolReview.addToolReview;
import static com.toolshare.toolshare.models.ToolReview.getAllRatingsByToolId;


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
    private TextView mLeaveReview;
    private RatingBar mRating;
    private TextView mOwner;

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
        mLeaveReview = (TextView) view.findViewById(R.id.tv_ratings);
        mRating = (RatingBar) view.findViewById(R.id.rb_tool_rating);
        mOwner = (TextView) view.findViewById(R.id.tv_owner);

        setToolValues();

        mLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View reviewView = li.inflate(R.layout.dialog_review, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(reviewView);

                final EditText userInput = (EditText) reviewView.findViewById(R.id.et_review);
                final RatingBar userRating = (RatingBar) reviewView.findViewById(R.id.rb_tool_rating) ;

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    ToolReview review = new ToolReview();
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        review.setToolId(tool.getId());
                                        review.setReview(userInput.getText().toString());
                                        review.setRating((int) userRating.getRating());

                                        addToolReview(db, review);

                                        // get new average of tool's ratings
                                        List<Integer> allRatings = getAllRatingsByToolId(db, tool.getId());
                                        float avg = 0;
                                        for (int i = 0; i < allRatings.size(); i++) {
                                            avg += allRatings.get(i);
                                        }
                                        avg = avg / allRatings.size();
                                        tool.setRating(avg);

                                        updateTool(db, tool);

                                        Fragment fragment = new ViewToolFragment();
                                        fragment.setArguments(bundle);

                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        transaction.replace(R.id.fragment_container, fragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        return view;
    }

    private void setToolValues() {
        mToolName.setText(tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());
        mImage.setImageBitmap(tool.getPicture());
        if (tool.getRating() == 0) {
            mLeaveReview.setText("Be the first to review");
        } else {
            mRating.setRating(tool.getRating());
            mLeaveReview.setText("Leave a review");
        }
        mLeaveReview.setTextColor(Color.BLUE);
        User user = User.getUser(db, tool.getOwner());
        mOwner.setText(user.getFirstName() + " " + user.getLastName());
        mOwner.setTextColor(Color.BLUE);
        if (bundle.getString("userEmail").equals(tool.getOwner())) {
            mEditButton.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
    }

    private void deleteTool() {
        tool.deleteTool(db, tool.getId());
        Toast.makeText(getActivity(), "Tool deleted", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
