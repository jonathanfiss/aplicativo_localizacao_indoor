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
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgressDialog;
    protected  HashMap<Integer, String> mapMacs;


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
        return true;
    }

    public void verificaWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
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

    public int contaMacs() {
        int cont = 0;
        for (PontoRef pontoRef1 : AppSetup.pontosRef) {
            cont++;

            if (pontoRef1.getBssidAnt() != null) {
                if (!pontoRef1.getBssidAnt().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidAnt2() != null) {
                if (!pontoRef1.getBssidAnt2().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidPost() != null) {
                if (!pontoRef1.getBssidPost().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidPost2() != null) {
                if (!pontoRef1.getBssidPost2().isEmpty()) {
                    cont++;
                }
            }
        }
        return cont;
    }

    public void criaMatriz() {
        mapMacs = new HashMap<Integer, String>();
        int principal = 0;
        BuscaProfundidade buscaProfundidade = new BuscaProfundidade(contaMacs());
        int i = 0;
        for (PontoRef pontoRef1 : AppSetup.pontosRef) {
            mapMacs.put(i, pontoRef1.getBssid());
            principal = i;
            i++;
            if (pontoRef1.getBssidAnt() != null) {
                if (!pontoRef1.getBssidAnt().isEmpty()) {
                    mapMacs.put(i, pontoRef1.getBssidAnt());
                    buscaProfundidade.adicionaAresta(principal,i);
                    i++;
                }
            }
            if (pontoRef1.getBssidAnt2() != null) {
                if (!pontoRef1.getBssidAnt2().isEmpty()) {
                    mapMacs.put(i, pontoRef1.getBssidAnt2());
                    buscaProfundidade.adicionaAresta(principal,i);
                    i++;
                }
            }
            if (pontoRef1.getBssidPost() != null) {
                if (!pontoRef1.getBssidPost().isEmpty()) {
                    mapMacs.put(i, pontoRef1.getBssidPost());
                    buscaProfundidade.adicionaAresta(principal,i);
                    i++;
                }
            }
            if (pontoRef1.getBssidPost2() != null) {
                if (!pontoRef1.getBssidPost2().isEmpty()) {
                    mapMacs.put(i, pontoRef1.getBssidPost2());
                    buscaProfundidade.adicionaAresta(principal,i);
                    i++;
                }
            }
        }

        Log.d("matriz", buscaProfundidade.toString2());
    }
}
