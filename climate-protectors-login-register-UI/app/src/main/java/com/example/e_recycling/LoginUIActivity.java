package com.example.e_recycling;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginUIActivity extends MainActivity {

    Button button_login;
    EditText edit_email, edit_password;
    RadioGroup radioGroup;

    //suman
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;



    String str_email = "", str_password = "", str_mode = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);

        //suman
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();


        prepareViews();
        initializeViews();
    }

    public void prepareViews() {

        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        button_login = findViewById(R.id.button_login);
    }

    public void initializeViews() {

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
            Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidEmail(str_email)) {
            Toast.makeText(this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( !str_email.equals("admin@gmail.com")) {
            Toast.makeText(this, "Account does not exists.", Toast.LENGTH_SHORT).show();
            return;
        }



        if(str_password.isEmpty())
        {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!str_password.equals("admin"))
        {
            Toast.makeText(this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        }

                Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
                intent.putExtra("email",str_email);
                intent.putExtra("userType",str_mode);
                startActivity(intent);
                 finish();
                Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();


    }
}
