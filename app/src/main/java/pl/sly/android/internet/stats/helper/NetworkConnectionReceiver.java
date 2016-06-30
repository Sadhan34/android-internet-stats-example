package pl.sly.android.internet.stats.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.sly.android.internet.stats.MainApp;
import timber.log.Timber;

public class NetworkConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("Received network change message.");
        NetworkConnectionHelper networkConnectionHelper = new NetworkConnectionHelper(context);
        NetworkConnectionHelper.Event event = networkConnectionHelper.buildEvent();
        MainApp.getBus().post(event);
    }
}
