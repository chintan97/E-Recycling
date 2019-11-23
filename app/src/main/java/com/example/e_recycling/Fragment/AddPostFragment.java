package com.example.e_recycling.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_recycling.R;

public class AddPostFragment extends FragmentMaster {

    View view;

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }

    @Override
    public void prepareViews(View view) {

    }

    @Override
    public void initializeViews() {

    }
}
