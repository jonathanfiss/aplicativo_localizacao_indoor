package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class LocalizarActivity extends BaseActivity {
    PontoRef pontoRef;
    private boolean wifi = true;
    private int temponovabusca = 40000; //tempo em milisegundos
    TextView tvLocaliza;
    List<ScanResult> scanResults;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar);
        tvLocaliza = findViewById(R.id.tvLocaliza);

        habilitarVoltar();

        if (AppSetup.pontosRef == null || AppSetup.pontosRef.isEmpty()) {
            buscaPontosRef();
        } else {
            new TaskPonto().execute();
        }
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            showWait(LocalizarActivity.this, R.string.builder_localizar);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiManager.startScan();
                List<ScanResult> scanResults = wifiManager.getScanResults();
                AppSetup.wiFiDetalhes.clear();

                for (ScanResult result : scanResults) {
                    if (AppSetup.listaMacs.containsValue(result.BSSID)) {
                        for (PontoRef pontoRef : AppSetup.pontosRef) {
                            if (formataBSSID(result.BSSID).equals(formataBSSID(pontoRef.getBssid()))) {
                                AppSetup.pontoRef = pontoRef;
                                break;
                            }
                        }
                    }//else seria se o local não foi localizado
                    for (Sala sala : AppSetup.salas) {
                        if (formataBSSID(result.BSSID).equals(formataBSSID(sala.getBssid_prox1()))) {
                            AppSetup.salasProx.add(sala);
                            break;
                        } else if (formataBSSID(result.BSSID).equals(formataBSSID(sala.getBssid_prox2()))) {
                            AppSetup.salasProx.add(sala);
                            break;
                        } else if (formataBSSID(result.BSSID).equals(formataBSSID(sala.getBssid_prox3()))) {
                            AppSetup.salasProx.add(sala);
                            break;
                        }
                    }
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
            if (flag) {
                dismissWait();
                flag = false;
            }
            setText();
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            if (flag) {
                dismissWait();
                flag = false;
            }
            setText();
        }
    }

    public void setText(){
        String txtsalas = "";
        String texto;
        int cont = 0;
        int tamanho = AppSetup.salasProx.size();
        for (Sala sala: AppSetup.salasProx){
            if (txtsalas.isEmpty()){
                txtsalas = sala.getNome();
            } else {
                txtsalas = txtsalas.concat(sala.getNome());
            }
            cont++;
            if (tamanho > cont) {
                txtsalas = txtsalas.concat(", ");
            }
        }
        if (tamanho !=0){
            if (tamanho>1){
                texto = getResources().getText(R.string.frase_voce) + " " +
                        "" + getResources().getText(R.string.frase_predio) + " " +
                        "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                        "" + getResources().getText(R.string.frase_andar) + " " +
                        "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                        "" + getResources().getText(R.string.frase_corredor) + " " +
                        "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
                        "" + getResources().getText(R.string.frase_salas) + " " +
                        "" + txtsalas;
            }else{
                texto = getResources().getText(R.string.frase_voce) + " " +
                        "" + getResources().getText(R.string.frase_predio) + " " +
                        "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                        "" + getResources().getText(R.string.frase_andar) + " " +
                        "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                        "" + getResources().getText(R.string.frase_corredor) + " " +
                        "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
                        "" + getResources().getText(R.string.frase_sala) + " " +
                        "" + txtsalas;
            }
        }else{
            texto = getResources().getText(R.string.frase_voce) + " " +
                    "" + getResources().getText(R.string.frase_predio) + " " +
                    "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
                    "" + getResources().getText(R.string.frase_andar) + " " +
                    "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
                    "" + getResources().getText(R.string.frase_corredor) + " " +
                    "" + AppSetup.pontoRef.getLocal().getCorredor();
        }
        tvLocaliza.setText(texto);
    }


//    public void buscaDados(WiFiDetalhe wiFiDetalhe) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("dados");

//        // Read from the database
//        myRef.child("pontosref").orderByChild("bssid").equalTo(wiFiDetalhe.getBSSID()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
//
//            @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            pontoRef = dataSnapshot.getValue(PontoRef.class);
//            Log.d("valor", pontoRef.toString());
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w("valor", "Failed to read value.", error.toException());
//        }
//    });
//        myRef.child("salas").orderByChild("bssid_prox").equalTo(pontoRef.getBssid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Sala sala = dataSnapshot.getValue(Sala.class);
//                setViewDados(sala);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    public void setViewDados(Sala sala) {
//        TextView localizacao = findViewById(R.id.tvLocaliza);
//        localizacao.setText(getString(R.string.label_msg_predio).concat(" ").concat(String.valueOf(sala.getNome())).concat(" ").concat(getString(R.string.label_msg_andar)).concat(" ").concat(String.valueOf(sala.getNumero())).concat(" ").concat(getString(R.string.label_msg_corredor)).concat(" ").concat(String.valueOf(sala.getBssid_prox1())));
//
//    }

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
