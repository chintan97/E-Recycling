package com.example.e_recycling;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUIActivity extends MainActivity {
    EditText edit_name, edit_email, edit_phone, edit_password,
            edit_confirm_password;
    RadioGroup radioGroup;
    Button button_register;
    String str_name = "", str_email = "", str_phone = "",
            str_password = "", str_confirm_password = "", str_mode = ""; //mode --> U = user, R = recycler

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui);

        openHelper = new DatabaseHelper(this);

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
        db = openHelper.getWritableDatabase();
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

        if (!isValidPassword(str_password)) {
            Toast.makeText(this, "Enter Strong Password. It must have at least one number, capital character, small character and special character.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_mode.isEmpty())
        {
            Toast.makeText(this, "Select Recycler or User", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=?", new String[]{str_email});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    Toast.makeText(getApplicationContext(), "Use is already registered,Please Login.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginUIActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        }

        insertData(str_name,str_phone,str_email,str_password);
        Toast.makeText(RegisterUIActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginUIActivity.class));
        finish();
    }

    public void insertData(String fname,String fPhone,String fGmail,String fPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,fname);
        contentValues.put(DatabaseHelper.COL_3,fPhone);
        contentValues.put(DatabaseHelper.COL_4,fGmail);
        contentValues.put(DatabaseHelper.COL_5,fPassword);
        // contentValues.put(DatabaseHelper.COL_6,fMode);

        long id = db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginUIActivity.class));
        finish();
    }
}
