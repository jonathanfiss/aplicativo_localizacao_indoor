package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.SalaList;
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
    private int temponovabusca = 40000; //tempo em milisegundos
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
        showWait(LocalizarActivity.this, R.string.builder_localizar);
        call.enqueue(new Callback<PontoRefList>() {
            @Override
            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                if (response.isSuccessful()) {
                    PontoRefList pontoRefList = response.body();
                    AppSetup.pontosRef.clear();
                    AppSetup.pontosRef.addAll(pontoRefList.getPontoref());
                    Log.d("PontoRef Banco", AppSetup.pontosRef.toString());
                    if (!pontoRefList.pontoref.isEmpty())
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
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                AppSetup.wiFiDetalhes.clear();
                wiFiDetalhes.clear();

                if (wifi) {
                    wifiManager.startScan();
                    scanResults = wifiManager.getScanResults();
                    if (!scanResults.isEmpty()) {
                        wifi = false;
                    } else {
                        wifi = true;
                    }
                }
                if (!scanResults.isEmpty() && !AppSetup.pontosRef.isEmpty())
                    for (ScanResult result : scanResults) {
                        for (final PontoRef ponto : AppSetup.pontosRef) {
                            if (formataBSSID(result.BSSID).equals(formataBSSID(ponto.getBssid()))) {
                                AppSetup.pontoRef = ponto;
                                Log.d("PontoRef busca", result.toString());
                                Call<LocalList> call = new RetrofitSetup().getLocalService().getLocalById(ponto.getLocal_id().toString());

                                call.enqueue(new Callback<LocalList>() {
                                    @Override
                                    public void onResponse(Call<LocalList> call, Response<LocalList> response) {
                                        if (response.isSuccessful()) {
                                            AppSetup.local = null;
                                            LocalList localList = response.body();
                                            AppSetup.local = localList.getLocalLists().get(0);
                                            AppSetup.pontoRef.setLocal(localList.getLocalLists().get(0));
                                            Log.d("PontoRef Local", AppSetup.pontoRef.toString());
                                            tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
                                                    "" + getResources().getText(R.string.frase_predio) + " " +
                                                    "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                                                    "" + getResources().getText(R.string.frase_andar) + " " +
                                                    "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                                                    "" + getResources().getText(R.string.frase_corredor) + " " +
                                                    "" + AppSetup.pontoRef.getLocal().getCorredor());
                                            AppSetup.salas.clear();
                                            Call<SalaList> call2 = new RetrofitSetup().getSalaRefService().getSalaById(ponto.getLocal_id().toString());

                                            call2.enqueue(new Callback<SalaList>() {
                                                @Override
                                                public void onResponse(Call<SalaList> call, Response<SalaList> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.d("sala", "ok");
                                                        SalaList salaList = response.body();
                                                        String txtsalas = "";
                                                        for (Sala sala : salaList.getSalasLists()) {
                                                            sala.setLocal(AppSetup.local);
                                                            AppSetup.salas.add(sala);
                                                            Log.d("sala", sala.toString());
                                                        }
                                                        int cont = 0;
                                                        int tamanho = AppSetup.salas.size();
                                                        for (Sala sala : AppSetup.salas) {
                                                            if (txtsalas.isEmpty()) {
                                                                txtsalas = sala.getNome();
                                                            } else {
                                                                txtsalas = txtsalas.concat(sala.getNome());
                                                            }
                                                            cont++;
                                                            if (tamanho > cont) {
                                                                txtsalas = txtsalas.concat(", ");
                                                            }
                                                        }
                                                        dismissWait();

                                                        if (tamanho > 1) {
                                                            tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
                                                                    "" + getResources().getText(R.string.frase_predio) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                                                                    "" + getResources().getText(R.string.frase_andar) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                                                                    "" + getResources().getText(R.string.frase_corredor) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
                                                                    "" + getResources().getText(R.string.frase_salas) + " " +
                                                                    "" + txtsalas);
                                                            ViewCompat.setAccessibilityLiveRegion(tvLocaliza, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
                                                        } else {
                                                            tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
                                                                    "" + getResources().getText(R.string.frase_predio) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                                                                    "" + getResources().getText(R.string.frase_andar) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                                                                    "" + getResources().getText(R.string.frase_corredor) + " " +
                                                                    "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
                                                                    "" + getResources().getText(R.string.frase_sala) + " " +
                                                                    "" + txtsalas);
                                                            ViewCompat.setAccessibilityLiveRegion(tvLocaliza, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);

                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SalaList> call, Throwable t) {
                                                    Log.d("sala", "erro");

                                                    Toast.makeText(LocalizarActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LocalList> call, Throwable t) {
                                        Toast.makeText(LocalizarActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }
                    }
                scanResults.clear();
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
