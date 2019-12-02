// A fragment to show bidded items to the recycler
package com.example.e_recycling.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_recycling.R;

public class BiddedItemsFragment extends FragmentMaster {

    View view;

    public BiddedItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void prepareViews(View view) {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bidded_items, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }
}
