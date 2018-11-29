package com.toolshare.toolshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private TextView mRateTool;
    private Button mDeleteButton;
    private Button mEditButton;
    private Button mSubmitButton;
    private RatingBar mToolRating;
    private Button mCardInfo;
    private AlertDialog.Builder ratingPopup;
    private LinearLayout linearLayout;
    private int currentRating;
    private RatingBar mSavedRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_tool, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        tool = (Tool) bundle.getSerializable("tool");
        //Todo: set to tool rating saved in database
        mSavedRating.setRating(currentRating);
        mSubmitButton = (Button) view.findViewById(R.id.b_submit_rating);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LinearLayout linearLayout = new LinearLayout(getActivity());
                mToolRating = new RatingBar(getActivity());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );


                //Todo: adjust centering if possible
                lp.gravity = Gravity.CENTER;

                mToolRating.setLayoutParams(lp);
                mToolRating.setNumStars(5);
                mToolRating.setStepSize(1);
                mToolRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));


                linearLayout.addView(mToolRating);

                builder.setView(linearLayout);

               mToolRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        //Todo: adjust rating to place into system
                        /*currentRating = Integer.valueOf(currentRating ) */
                        System.out.println("Rated val:"+v);
                    }
                });


                builder.setMessage("How would you rate this tool?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.create();
                builder.show();

            }
        });
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

        mCardInfo = (Button) view.findViewById(R.id.b_card_info);
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

        setToolValues();

        return view;
    }

    private void setToolValues() {
        mToolName.setText(tool.getName());
        mToolYear.setText(mToolYear.getText() + Integer.toString(tool.getYear()));
        mToolBrand.setText(mToolBrand.getText() + Brand.getBrandByPk(db, tool.getBrand()).getName());
        mToolModel.setText(mToolModel.getText() + tool.getModel());
    }

    private void deleteTool() {
        tool.deleteTool(db, tool.getId());
        Toast.makeText(getActivity(), "Tool deleted", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }


}
