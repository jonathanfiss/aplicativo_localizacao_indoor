package com.example.aplicativo_localizacao_indoor.activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Scanner;


public class AdminCadastrarActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastrar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                callDialog("Para você poder utilizar o sistema é necessario a permissão a localização", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                //chama o modal para caso no se haja a permissão
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        } else {
            enableWiFi();
            Log.d("teste",getSSID());
            Log.d("test2",getBSSID());
                try {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    LocationListener locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            Log.d("testlat", String.valueOf(location.getLatitude()));
                            Log.d("testlong", String.valueOf(location.getLongitude()));
                            Log.d("testalt", String.valueOf(location.getAltitude()));
                            Log.d("testvel", String.valueOf(location.getSpeed()));
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) { }

                        public void onProviderEnabled(String provider) { }

                        public void onProviderDisabled(String provider) { }
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }catch(SecurityException ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }



    private void callDialog(String message, final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //adiciona um título e uma mensagem
        builder.setTitle("Permissão");
        builder.setMessage(message);
        //adiciona os botões
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(AdminCadastrarActivity.this, permissions, REQUEST_PERMISSIONS_CODE);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
    private void enableWiFi() {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } catch (Exception e) {

        }
    }

    private String getBSSID() {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getBSSID();
        } catch (Exception e) {
            return "false";
        }
    }

    private String getSSID() {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getSSID();
        } catch (Exception e) {
            return "false";
        }
    }


//    public static String getMacAddr() {
//        try {
//            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface nif : all) {
//                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
//
//                byte[] macBytes = nif.getHardwareAddress();
//                if (macBytes == null) {
//                    return "";
//                }
//
//                StringBuilder res1 = new StringBuilder();
//                for (byte b : macBytes) {
//                    res1.append(Integer.toHexString(b & 0xFF) + ":");
//                }
//
//                if (res1.length() > 0) {
//                    res1.deleteCharAt(res1.length() - 1);
//                }
//                return res1.toString();
//            }
//        } catch (Exception ex) {
//            //handle exception
//        }
//        return "";
//    }
}
//Armazenar coordenadas do local utilizando o GPS
//Adicionar botao para selecionar o ponto de referencia ou selecionar automaticamente pela intencidade de sinal
//selecioanr no mapa o lucal atual
//Adicionar informações sobre o local nova janela ou fazer tudo na mesma pagina
//botao de cadatrar