package com.mars.socket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Switch_main extends AppCompatActivity
{

    private Button btn_server;
    private Button btn_client;
    private TextView local_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_switch_main );
        btn_server=(Button)findViewById ( R.id.btn_server );
        btn_client=(Button)findViewById ( R.id.btn_client );
        local_ip = (TextView)findViewById(R.id.local_ip);


        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        local_ip.setTextColor(Color.BLUE);
        local_ip.setText("本机IP： " + ip);
        btn_server.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                startActivity ( new Intent ( Switch_main.this,MainSocketServer.class ) );
            }
        } );
        btn_client.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                startActivity ( new Intent ( Switch_main.this,MainSocketClient.class ) );
            }
        } );

    }

    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            Log.d("TIEJIANG", "WifiPreference: " + ex.toString());
        }
        return null;
    }
}
