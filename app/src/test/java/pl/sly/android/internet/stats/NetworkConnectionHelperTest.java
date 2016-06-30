package pl.sly.android.internet.stats;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.sly.android.internet.stats.helper.NetworkConnectionHelper;

@RunWith(MockitoJUnitRunner.class)
public class NetworkConnectionHelperTest {
    @Mock
    private Context mockContext;
    @Mock
    private ConnectivityManager mockConnectivityManager;
    @Mock
    private NetworkInfo mockNetworkInfo;

    private NetworkConnectionHelper networkConnectionHelper;

    @Before
    public void setUp() {
        Mockito.when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager);
        networkConnectionHelper = new NetworkConnectionHelper(mockContext);
    }

    @Test
    public void testIsConnectedSuccess() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.TRUE);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnected();

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void testIsConnectedFail() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.FALSE);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnected();

        //then
        Assert.assertFalse(result);
    }

    @Test
    public void testIsConnectedOnMobileSuccess() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnectedOnMobile();

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void testIsConnectedOnMobileFail() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.FALSE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnectedOnMobile();

        //then
        Assert.assertFalse(result);
    }

    @Test
    public void testIsConnectedOnWifiSuccess() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnectedOnWifi();

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void testIsConnectedOnWifiFail() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.FALSE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        boolean result = networkConnectionHelper.isConnectedOnWifi();

        //then
        Assert.assertFalse(result);
    }

    @Test
    public void testNetworkInfo() {
        //given
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        NetworkInfo result = networkConnectionHelper.getNetworkInfo();

        //then
        Assert.assertNotNull(result);
    }

    @Test
    public void testBuildEventForWifi() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.isAvailable()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        NetworkConnectionHelper.Event result = networkConnectionHelper.buildEvent();

        //then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isConnected());
        Assert.assertFalse(result.isConnectedOnMobile());
        Assert.assertTrue(result.isConnectedOnWifi());
        Assert.assertNotNull(result.getNetworkInfo());
    }

    @Test
    public void testBuildEventForMobile() {
        //given
        Mockito.when(mockNetworkInfo.isConnected()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.isAvailable()).thenReturn(Boolean.TRUE);
        Mockito.when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
        Mockito.when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);

        //when
        NetworkConnectionHelper.Event result = networkConnectionHelper.buildEvent();

        //then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isConnected());
        Assert.assertTrue(result.isConnectedOnMobile());
        Assert.assertFalse(result.isConnectedOnWifi());
        Assert.assertNotNull(result.getNetworkInfo());
    }
}