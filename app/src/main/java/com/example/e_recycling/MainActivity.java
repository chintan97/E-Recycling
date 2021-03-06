package com.example.e_recycling;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

public class MainActivity extends AppCompatActivity {

    public static final int ATTACHMENT_CHOICE_CAMERA = 1;
    public static final int ATTACHMENT_CHOICE_GALLERY = 2;
    public static final int REQUEST_PERMISSION = 200;

    private TextView mTextMessage;
    private ImageView logoImage;

    // To check if the network is available
    protected boolean hasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    // To check if the email is valid
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // To check if the phone is valid
    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent redirect;
            switch (item.getItemId()) {
                case R.id.navigation_login:   // Redirect to login page
                    redirect = new Intent(getApplicationContext(), LoginUIActivity.class);
                    startActivity(redirect);
                    mTextMessage.setText("Login");
                    return true;
                case R.id.navigation_admin:   // Redirect to admin page
                    mTextMessage.setText("Admin");
                    return true;
            }
            return false;
        }
    };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB debugging tool// chrome://inspect/#devices
        Stetho.initializeWithDefaults(this);

        mTextMessage = (TextView) findViewById(R.id.app_name);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        logoImage = (ImageView) findViewById(R.id.imageLogo);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(400,400);
    }

}
