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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminCadastraPontoActivity extends BaseActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ProgressDialog mProgressDialog;
    private ListView lvPontosRef;
    private int executa = 0;
    private int temponovabusca = 5000; //tempo em milisegundos


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        verificaWifi();
        verificaPermissao(AdminCadastraPontoActivity.this);

        lvPontosRef = findViewById(R.id.lv_pontos_ref);
        new TaskPonto().execute();

        lvPontosRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AdminCadastraPontoActivity.this, AdminCadastraPontoActivityDetalhe.class);
                intent.putExtra("position", position);
                executa = 1;
                finish();
                startActivity(intent);
            }
//            }
        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWait(AdminCadastraPontoActivity.this, R.string.builder_redes);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                while (executa == 0) {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                        AppSetup.wiFiDetalhes.clear();
                        for (ScanResult result : scanResults) {
                            WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                            wiFiDetalhes.setBSSID(result.BSSID);
                            wiFiDetalhes.setSSID(result.SSID);
                            wiFiDetalhes.setWiFiSignal(result.level);
                            wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                            AppSetup.wiFiDetalhes.add(wiFiDetalhes);
                        }
                        publishProgress(AppSetup.wiFiDetalhes);

                    Log.d("listscan", scanResults.toString());
                    Thread.sleep(temponovabusca);
                    scanResults.clear();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(List<WiFiDetalhe>... values) {
            super.onProgressUpdate(values);
            dismissWait();
            lvPontosRef.setAdapter(new PontoReferenciaAdapter(AdminCadastraPontoActivity.this, AppSetup.wiFiDetalhes));
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            dismissWait();
            lvPontosRef.setAdapter(new PontoReferenciaAdapter(AdminCadastraPontoActivity.this, AppSetup.wiFiDetalhes));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                executa = 1;
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        executa = 1;
        finish();
    }
}
