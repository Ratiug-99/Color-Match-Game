package com.ratiug.dev.colormatchgame;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetUtils {
    public boolean isOnline(Context ct) {
        ConnectivityManager cm =
                (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }else{
             Toast.makeText(ct, R.string.chek_internet_connection, Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
