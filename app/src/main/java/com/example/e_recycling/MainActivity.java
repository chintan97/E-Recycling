package com.example.e_recycling;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

public class MainActivity extends AppCompatActivity {

    public static final int ATTACHMENT_CHOICE_CAMERA = 1;
    public static final int ATTACHMENT_CHOICE_GALLERY = 2;
    public static final int REQUEST_PERMISSION = 200;

    private TextView mTextMessage;

    protected boolean hasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

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
                case R.id.navigation_login:
                    redirect = new Intent(getApplicationContext(), LoginUIActivity.class);
                    startActivity(redirect);
                    mTextMessage.setText("Login");
                    return true;
                case R.id.navigation_admin:
                    mTextMessage.setText("Admin");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB debugging tool// chrome://inspect/#devices
        Stetho.initializeWithDefaults(this);

        mTextMessage = (TextView) findViewById(R.id.app_name);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
