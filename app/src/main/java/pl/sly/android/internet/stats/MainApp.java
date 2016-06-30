package pl.sly.android.internet.stats;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import timber.log.Timber;

public class MainApp extends Application {
    private static Bus mBus = new Bus(ThreadEnforcer.MAIN);

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    public static Bus getBus() {
        return mBus;
    }
}
