package com.example.aplicativo_localizacao_indoor.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminCadastraPontoActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ProgressDialog mProgressDialog;
    private ListView lvPontosRef;
    private int executa = 0;

    private PontoReferenciaAdapter pontoReferenciaAdapter;
    long TEMPO = (1000 * 3); // chama o método a cada 3 segundos


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto);

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        verificaPermissao();
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

        lvPontosRef = findViewById(R.id.lv_pontos_ref);
        lvPontosRef.setAdapter(pontoReferenciaAdapter);
        new TaskPonto().execute();

        lvPontosRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AdminCadastraPontoActivity.this, AdminCadastraPontoActivityDetalhe.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
//            }
        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, Integer, List<WiFiDetalhes>> {
        ArrayAdapter<WiFiDetalhes> adapter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter = (ArrayAdapter<WiFiDetalhes>) lvPontosRef.getAdapter();
            mProgressDialog = new ProgressDialog(AdminCadastraPontoActivity.this);
            mProgressDialog.setMessage("Buscando redes...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }

        @Override
        protected List<WiFiDetalhes> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                do {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    AppSetup.wiFiDetalhes.clear();
                    wiFiDetalhes.clear();
                    for (ScanResult result : scanResults) {
                        WiFiDetalhes wiFiDetalhes = new WiFiDetalhes();
                        wiFiDetalhes.setBSSID(result.BSSID);
                        wiFiDetalhes.setSSID(result.SSID);
                        wiFiDetalhes.setWiFiSignal(result.level);
                        wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                        AppSetup.wiFiDetalhes.add(wiFiDetalhes);
                    }
                    Log.d("listscan", wifiManager.getScanResults().toString());
                    publishProgress(1);
                    Thread.sleep(3000);
                } while (executa == 0);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.dismiss();
            if (values[0].equals(1)) {
                lvPontosRef.setAdapter(new PontoReferenciaAdapter(AdminCadastraPontoActivity.this, AppSetup.wiFiDetalhes));
            }
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhes> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            mProgressDialog.dismiss();
            lvPontosRef.setAdapter(new PontoReferenciaAdapter(AdminCadastraPontoActivity.this, AppSetup.wiFiDetalhes));
        }
    }


    public void dialogBuscando() {

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
