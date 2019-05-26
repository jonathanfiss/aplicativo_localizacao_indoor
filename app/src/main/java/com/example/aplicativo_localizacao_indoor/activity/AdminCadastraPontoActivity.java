package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aplicativo_localizacao_indoor.R;

public class AdminCadastraPontoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto);

//        tvLatitude.setText(String.format("%s %s", getString(R.string.cood_lat), String.valueOf(ponto.getCoodLatitude())));
//            tvLongitude.setText(String.format("%s %s", getString(R.string.cood_long), String.valueOf(ponto.getCoodLongitude())));
    }
}
