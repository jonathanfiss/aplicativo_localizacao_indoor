package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class LocalizarActivity extends BaseActivity {
    PontoRef pontoRef;
    private int executa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar);

        verificaWifi();
        verificaPermissao(LocalizarActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new TaskPonto().execute();
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
                while (executa == 0) {
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    AppSetup.wiFiDetalhes.clear();
                    wiFiDetalhes.clear();
                    if (scanResults.isEmpty()) {
                        showWait(LocalizarActivity.this);
                    } else {
                        WiFiDetalhe wiFiDetalhes = new WiFiDetalhe();
                        for (ScanResult result : scanResults) {
                            if (result.level > wiFiDetalhes.getWiFiSignal()) {
                                wiFiDetalhes.setWiFiSignal(result.level);
                                Log.d("teste", String.valueOf(wiFiDetalhes.getWiFiSignal()));
                                dismissWait();

//                                wiFiDetalhes.setBSSID(result.BSSID);
//                                buscaDados(wiFiDetalhes);
//                                if(result.frequency> wiFiDetalhes.getFrequencia()){
//                                    wiFiDetalhes.setBSSID(result.BSSID);
//                                    buscaDados(wiFiDetalhes);
//                                    dismissWait();
//                                }
                            }
                        }
                    }
                    Log.d("listscan", scanResults.toString());
                    Thread.sleep(5000);
                }
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
