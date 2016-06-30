package pl.sly.android.internet.stats.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sly.android.internet.stats.MainApp;
import pl.sly.android.internet.stats.R;
import pl.sly.android.internet.stats.helper.NetworkConnectionHelper;

public class MainActivity extends AppCompatActivity {
    private static final String BUNDLE_LAST_RELOAD = "lastReload";
    private NetworkConnectionHelper mNetworkConnectionHelper;

    @BindView(R.id.isConnected1_textview)
    TextView isConnected1TextView;

    @BindView(R.id.isConnectedOnMobile1_textview)
    TextView isConnectedOnMobile1TextView;

    @BindView(R.id.isConnectedOnWifi1_textview)
    TextView isConnectedOnWifi1TextView;

    @BindView(R.id.isConnected2_textview)
    TextView isConnected2TextView;

    @BindView(R.id.isConnectedOnMobile2_textview)
    TextView isConnectedOnMobile2TextView;

    @BindView(R.id.isConnectedOnWifi2_textview)
    TextView isConnectedOnWifi2TextView;

    @BindView(R.id.lastReload_textview)
    TextView lastReloadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNetworkConnectionHelper = new NetworkConnectionHelper(this);
        reloadNetworkStats();
        onLoadInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_LAST_RELOAD, lastReloadTextView.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        MainApp.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MainApp.getBus().unregister(this);
    }

    @OnClick(R.id.reload_button)
    public void onClickReloadButton() {
        reloadNetworkStats();
    }

    @Subscribe
    public void onNetworkConnectionHelperEvent(NetworkConnectionHelper.Event event) {
        Toast.makeText(this, "NetworkConnectionReceiver reloaded", Toast.LENGTH_SHORT).show();
        reloadReceiverNetworkStats(event);
    }

    private void reloadNetworkStats() {
        isConnected1TextView.setText(mNetworkConnectionHelper.isConnected() + "");
        isConnectedOnMobile1TextView.setText(mNetworkConnectionHelper.isConnectedOnMobile() + "");
        isConnectedOnWifi1TextView.setText(mNetworkConnectionHelper.isConnectedOnWifi() + "");
    }

    private void reloadReceiverNetworkStats(NetworkConnectionHelper.Event event) {
        isConnected2TextView.setText(event.isConnected() + "");
        isConnectedOnMobile2TextView.setText(event.isConnectedOnMobile() + "");
        isConnectedOnWifi2TextView.setText(event.isConnectedOnWifi() + "");
        lastReloadTextView.setText(generateTimeStamp());
    }

    private String generateTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        return "Last reloaded: " + simpleDateFormat.format(new Date());
    }

    private void onLoadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_LAST_RELOAD)) {
            lastReloadTextView.setText(savedInstanceState.getString(BUNDLE_LAST_RELOAD));
        }
    }
}