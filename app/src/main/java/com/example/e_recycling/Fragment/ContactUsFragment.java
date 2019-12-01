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


public class ContactUsFragment extends FragmentMaster {

    View view;
    String userEmail, userMode;
    SharedPreferences sharedPreferences;
    Button upload_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch user data
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "NOT_FOUND");
        userMode = sharedPreferences.getString("userType", "NOT_FOUND");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }

    @Override
    public void prepareViews(View view) {
        upload_button = view.findViewById(R.id.submit_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
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
}
