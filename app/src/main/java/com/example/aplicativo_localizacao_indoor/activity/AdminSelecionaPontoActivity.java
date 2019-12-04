package com.example.aplicativo_localizacao_indoor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.adapter.SelecionaPontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminSelecionaPontoActivity extends BaseActivity {
    private ListView lv_select_pontos_ref;
    private int executa = 0;
    private int temponovabusca = 3000; //tempo em milisegundos
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer activity_code = getIntent().getExtras().getInt("Activity_code");
        setContentView(R.layout.activity_admin_seleciona_ponto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        verificaPermissao(AdminSelecionaPontoActivity.this);
        verificaWifi();

        lv_select_pontos_ref = findViewById(R.id.lv_select_pontos_r);
        AppSetup.pontosRef.clear();

        if (activity_code == 5 || activity_code == 6 || activity_code == 7) {
            new TaskPontoSala().execute();
        } else {
            new TaskPonto().execute();
        }

        lv_select_pontos_ref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("position", position);
                setResult(1, i);
                Toast.makeText(AdminSelecionaPontoActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                executa = 1;
                finish();
            }

        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            showWait(AdminSelecionaPontoActivity.this, R.string.builder_redes);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                while (executa == 0) {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    AppSetup.wiFiDetalhes.clear();

                    Log.d("listscan", scanResults.toString());
                    for (ScanResult result : scanResults) {
                        if (!AppSetup.listaMacs.containsValue(formataBSSID(result.BSSID))) {

                            if (!formataBSSID(result.BSSID).equals(formataBSSID(AppSetup.pontoWiFi.getBSSID()))) {
                                WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                                wiFiDetalhes.setBSSID(result.BSSID);
                                wiFiDetalhes.setSSID(result.SSID);
                                wiFiDetalhes.setWiFiSignal(result.level);
                                wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                                AppSetup.wiFiDetalhes.add(wiFiDetalhes);
                            }
                            if (AppSetup.pontoPost != null) {
                                for (WiFiDetalhe wiFiDetalhe : AppSetup.wiFiDetalhes) {
                                    if (formataBSSID(wiFiDetalhe.getBSSID()).equals(formataBSSID(AppSetup.pontoPost.getBSSID()))) {
                                        AppSetup.wiFiDetalhes.remove(wiFiDetalhe);
                                    }
                                }
                            }
                            if (AppSetup.pontoPost2 != null) {
                                for (WiFiDetalhe wiFiDetalhe : AppSetup.wiFiDetalhes) {
                                    if (formataBSSID(wiFiDetalhe.getBSSID()).equals(formataBSSID(AppSetup.pontoPost2.getBSSID()))) {
                                        AppSetup.wiFiDetalhes.remove(wiFiDetalhe);
                                    }
                                }
                            }
                        }
                    }
                    publishProgress(AppSetup.wiFiDetalhes);
                    Thread.sleep(temponovabusca);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(List<WiFiDetalhe>... values) {
            super.onProgressUpdate(values);
//            dismissWait();
            if (flag) {
                dismissWait();
                flag = false;
            }
            if (!AppSetup.wiFiDetalhes.isEmpty()) {
                lv_select_pontos_ref.setAdapter(new SelecionaPontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));

            }
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            if (flag) {
                dismissWait();
                flag = false;
            }
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));
        }
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPontoSala extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            showWait(AdminSelecionaPontoActivity.this, R.string.builder_redes);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                while (executa == 0) {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    AppSetup.wiFiDetalhes.clear();
                    Log.d("listscan", scanResults.toString());
                    for (ScanResult result : scanResults) {
                        WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                        wiFiDetalhes.setBSSID(result.BSSID);
                        wiFiDetalhes.setSSID(result.SSID);
                        wiFiDetalhes.setWiFiSignal(result.level);
                        wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                        AppSetup.wiFiDetalhes.add(wiFiDetalhes);

                        if (!AppSetup.wiFiDetalhesSelecionados.isEmpty()) {
                            for (WiFiDetalhe selecionados : AppSetup.wiFiDetalhesSelecionados) {
                                if (formataBSSID(wiFiDetalhes.getBSSID()).equals(formataBSSID(selecionados.getBSSID()))) {
                                    AppSetup.wiFiDetalhes.remove(wiFiDetalhes);
                                }
                            }
                        }
                    }


                    publishProgress(AppSetup.wiFiDetalhes);
                    Thread.sleep(temponovabusca);
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
            lv_select_pontos_ref.setAdapter(new SelecionaPontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            dismissWait();
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                executa = 1;
//                AppSetup.pontoAnt = null;
//                AppSetup.pontoPost = null;
//                AppSetup.wiFiDetalhes.clear();
//                wiFiDetalhes = null;
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
//        AppSetup.pontoAnt = null;
//        AppSetup.pontoPost = null;
//        AppSetup.wiFiDetalhes.clear();
//        wiFiDetalhes = null;
        finish();
    }
}
