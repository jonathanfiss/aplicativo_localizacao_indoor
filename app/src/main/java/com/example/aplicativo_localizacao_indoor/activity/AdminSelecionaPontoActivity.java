package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

public class AdminSelecionaPontoActivity extends AppCompatActivity {
//    final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seleciona_ponto);


    }
}
