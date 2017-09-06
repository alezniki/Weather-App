package com.alezniki.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nikola on 7/30/17.
 */

public class NetworkConnection {

    private Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    // Checking the Network NetworkConnection
    public boolean isNetworkConnected() {

        // 1. Retrieves an instance of the ConnectivityManager class from the current application context.
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // 2.Retrieves an instance of the NetworkInfo class that represents the current network connection.
        // This will be null if no network is available.
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // 3. Check if there is an available network connection and the device is connected.
        // Should check null because in airplane mode it will be null
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
