package com.example.e_recycling.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class FragmentMaster extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void prepareViews(View view);

    public abstract void initializeViews();

    protected boolean hasNetwork(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
