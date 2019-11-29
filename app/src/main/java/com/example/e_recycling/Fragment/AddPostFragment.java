package com.example.e_recycling.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.e_recycling.R;
import com.example.e_recycling.adapter.PlaceAutoSuggestAdapter;

public class AddPostFragment extends FragmentMaster {

    View view;
    AutoCompleteTextView edit_location;

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
        Log.i("Main", "Message");
        edit_location = view.findViewById(R.id.edit_location);
        edit_location.setAdapter(new PlaceAutoSuggestAdapter(getContext(),android.R.layout.simple_list_item_1));
    }

    @Override
    public void initializeViews() {

    }
}
