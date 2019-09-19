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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminSelecionaPontosActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 0;
    private ProgressDialog mProgressDialog;
    private ListView lv_select_pontos_ref;
    private int executa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seleciona_pontos);

        Integer Activity_code = getIntent().getExtras().getInt("Activity_code");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        verificaPermissao();
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        lv_select_pontos_ref = findViewById(R.id.lv_select_pontos_ref);
        AppSetup.pontosRef.clear();
        new TaskPonto().execute();

        if (Activity_code == 3) {
            lv_select_pontos_ref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                }
            });
        } else {
            lv_select_pontos_ref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent();
                    i.putExtra("position", position);
                    setResult(1, i);
                    Toast.makeText(AdminSelecionaPontosActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                    executa = 1;
                    finish();
                }

            });
        }
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AdminSelecionaPontosActivity.this);
            mProgressDialog.setMessage("Buscando redes...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                Call<PontoRefList> call = new RetrofitSetup().getPontoRefService().getPonto();

                call.enqueue(new Callback<PontoRefList>() {
                    @Override
                    public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                        if (response.isSuccessful()) {
                            PontoRefList pontoRefList = response.body();
                            AppSetup.pontosRef.clear();
                            AppSetup.pontosRef.addAll(pontoRefList.getPontoref());
                            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                            while (executa == 0) {
                                wifiManager.startScan();
                                List<ScanResult> scanResults = wifiManager.getScanResults();
                                AppSetup.wiFiDetalhes.clear();
                                wiFiDetalhes.clear();
                                for (ScanResult result : scanResults) {
                                    for (PontoRef pontoRef : AppSetup.pontosRef) {
                                        if (!result.BSSID.equals(pontoRef.getBssid())) {


                                            WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                                            wiFiDetalhes.setBSSID(result.BSSID);
                                            wiFiDetalhes.setSSID(result.SSID);
                                            wiFiDetalhes.setWiFiSignal(result.level);
                                            wiFiDetalhes.setDistacia(wiFiDetalhes.calculaDistancia(result.frequency, result.level));
                                            AppSetup.wiFiDetalhes.add(wiFiDetalhes);
                                            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontosActivity.this, AppSetup.wiFiDetalhes));
                                            Log.d("diferente", wiFiDetalhes.toString());
                                        } else {
                                            Log.d("igual", pontoRef.toString());
                                        }
                                    }
                                }
                                publishProgress(AppSetup.wiFiDetalhes);
//                                Log.d("listscan", scanResults.toString());
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PontoRefList> call, Throwable t) {
                        Toast.makeText(AdminSelecionaPontosActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onProgressUpdate(List<WiFiDetalhe>... values) {
            super.onProgressUpdate(values);
            mProgressDialog.dismiss();
            lv_select_pontos_ref.setAdapter(new SelecionaPontoReferenciaAdapter(AdminSelecionaPontosActivity.this, AppSetup.wiFiDetalhes));
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            mProgressDialog.dismiss();
            lv_select_pontos_ref.setAdapter(new PontoReferenciaAdapter(AdminSelecionaPontosActivity.this, AppSetup.wiFiDetalhes));
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
                        ActivityCompat.requestPermissions(AdminSelecionaPontosActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checked, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                executa = 1;
                AppSetup.wiFiDetalhesSelecionados.clear();
                finish();
                break;
            case R.id.buttonOk:
                Intent i = new Intent();
                setResult(1, i);
                Toast.makeText(AdminSelecionaPontosActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
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
        AppSetup.wiFiDetalhesSelecionados.clear();
        finish();
    }
}

