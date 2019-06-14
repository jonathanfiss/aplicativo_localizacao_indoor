package com.example.aplicativo_localizacao_indoor.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.adapter.SelecionaPontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminSelecionaPontoActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ProgressDialog mProgressDialog;
    private ListView lv_select_pontos_ref;
    private CardView cv_seleciona_lista;
    private int executa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seleciona_ponto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        final Integer retorno = getIntent().getExtras().getInt("retorno");
//        if (retorno.equals("anterior")) {
//
//        } else if (retorno.equals("posterior")) {
//
//        }

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        verificaPermissao();
        if (!wifiManager.isWifiEnabled()) {
            //gerar modal perguntando se deseja ligar o wifi
            wifiManager.setWifiEnabled(true);
        }

        lv_select_pontos_ref = findViewById(R.id.lv_select_pontos_r);
        new TaskPonto().execute();

        lv_select_pontos_ref.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cv_seleciona_lista = findViewById(R.id.cv_seleciona_lista);
                cv_seleciona_lista.setCardBackgroundColor(getResources().getColor(R.color.colorBranco));
                cv_seleciona_lista.setCardElevation(0);
                Log.d("seleciona", "não");
                return true;
            }
        });

        lv_select_pontos_ref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cv_seleciona_lista = findViewById(R.id.cv_seleciona_lista);
                cv_seleciona_lista.setCardBackgroundColor(getResources().getColor(R.color.colorVerdeClaro));
                cv_seleciona_lista.setCardElevation(1);
                AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
            }

        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AdminSelecionaPontoActivity.this);
            mProgressDialog.setMessage("Buscando redes...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                while (executa == 0) {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    AppSetup.wiFiDetalhes.clear();
                    wiFiDetalhes.clear();
                    if (scanResults.isEmpty()) {
                        mProgressDialog = new ProgressDialog(AdminSelecionaPontoActivity.this);
                        mProgressDialog.setMessage("Buscando redes...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();
                    } else {
                        for (ScanResult result : scanResults) {
                            WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                            wiFiDetalhes.setBSSID(result.BSSID);
                            wiFiDetalhes.setSSID(result.SSID);
                            wiFiDetalhes.setWiFiSignal(result.level);
                            wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                            AppSetup.wiFiDetalhes.add(wiFiDetalhes);
                        }
                        publishProgress(AppSetup.wiFiDetalhes);
                    }
                    Log.d("listscan", scanResults.toString());
                    Thread.sleep(4000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(List<WiFiDetalhe>... values) {
            super.onProgressUpdate(values);
            mProgressDialog.dismiss();
            lv_select_pontos_ref.setAdapter(new SelecionaPontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            mProgressDialog.dismiss();
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontoActivity.this, AppSetup.wiFiDetalhes));
        }
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
                        ActivityCompat.requestPermissions(AdminSelecionaPontoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
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
