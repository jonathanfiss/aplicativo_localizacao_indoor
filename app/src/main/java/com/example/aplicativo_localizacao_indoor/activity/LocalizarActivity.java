package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class LocalizarActivity extends BaseActivity {
    PontoRef pontoRef;
    private int executa = 0;
    private boolean wifi = true;
    private int temponovabusca = 15000; //tempo em milisegundos
    TextView tvLocaliza;
    List<ScanResult> scanResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar);
        tvLocaliza = findViewById(R.id.tvLocaliza);

        verificaWifi();
        verificaPermissao(LocalizarActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Call<PontoRefList> call = new RetrofitSetup().getPontoRefService().getPonto();

        call.enqueue(new Callback<PontoRefList>() {
            @Override
            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                if (response.isSuccessful()) {
                    PontoRefList pontoRefList = response.body();
                    AppSetup.pontosRef.clear();
                    AppSetup.pontosRef.addAll(pontoRefList.getPontoref());
                    Log.d("ponto", AppSetup.pontosRef.toString());
                    new TaskPonto().execute();

                }
            }

            @Override
            public void onFailure(Call<PontoRefList> call, Throwable t) {
                Toast.makeText(LocalizarActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWait(LocalizarActivity.this);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                AppSetup.wiFiDetalhes.clear();
                wiFiDetalhes.clear();
                Log.d("a", "passou aqui 1");

                while (executa == 0) {
                    if (wifi) {
                        wifiManager.startScan();
                        scanResults = wifiManager.getScanResults();
                        Log.d("wifi", scanResults.toString());
                        wifi = false;
                        Log.d("a", "passou aqui 2");
                    }

                    percorreArray(scanResults);


                }
                Thread.sleep(temponovabusca);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            dismissWait();
        }
    }
    public int percorreArray(List<ScanResult> scanResult){
        for (ScanResult result : scanResults) {
            for (PontoRef ponto : AppSetup.pontosRef) {
                Log.d("result", result.BSSID);
                Log.d("pontoRef", ponto.getBssid());
                if (ponto.getBssid().contains(result.BSSID)) {
                    AppSetup.pontoRef.setBssid(ponto.getBssid());
                    AppSetup.pontoRef.setBssidAnt(ponto.getBssidAnt());
                    AppSetup.pontoRef.setBssidPost(ponto.getBssidPost());
                    AppSetup.pontoRef.setId_ponto(ponto.getId_ponto());
                    AppSetup.pontoRef.setLocal(ponto.getLocal());
                    AppSetup.pontoRef.setLocal_id(ponto.getLocal_id());
                    AppSetup.pontoRef.setPatrimonio(ponto.getPatrimonio());
                    AppSetup.pontoRef.setSituacao(ponto.getSituacao());
                    AppSetup.pontoRef.setSsid(ponto.getSsid());
                    break;
                }
                Log.d("a", "passou aqui ttttttttttttttttttttt");
            }
            Log.d("a", "passou aqui dddddddddddddd");

        }
        return 1;
    }

    public void buscaDados(WiFiDetalhe wiFiDetalhe) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("dados");

        // Read from the database
        myRef.child("pontosref").orderByChild("bssid").equalTo(wiFiDetalhe.getBSSID()).limitToFirst(1).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pontoRef = dataSnapshot.getValue(PontoRef.class);
                Log.d("valor", pontoRef.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("valor", "Failed to read value.", error.toException());
            }
        });
        myRef.child("salas").orderByChild("bssid_prox").equalTo(pontoRef.getBssid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sala sala = dataSnapshot.getValue(Sala.class);
                setViewDados(sala);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setViewDados(Sala sala) {
        TextView localizacao = findViewById(R.id.tvLocaliza);
        localizacao.setText(getString(R.string.label_msg_predio).concat(" ").concat(String.valueOf(sala.getNome())).concat(" ").concat(getString(R.string.label_msg_andar)).concat(" ").concat(String.valueOf(sala.getNumero())).concat(" ").concat(getString(R.string.label_msg_corredor)).concat(" ").concat(String.valueOf(sala.getBssid_prox())));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
