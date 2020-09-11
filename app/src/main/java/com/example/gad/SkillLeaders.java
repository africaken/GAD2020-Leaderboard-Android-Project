package com.example.gad;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillLeaders#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SkillLeaders extends Fragment {

    // TextView that is displayed when the list is empty
    private TextView connectionView;

    /**
     * Progress Indicator that is displayed when fetching topSkillLearner
     */
    private ProgressBar progressIndicator;

    private RecyclerView top_skill_view;
    private View mView;

    private SkillRecyclerAdapter mAdapter;


    public SkillLeaders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_skill_leaders, container, false);
        top_skill_view = (RecyclerView) mView.findViewById(R.id.list_top_learner);
        top_skill_view.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ArrayList<TopSkill> topSkills = new ArrayList<>();

        mAdapter = new SkillRecyclerAdapter(topSkills);
        top_skill_view.setAdapter(mAdapter);
        top_skill_view.setItemAnimator(new DefaultItemAnimator());


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                mView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            try {
                URL learnerUrl = SkillUtil.buildUrl("skilliq");
                new SkillLeaders.topSkillLearnerQueryTask().execute(learnerUrl);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }

        } else {
            // If there is no network connection
            progressIndicator = (ProgressBar) mView.findViewById(R.id.loading_spinner);
            progressIndicator.setVisibility(View.INVISIBLE);
            connectionView = (TextView) mView.findViewById(R.id.connection_view);
            connectionView.setText(getString(R.string.connection_absent));
        }
        return mView;

    }

    public class topSkillLearnerQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = SkillUtil.makeHttpRequest(searchUrl);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute (String result) {

            connectionView = (TextView) mView.findViewById(R.id.connection_view);
            progressIndicator = (ProgressBar) mView.findViewById(R.id.loading_spinner);
            progressIndicator.setVisibility(View.INVISIBLE);
            if (result == null) {
                top_skill_view.setVisibility(View.INVISIBLE);
                connectionView.setText(getString(R.string.top_learner_absent));
            } else {
                top_skill_view.setVisibility(View.VISIBLE);
                connectionView.setVisibility(View.INVISIBLE);
            }
            ArrayList<TopSkill> topSkills = SkillUtil.extractFeatureFromJson(result);
            mAdapter = new SkillRecyclerAdapter(topSkills);
            top_skill_view.setAdapter(mAdapter);

        }

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            progressIndicator = (ProgressBar) mView.findViewById(R.id.loading_spinner);
            progressIndicator.setIndeterminate(true);
            progressIndicator.setVisibility(View.VISIBLE);
        }
    }
}