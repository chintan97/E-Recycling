package com.example.e_recycling.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_recycling.R;
import com.example.e_recycling.SlideMenuActivity;

public class OpenPost extends FragmentMaster {

    View view;
    SharedPreferences sharedPreferences;
    String userEmail, userMode;
    Button delete_post_button;
    public OpenPost() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "NOT_FOUND");
        userMode = sharedPreferences.getString("userType", "NOT_FOUND");
    }

    @Override
    public void prepareViews(View view) {
        delete_post_button = view.findViewById(R.id.delete_post_button);
        delete_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlideMenuActivity.class);
                intent.putExtra("email",userEmail);
                intent.putExtra("userType",userMode);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initializeViews() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_user_post, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }
}
