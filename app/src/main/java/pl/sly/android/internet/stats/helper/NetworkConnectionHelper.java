package pl.sly.android.internet.stats.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionHelper {
    private ConnectivityManager mConnectivityManager;

    public NetworkConnectionHelper(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isConnectedOnMobile() {
        return inConnectedOnNetwork(ConnectivityManager.TYPE_MOBILE);
    }

    public boolean isConnectedOnWifi() {
        return inConnectedOnNetwork(ConnectivityManager.TYPE_WIFI);
    }

    public NetworkInfo getNetworkInfo() {
        return mConnectivityManager.getActiveNetworkInfo();
    }

    public Event buildEvent() {
        return new Event(isConnected(), isConnectedOnMobile(), isConnectedOnWifi(), mConnectivityManager.getActiveNetworkInfo());
    }

    private boolean inConnectedOnNetwork(int networkType) {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == networkType;
    }

    public class Event {
        private boolean isConnected;
        private boolean isConnectedOnMobile;
        private boolean isConnectedOnWifi;
        private NetworkInfo networkInfo;

        public Event(boolean isConnected, boolean isConnectedOnMobile, boolean isConnectedOnWifi, NetworkInfo networkInfo) {
            this.isConnected = isConnected;
            this.isConnectedOnMobile = isConnectedOnMobile;
            this.isConnectedOnWifi = isConnectedOnWifi;
            this.networkInfo = networkInfo;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public boolean isConnectedOnMobile() {
            return isConnectedOnMobile;
        }

        public boolean isConnectedOnWifi() {
            return isConnectedOnWifi;
        }

        public NetworkInfo getNetworkInfo() {
            return networkInfo;
        }
    }
}