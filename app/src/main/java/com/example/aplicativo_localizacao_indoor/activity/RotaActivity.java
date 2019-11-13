package com.example.aplicativo_localizacao_indoor.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.BuscaProfundidade;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class RotaActivity extends BaseActivity {

    private Button btBuscaRota;
    private AutoCompleteTextView acBuscaRota;
    private String macs[];
    private HashMap<Integer, String> mapMacs;
    private int origem, destino, codErro;
    private boolean flag;
    private BuscaProfundidade buscaProfundidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        habilitarVoltar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        acBuscaRota = findViewById(R.id.acBuscaRota);
        btBuscaRota = findViewById(R.id.btBuscaRota);

        final List<String> informacoes = new ArrayList<>();

        if (AppSetup.locais.isEmpty()) {
            buscaLocais();
        }
        if (AppSetup.pontosRef.isEmpty()) {
            buscaPontosRef();
        }
        if (AppSetup.salas.isEmpty()) {
            buscaSalas();
        }
        for (Sala sala : AppSetup.salas) {
            informacoes.add(sala.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, informacoes);
        acBuscaRota.setAdapter(adapter);

        btBuscaRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaProfundidade = new BuscaProfundidade(contaMacs());
                new TaskRota().execute();
            }
        });
    }

    class TaskRota extends AsyncTask<BuscaProfundidade, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
//            showWait(RotaActivity.this, R.string.builder_rota);
        }

        @Override
        protected Void doInBackground(BuscaProfundidade... buscaProfundidades) {
            List<ScanResult> scanResults;
            mapMacs = new HashMap<Integer, String>();
            int principal = 0;
            int i = -1;
            if (!mapMacs.isEmpty()) {
                mapMacs.clear();
            }
            try {

                for (PontoRef pontoRef1 : AppSetup.pontosRef) {
                    /////defini o vertice principal
                    if (mapMacs.containsValue(formataBSSID(pontoRef1.getBssid()))) {
                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
                            if (map.getValue().equals(formataBSSID(pontoRef1.getBssid()))) {
                                mapMacs.put(map.getKey(), formataBSSID(pontoRef1.getBssid()));
                                principal = map.getKey();
                                break;
                            }
                        }
                    } else {
                        i++;
                        mapMacs.put(i, formataBSSID(pontoRef1.getBssid()));
                        principal = i;
                    }
//            Log.d("A ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
                    if (pontoRef1.getBssidPost() != null) {
                        if (!pontoRef1.getBssidPost().isEmpty()) {
//                    if (mapMacs.containsValue(pontoRef1.getBssidPost())) {
//                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
//                            if (map.getValue().equals(pontoRef1.getBssidPost())) {
//                                if (!map.getValue().equals(principal)) {
//                                    mapMacs.put(map.getKey(), pontoRef1.getBssidPost());
//                                    buscaProfundidade.adicionaAresta(principal, map.getKey());
//                                    break;
//                                }
//                            }
//                        }
//                    } else {
                            i++;
                            mapMacs.put(i, formataBSSID(pontoRef1.getBssidPost()));
                            buscaProfundidade.adicionaAresta(principal, i);
//                    }
                        }
//                Log.d("D ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
                    }
                    if (pontoRef1.getBssidPost2() != null) {
                        if (!pontoRef1.getBssidPost2().isEmpty()) {
//                    if (mapMacs.containsValue(pontoRef1.getBssidPost2())) {
//                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
//                            if (map.getValue().equals(pontoRef1.getBssidPost2())) {
//                                if (!map.getValue().equals(principal)) {
//                                    mapMacs.put(map.getKey(), pontoRef1.getBssidPost2());
//                                    buscaProfundidade.adicionaAresta(principal, map.getKey());
//                                    break;
//                                }
//                            }
//                        }
//                    } else {
                            i++;
                            mapMacs.put(i, formataBSSID(pontoRef1.getBssidPost2()));
                            buscaProfundidade.adicionaAresta(principal, i);
//                    }
                        }
//                Log.d("E ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
                    }
                }

                AppSetup.pontosProx.clear();
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiManager.startScan();
                do {
                    scanResults = wifiManager.getScanResults();
                } while (scanResults.isEmpty());
                AppSetup.wiFiDetalhes.clear();
                first:
                for (ScanResult result : scanResults) {
                    if (AppSetup.listaMacs.containsValue(result.BSSID)) {
                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
                            if (map.getValue().equals(formataBSSID(result.BSSID))) {
                                origem = map.getKey();
                                break first;
                            }
                        }
                    }
                }
                if (AppSetup.listaSalas.containsValue(acBuscaRota.getText().toString().toLowerCase())) {
                    second:
                    for (Sala sala : AppSetup.salas) {
                        if (sala.getNome().equalsIgnoreCase(String.valueOf(acBuscaRota.getText()))) {
                            for (PontoRef pontoRef : AppSetup.pontosRef) {
                                if (pontoRef.getBssid().equals(sala.getBssid_prox1()) || pontoRef.getBssid().equals(sala.getBssid_prox2()) || pontoRef.getBssid().equals(sala.getBssid_prox3())) {
                                    Log.d("aqui", "chegou eeeee");
                                    for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
                                        if (map.getValue().equals(formataBSSID(pontoRef.getBssid()))) {
                                            destino = map.getKey();
                                            break second;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    codErro = 1;
                    publishProgress();
                }

                Log.d("matriz", buscaProfundidade.toString());
                Log.d("matriz", buscaProfundidade.toString2(mapMacs));
                Log.d("matriz", buscaProfundidade.getCaminho(origem, destino).toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (flag) {
//                dismissWait();
                flag = false;
            }
        }
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

    public int contaMacs() {
        int cont = 0;
        for (PontoRef pontoRef1 : AppSetup.pontosRef) {
            cont++;

            if (pontoRef1.getBssidAnt() != null) {
                if (!pontoRef1.getBssidAnt().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidAnt2() != null) {
                if (!pontoRef1.getBssidAnt2().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidPost() != null) {
                if (!pontoRef1.getBssidPost().isEmpty()) {
                    cont++;
                }
            }
            if (pontoRef1.getBssidPost2() != null) {
                if (!pontoRef1.getBssidPost2().isEmpty()) {
                    cont++;
                }
            }
        }
        return cont;
    }

    public void buscaCaminho(int origem, int destino) {
        mapMacs = new HashMap<Integer, String>();
        int principal = 0;
        BuscaProfundidade buscaProfundidade = new BuscaProfundidade(contaMacs());
        int i = -1;
        if (!mapMacs.isEmpty()) {
            mapMacs.clear();
        }
        for (PontoRef pontoRef1 : AppSetup.pontosRef) {
            /////defini o vertice principal
            if (mapMacs.containsValue(formataBSSID(pontoRef1.getBssid()))) {
                for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
                    if (map.getValue().equals(formataBSSID(pontoRef1.getBssid()))) {
                        mapMacs.put(map.getKey(), formataBSSID(pontoRef1.getBssid()));
                        principal = map.getKey();
                        break;
                    }
                }
            } else {
                i++;
                mapMacs.put(i, formataBSSID(pontoRef1.getBssid()));
                principal = i;
            }
//            Log.d("A ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
            if (pontoRef1.getBssidPost() != null) {
                if (!pontoRef1.getBssidPost().isEmpty()) {
//                    if (mapMacs.containsValue(pontoRef1.getBssidPost())) {
//                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
//                            if (map.getValue().equals(pontoRef1.getBssidPost())) {
//                                if (!map.getValue().equals(principal)) {
//                                    mapMacs.put(map.getKey(), pontoRef1.getBssidPost());
//                                    buscaProfundidade.adicionaAresta(principal, map.getKey());
//                                    break;
//                                }
//                            }
//                        }
//                    } else {
                    i++;
                    mapMacs.put(i, formataBSSID(pontoRef1.getBssidPost()));
                    buscaProfundidade.adicionaAresta(principal, i);
//                    }
                }
//                Log.d("D ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
            }
            if (pontoRef1.getBssidPost2() != null) {
                if (!pontoRef1.getBssidPost2().isEmpty()) {
//                    if (mapMacs.containsValue(pontoRef1.getBssidPost2())) {
//                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
//                            if (map.getValue().equals(pontoRef1.getBssidPost2())) {
//                                if (!map.getValue().equals(principal)) {
//                                    mapMacs.put(map.getKey(), pontoRef1.getBssidPost2());
//                                    buscaProfundidade.adicionaAresta(principal, map.getKey());
//                                    break;
//                                }
//                            }
//                        }
//                    } else {
                    i++;
                    mapMacs.put(i, formataBSSID(pontoRef1.getBssidPost2()));
                    buscaProfundidade.adicionaAresta(principal, i);
//                    }
                }
//                Log.d("E ".concat(String.valueOf(i)), buscaProfundidade.toString2(mapMacs));
            }
        }

        Log.d("matriz", buscaProfundidade.toString());
        Log.d("matriz", buscaProfundidade.toString2(mapMacs));
        Log.d("matriz", buscaProfundidade.getCaminho(origem, destino).toString());
    }


}
