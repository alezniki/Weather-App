package com.alezniki.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by nikola on 7/30/17.
 */

public class NetworkConnectionReceiver extends BroadcastReceiver {
    public static final String NOTIFY_NETWORK_CHANGE = "NOTIFY_NETWORK_CHANGE";
    public static final String EXTRA_IS_CONNECTED = "EXTRA_IS_CONNECTED";

    private NetworkConnection connection;

    @Override
    public void onReceive(Context context, Intent intent) {
        connection = new NetworkConnection(context);

        Intent localIntent = new Intent(NOTIFY_NETWORK_CHANGE);
        localIntent.putExtra(EXTRA_IS_CONNECTED, connection.isNetworkConnected());
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);

    }


}
