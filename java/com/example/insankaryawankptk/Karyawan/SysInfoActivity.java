package com.example.insankaryawankptk.Karyawan;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import com.example.insankaryawankptk.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by muhjaury on 7/17/2019
 * Email : muhjaury@gmail.com
 **/

public class SysInfoActivity extends AppCompatActivity {

    TextView sys_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_info);

        //Wifi Manager
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            String SSID = info.getSSID();
            String BSSID = info.getBSSID();
            @SuppressWarnings("Unchecked")
            String IP = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        //Wifi Manager

        sys_tv = (TextView)findViewById(R.id.sysInfo);

        sys_tv.setText(
                "PRODUCT : " + Build.PRODUCT + "\n"
                + "BRAND : " + Build.BRAND + "\n"
                + "DEVICE : " + Build.DEVICE + "\n"
                + "BOARD : " + Build.BOARD + "\n"
                + "DISPLAY : " + Build.DISPLAY + "\n"
                + "FINGERPRINT : " + Build.FINGERPRINT + "\n"
                + "HARDWARE : " + Build.HARDWARE + "\n"
                + "HOST : " + Build.HOST + "\n"
                + "ID : " + Build.ID + "\n"
                + "MANUFACTURE : " + Build.MANUFACTURER + "\n"
                + "MODEL : " + Build.MODEL + "\n"
                + "TAGS : " + Build.TAGS + "\n"
                + "TYPE : " + Build.TYPE + "\n"
                + "USER : " + Build.USER + "\n"
                + "TIME : " + Build.TIME + "\n"
                + "\n"
                + "SSID : " + SSID + "\n"
                + "IP Address : " + IP + "\n"
                + "MAC Device : " + getMacAddr() + "\n"
                + "MAC Access Point : " + BSSID
        );
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
