package com.toolshare.toolshare;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Ad;
import com.toolshare.toolshare.models.Request;

import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAllAdsByOwner;
import static com.toolshare.toolshare.models.Request.getAllRequestsByOwner;
import static com.toolshare.toolshare.models.Request.getAllRequestsByRequester;

public class RequestListFragment extends Fragment {
    private Bundle bundle;
    private DbHandler db;
    private List<Request> requests;
    private LinearLayout mRequestsLayout;
    private String mWhoseRequests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, null);

        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        mWhoseRequests = bundle.getString("whoseRequests");

        mRequestsLayout = (LinearLayout) view.findViewById(R.id.ll_requests);

        loadRequests();

        return view;
    }

    private void loadRequests() {
        List<Request> requests;
        if (mWhoseRequests.equals("myRequests")) {
            requests = getAllRequestsByRequester(db, bundle.getString("userEmail"));
        } else {
            requests = getAllRequestsByOwner(db, bundle.getString("userEmail"));
        }

        for (int i = 0; i < requests.size(); i++) {
            final Request request = requests.get(i);
            LayoutInflater li = LayoutInflater.from(getActivity());
            View requestView = li.inflate(R.layout.layout_request, null);

            TextView labelOwnerRequester = (TextView) requestView.findViewById(R.id.label_owner_requester);
            TextView ownerRequester = (TextView) requestView.findViewById(R.id.tv_owner_requester);
            TextView ad = (TextView) requestView.findViewById(R.id.tv_ad);
            TextView tool = (TextView) requestView.findViewById(R.id.tv_tool);
            CardView requestLink = requestView.findViewById(R.id.cv_request);

            if (mWhoseRequests.equals("myRequests")) {
                labelOwnerRequester.setText("Owner: ");
                ownerRequester.setText(request.getOwner().getFirstName() + " " + request.getOwner().getLastName());
            } else {
                labelOwnerRequester.setText("Requester: ");
                ownerRequester.setText(request.getRequester().getFirstName() + " " + request.getRequester().getLastName());
            }

            ad.setText(request.getAd().getTitle());
            tool.setText(request.getAd().getTool().getName());

            requestLink.setClickable(true);
            requestLink.setOnClickListener(new View.OnClickListener() {
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

            mRequestsLayout.addView(requestView);
        }
    }
}
