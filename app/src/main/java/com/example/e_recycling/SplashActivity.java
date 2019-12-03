// A landing page activity
package com.example.e_recycling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // Login page will be the landing page
                Intent i = new Intent(getApplicationContext(), LoginUIActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
