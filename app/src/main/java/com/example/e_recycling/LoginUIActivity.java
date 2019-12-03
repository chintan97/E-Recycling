// An activity for Login UI
package com.example.e_recycling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.e_recycling.Fragment.*;

public class LoginUIActivity extends MainActivity {

    TextView text_register_now;
    Button button_login;
    EditText edit_email, edit_password;
    RadioGroup radioGroup;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;
    SharedPreferences sharedPreferences;

    String str_email = "", str_password = "", str_mode = ""; // mode --> U = user, R = recycler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

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

    // The below function is implemented to add an event which will disappear the keyboard if
    // the user clicks anywhere except edit views
    // https://stackoverflow.com/a/54308582/8243992
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void SubmitLogin()
    {
        str_email = edit_email.getText().toString();
        str_password = edit_password.getText().toString();

        // Validations before submitting the data
        if(str_email.isEmpty())
        {
            Toast.makeText(this, "Please enter Email Address", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isValidEmail(str_email)) {
            Toast.makeText(this, "Enter valid Email Address", Toast.LENGTH_LONG).show();
            return;
        }

        if(str_password.isEmpty())
        {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        if(str_mode.isEmpty())
        {
            Toast.makeText(this, "Please select Recycler or User", Toast.LENGTH_LONG).show();
            return;
        }

        cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{str_email, str_password});
        if (cursor != null) {
            if (cursor.getCount() > 0) {

                // Insert data into SharePreferences
                sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                sharedPreferences.edit().putString("email", str_email).apply();
                sharedPreferences.edit().putString("userType", str_mode).apply();

                Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
                intent.putExtra("email",str_email);
                intent.putExtra("userType",str_mode);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Login error. Username or password not matched.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
