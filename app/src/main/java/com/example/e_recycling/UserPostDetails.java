// An activity to show user post
package com.example.e_recycling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UserPostDetails extends AppCompatActivity {
    Button delete_post_button;
    SharedPreferences sharedPreferences;
    String userEmail, userMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fetch user data from SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "NOT_FOUND");
        userMode = sharedPreferences.getString("userType", "NOT_FOUND");

        // Redirect to Slide Meny activity after deleting the post
        delete_post_button = findViewById(R.id.delete_post_button);
        delete_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
                intent.putExtra("email",userEmail);
                intent.putExtra("userType",userMode);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}
