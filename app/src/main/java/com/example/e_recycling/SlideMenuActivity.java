package com.example.e_recycling;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class SlideMenuActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    View headerLayout;
    Toolbar toolbar;
    TextView text_email;
    String userType = "", str_name = "";

    String[] userMenu = {"My Post", "My Profile", "Add Post", "Bidded items", "Contact us", "Logout"};
    String[] recyclerMenu = {"Buy Items", "My Profile", "My Bids", "Contact us", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);

        Intent intent = getIntent();
        str_name = intent.getStringExtra("email");
        userType = intent.getStringExtra("userType");

        toolbar = findViewById(R.id.toolbar_slide_menu);
        if (userType.equals("U")) {
            toolbar.setTitle("Add Post");
        } else {
            toolbar.setTitle("Buy Items");
        }
        setSupportActionBar(toolbar);

        prepareViews();
        initializeViews();
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
        if (userType.equals("R")) {
            for (int i = 0; i < recyclerMenu.length; i++) {
                menu.add(recyclerMenu[i]);
            }
        } else if (userType.equals("U")) {
            for (int i = 0; i < userMenu.length; i++) {
                menu.add(userMenu[i]);
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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

        if (strMenu.equals("My Post")) {
        } else if (strMenu.equals("My Profile")) {
        } else if (strMenu.equals("Add Post")) {
        } else if (strMenu.equals("Bidded items")) {
        } else if (strMenu.equals("Buy items")) {
        } else if (strMenu.equals("My bids")) {
        } else if (strMenu.equals("Contact us")) {
        } else if (strMenu.equals("Logout")) {
            startActivity(new Intent(getApplicationContext(),LoginUIActivity.class));
            finish();
        }
        toolbar.setTitle(strMenu);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
