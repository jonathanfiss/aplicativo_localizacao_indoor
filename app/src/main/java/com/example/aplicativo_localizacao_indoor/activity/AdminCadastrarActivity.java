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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Ponto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AdminCadastrarActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastrar);


        final TextView tvNomeRede, tvMac, tvLatitude, tvLongitude, tvAltura, tvVelocidade;
        final AutoCompleteTextView autoCTexview;

        if (verificaPermissao()) {
            Ponto ponto = new Ponto();

//            tvNomeRede = findViewById(R.id.tvNomeRede);
//            tvMac = findViewById(R.id.tvMac);
//            tvLatitude = findViewById(R.id.tvLatitude);
//            tvLongitude = findViewById(R.id.tvLongitude);
//            tvAltura = findViewById(R.id.tvAltura);
//            tvVelocidade = findViewById(R.id.tvVelocidade);
//
            autoCTexview = findViewById(R.id.acteCadastrar);

            //auto complete do botão ambiente
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
            autoCTexview.setAdapter(adapter);

            enableWiFi();//criar verificação

            localizacao(ponto);
            //concatenando dados
//            tvLatitude.setText(String.format("%s %s", getString(R.string.cood_lat), String.valueOf(ponto.getCoodLatitude())));
//            tvLongitude.setText(String.format("%s %s", getString(R.string.cood_long), String.valueOf(ponto.getCoodLongitude())));
//            tvAltura.setText(String.format("%s %s", getString(R.string.cood_alt), String.valueOf(ponto.getCoodAltura())));
//            tvVelocidade.setText(String.format("%s %s", getString(R.string.cood_vel), String.valueOf(ponto.getCoodVelocidade())));

            infoWifi(ponto);

            //concatenando dados
//            tvNomeRede.setText(String.format("%s %s", getString(R.string.nome_rede), ponto.getSsid()));
//            tvMac.setText(ponto.getBssid());

//            listView = (ExpandableListView)findViewById(R.id.lvExp);
//            initData(ponto);
//            listAdapter = new com.example.aplicativo_localizacao_indoor.adapter.ExpandableListAdapter(this, listDataHeader, listHash);
//            listView.setAdapter(listAdapter);



        }
    }

//    private void initData(Ponto ponto) {
//        listDataHeader = new ArrayList<>();
//        listHash = new HashMap<>();
//
//        listDataHeader.add("Informações");
//
//        List<String> info = new ArrayList<>();
//        info.add(ponto.getSsid());
//        info.add(ponto.getBssid());
//
//        listHash.put(listDataHeader.get(0),info);
//    }

    private void localizacao(final Ponto ponto) {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {

                public void onLocationChanged(Location location) {
                    ponto.setCoodLatitude(location.getLatitude());
//                    TextView tvLatitude = findViewById(R.id.tvLatitude);
//                    tvLatitude.setText(String.format("%s %s", getString(R.string.cood_lat), String.valueOf(ponto.getCoodLatitude())));
                    ponto.setCoodLongitude(location.getLongitude());
                    ponto.setCoodAltura(location.getAltitude());
                    ponto.setCoodVelocidade(location.getSpeed());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    private boolean verificaPermissao() {
        //verifica se o aplicativo tem permissão para utilizar a localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //verifica se permissão ja foi negada alguma vez para utilizar a localização
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //adiciona um título e uma mensagem
                builder.setTitle("Permissão");
                builder.setMessage("Para você poder utilizar o sistema é necessario a permissão a localização");
                //adiciona os botões
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AdminCadastrarActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        }//retorna true se tiver tudo certo
        return true;
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

    private void infoWifi(Ponto ponto){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ponto.setBssid(wifiInfo.getBSSID());
        ponto.setSsid(wifiInfo.getSSID());
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