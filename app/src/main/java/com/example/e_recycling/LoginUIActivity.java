package com.example.e_recycling;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginUIActivity extends MainActivity {

    TextView text_register_now;
    Button button_login;
    EditText edit_email, edit_password;
    RadioGroup radioGroup;

    String str_email = "", str_password = "", str_mode = ""; // mode --> U = user, R = recycler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);

        prepareViews();
        initializeViews();
    }

    public void prepareViews() {
        text_register_now = findViewById(R.id.click_new_user);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        radioGroup = findViewById(R.id.radio_group);
        button_login = findViewById(R.id.button_login);
    }

    public void initializeViews() {
        text_register_now.setText(Html.fromHtml("<U><font color='#53c4f7'>Register Now</font></U>"));
        text_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterUIActivity.class));
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.choice_recycler) {
                    str_mode = "R";
                } else if(checkedId == R.id.choice_user){
                    str_mode = "U";
                }
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLogin();
            }
        });
    }

    public void SubmitLogin()
    {
        str_email = edit_email.getText().toString();
        str_password = edit_password.getText().toString();

        if(str_email.isEmpty())
        {
            Toast.makeText(this, "Please enter Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(str_email)) {
            Toast.makeText(this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_password.isEmpty())
        {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_mode.isEmpty())
        {
            Toast.makeText(this, "Please select Recycler or User", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
        intent.putExtra("email",str_email);
        intent.putExtra("userType",str_mode);
        startActivity(intent);
        finish();
    }
}
