package com.example.e_recycling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterUIActivity extends MainActivity {
    EditText edit_name, edit_email, edit_phone, edit_password,
            edit_confirm_password;
    RadioGroup radioGroup;
    Button button_register;
    String str_name = "", str_email = "", str_phone = "",
            str_password = "", str_confirm_password = "", str_mode = ""; //mode --> U = user, R = recycler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui);

        prepareViews();
        initializeViews();
    }

    public void prepareViews() {

        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        edit_password = findViewById(R.id.edit_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);

        radioGroup = findViewById(R.id.radio_group);

        button_register = findViewById(R.id.button_register);

    }

    public void initializeViews() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.choice_recycler) {
                    str_mode = "R";
                } else if (checkedId == R.id.choice_user) {
                    str_mode = "U";
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitRegistration();
            }
        });

    }

    public void SubmitRegistration() {
        str_name = edit_name.getText().toString();
        str_email = edit_email.getText().toString();
        str_phone = edit_phone.getText().toString();
        str_password = edit_password.getText().toString();
        str_confirm_password = edit_confirm_password.getText().toString();

        if (str_name.isEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (str_email.isEmpty()) {
            Toast.makeText(this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(str_email)) {
            Toast.makeText(this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (str_phone.isEmpty()) {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhone(str_phone)) {
            Toast.makeText(this, "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (str_password.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (str_confirm_password.isEmpty()) {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!str_password.equals(str_confirm_password)) {
            Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_mode.isEmpty())
        {
            Toast.makeText(this, "Select Recycler or User", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginUIActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginUIActivity.class));
        finish();
    }
}
