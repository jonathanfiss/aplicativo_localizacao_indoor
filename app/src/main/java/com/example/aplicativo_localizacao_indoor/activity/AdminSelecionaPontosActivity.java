package com.example.aplicativo_localizacao_indoor.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaPontosRefAdapter;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.adapter.SelecionaPontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminSelecionaPontosActivity extends BaseActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ProgressDialog mProgressDialog;
    private ListView lv_select_pontos_ref;
    private TextView tvPontoRefSelecionados;
    private int temponovabusca = 5000; //tempo em milisegundos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seleciona_pontos);

        tvPontoRefSelecionados = findViewById(R.id.tvPontoRefSelecionados);

        Integer Activity_code = getIntent().getExtras().getInt("Activity_code");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        verificaPermissao(AdminSelecionaPontosActivity.this);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        lv_select_pontos_ref = findViewById(R.id.lv_select_pontos_ref);
        AppSetup.pontosRef.clear();
        AppSetup.wiFiDetalhesSelecionados.clear();
        new TaskPonto().execute();
        final PontoRef pontoRef = new PontoRef();
        final ArrayList<PontoRef> pontoRefSelecionados = new ArrayList();
        final ArrayList<String> selections = new ArrayList();
        lv_select_pontos_ref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (pontoRefSelecionados.isEmpty()) {
                    pontoRef.setSsid(AppSetup.wiFiDetalhes.get(position).getSSID());
                    pontoRef.setBssid(AppSetup.wiFiDetalhes.get(position).getBSSID());
                    pontoRefSelecionados.add(pontoRef);
                } else {
                    for (PontoRef pontoRef1 : pontoRefSelecionados) {
                        if (!pontoRef1.getBssid().equals(AppSetup.wiFiDetalhes.get(position).getBSSID())) {
                            pontoRef.setSsid(AppSetup.wiFiDetalhes.get(position).getSSID());
                            pontoRef.setBssid(AppSetup.wiFiDetalhes.get(position).getBSSID());
                            pontoRefSelecionados.add(pontoRef);
                        }
                    }
                }
                if (!pontoRefSelecionados.isEmpty()) {
                    for (PontoRef wifi : pontoRefSelecionados) {
                        selections.add(wifi.getSsid().concat(" - ").concat(wifi.getBssid()));
                    }
                    tvPontoRefSelecionados.setText(selections.toString());
                    selections.clear();
                }
            }
        });

    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWait(AdminSelecionaPontosActivity.this, R.string.builder_redes);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(List<WiFiDetalhe>... values) {
            super.onProgressUpdate(values);
            dismissWait();
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontosActivity.this, AppSetup.wiFiDetalhes));
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            dismissWait();
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontosActivity.this, AppSetup.wiFiDetalhes));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checked, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppSetup.wiFiDetalhesSelecionados.clear();
                finish();
                break;
            case R.id.buttonOk:
                Intent i = new Intent();
                setResult(1, i);
                Toast.makeText(AdminSelecionaPontosActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AppSetup.wiFiDetalhesSelecionados.clear();
        finish();
    }
}

