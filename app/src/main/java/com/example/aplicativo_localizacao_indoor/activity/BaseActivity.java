package com.example.aplicativo_localizacao_indoor.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.BuscaProfundidade;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgressDialog;
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();


    public boolean verificaPermissao(final Context context) {
        final int REQUEST_PERMISSIONS_CODE = 0;

        //verifica se o aplicativo tem permissão para utilizar a localização
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //verifica se permissão ja foi negada alguma vez para utilizar a localização
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //adiciona um título e uma mensagem
                builder.setTitle(R.string.builder_titulo);
                builder.setMessage(R.string.builder_mensagem);
                //adiciona os botões
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        }//retorna true se tiver tudo certo
        Log.d("Funcionalidades", "Permissão para utilizar a localização aceita");
        return true;
    }

    public void verificaWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            Log.d("Funcionalidades", "Wi-Fi Habilitado");
        }
    }

    public void showWait(final Context context, int message) {
        //cria e configura a caixa de diálogo e progressão
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getResources().getString(message));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    public String formataBSSID(String BSSID) {
//        return BSSID.substring(2, 14);
        return BSSID.substring(0, 14);
    }

    //Faz Dismiss na ProgressDialog
    public void dismissWait() {
        mProgressDialog.dismiss();
    }

    public void habilitarVoltar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void buscaSalas() {
        DatabaseReference mySala = database.getReference("dados/salas");
        mySala.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Sala sala = ds.getValue(Sala.class);
                    AppSetup.salas.add(sala);
                }
                hashMapSalas();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Salas", "Failed to read value.", error.toException());
            }
        });
    }

    public void buscaPontosRef() {
        DatabaseReference myPonto = database.getReference("dados/pontosref");
        myPonto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PontoRef pontoRef = ds.getValue(PontoRef.class);
                    pontoRef.setKey(ds.getKey());
                    AppSetup.pontosRef.add(pontoRef);
                }
                hashMapMacs();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Pontos de referencia", "Failed to read value.", error.toException());
            }
        });
    }

    public void buscaLocais() {
        DatabaseReference myLocais = database.getReference("dados/locais");
        myLocais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Local local = ds.getValue(Local.class);
                    AppSetup.locais.add(local);
                }
                hashMapCorredores();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Locais", "Failed to read value.", error.toException());
            }
        });
    }

    public void hashMapMacs() {
        AppSetup.listaMacs.clear();
        for (PontoRef pontoRef : AppSetup.pontosRef) {
            AppSetup.listaMacs.put(pontoRef.getKey(), pontoRef.getBssid());
        }
    }

    public void hashMapCorredores() {
        AppSetup.listaCorredores.clear();
        for (Local local : AppSetup.locais) {
            AppSetup.listaCorredores.put(local.getKey(), local.getCorredor().toLowerCase());
        }
    }

    public void hashMapSalas() {
        AppSetup.listaSalas.clear();
        for (Sala sala : AppSetup.salas) {
            AppSetup.listaSalas.put(sala.getKey(), sala.getNome().toLowerCase());
        }
    }
}
