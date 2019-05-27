package com.example.aplicativo_localizacao_indoor.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

public class AdminCadastraPontoActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto);
        ListView lvPontosRef = findViewById(R.id.lv_pontos_ref);
        lvPontosRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        if (wifiManager.startScan()) {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            Log.d("listscan",wifiManager.getScanResults().toString());
            for(ScanResult result: scanResults){
                WiFiDetalhes wiFiDetalhes = new WiFiDetalhes();
                wiFiDetalhes.setBSSID(result.BSSID);
                wiFiDetalhes.setSSID(result.SSID);
                wiFiDetalhes.setWiFiSignal(result.frequency);
                wiFiDetalhes.setCapabilities(result.capabilities);
                AppSetup.wiFiDetalhes.add(wiFiDetalhes);
            }
            lvPontosRef.setAdapter(new PontoReferenciaAdapter(AdminCadastraPontoActivity.this, AppSetup.wiFiDetalhes));
        }



        verificaPermissao();
        wifiManager.startScan();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiinfo", String.valueOf(wifiInfo.getRssi()));
        Log.d("wifiinfo", String.valueOf(wifiInfo.getBSSID()));
        Log.d("wifiinfo", String.valueOf(wifiInfo.getSSID()));
        Log.d("wifiinfo", String.valueOf(wifiInfo.getNetworkId()));
        Log.d("wifiinfo", String.valueOf(wifiInfo.getMacAddress()));
        try {
            if (wifiManager.startScan()) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                Log.d("listscan",wifiManager.getScanResults().toString());
            }
        } catch (Exception e) {
            // critical error: do not die
        }

//        tvLatitude.setText(String.format("%s %s", getString(R.string.cood_lat), String.valueOf(ponto.getCoodLatitude())));
//            tvLongitude.setText(String.format("%s %s", getString(R.string.cood_long), String.valueOf(ponto.getCoodLongitude())));
    }

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
                        ActivityCompat.requestPermissions(AdminCadastraPontoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
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
}
