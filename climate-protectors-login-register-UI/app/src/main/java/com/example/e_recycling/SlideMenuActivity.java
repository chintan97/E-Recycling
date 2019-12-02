package com.example.e_recycling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SlideMenuActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    View headerLayout;
    Toolbar toolbar;
    TextView text_email;
    String userType = "", str_name = "";


    String[] recyclerMenu = {"Pending Requests Of Recyclers","Accepted Requests","Rejected Requests", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);

        Intent intent = getIntent();
        str_name = intent.getStringExtra("email");
        userType = intent.getStringExtra("userType");

        toolbar = findViewById(R.id.toolbar_slide_menu);
        if (userType.equals("admin")) {
            toolbar.setTitle("Pending Requests Of Recyclers");
        }
        setSupportActionBar(toolbar);

        prepareViews();
        initializeViews();
    }

    // https://stackoverflow.com/a/54308582/8243992
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void prepareViews() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        text_email = headerLayout.findViewById(R.id.textView_header_email);
    }

    public void initializeViews() {

        text_email.setText(str_name);

        Menu menu = navigationView.getMenu();
        if (userType.equals("admin")) {
            for (int i = 0; i < recyclerMenu.length; i++) {
                menu.add(recyclerMenu[i]);
            }
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if (userType.equals("admin")) {
            setFragment(new RecyclerListFragment());
        }
    }

    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        } else {
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(getApplicationContext(), LoginUIActivity.class));
            finish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String strMenu = item.toString();

        Log.d("Menu", strMenu);

         if (strMenu.equals("Pending Requests Of Recyclers")) {
            setFragment(new RecyclerListFragment());
        }else if (strMenu.equals("Accepted Requests")) {
             setFragment(new RecyclerAcceptedListFragment());
         }else if (strMenu.equals("Rejected Requests")) {
             setFragment(new RecyclerRejectedListFragment());
         } else if (strMenu.equals("Logout")) {
            startActivity(new Intent(getApplicationContext(),LoginUIActivity.class));
            finish();
        }
        toolbar.setTitle(strMenu);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
