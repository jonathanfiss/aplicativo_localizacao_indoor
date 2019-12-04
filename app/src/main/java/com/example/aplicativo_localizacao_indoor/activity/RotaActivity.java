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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.RotaAdapter;
import com.example.aplicativo_localizacao_indoor.model.BuscaProfundidade;
import com.example.aplicativo_localizacao_indoor.model.Rota;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotaActivity extends BaseActivity {

    private Button btBuscaRota;
    private ListView lvRota;
    private AutoCompleteTextView acBuscaRota;
    private String macs[];
    private HashMap<Integer, String> mapMacs;
    private int origem = -1, destino = -1;
    private boolean flag;
    private BuscaProfundidade buscaProfundidade;
    private List<Integer> caminho = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        habilitarVoltar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        acBuscaRota = findViewById(R.id.acBuscaRota);
        btBuscaRota = findViewById(R.id.btBuscaRota);
        lvRota = findViewById(R.id.lvRota);

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
                if (AppSetup.listaSalas.containsValue(acBuscaRota.getText().toString().toLowerCase())) {
                    new TaskRota().execute();
                } else {
                    Toast.makeText(RotaActivity.this, "Sala não encontrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class TaskRota extends AsyncTask<BuscaProfundidade, List<Rota>, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            showWait(RotaActivity.this, R.string.builder_rota);
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
                /////verifica se o mac está salvo no banco e adiciona a aresta
                for (PontoRef pontoRef1 : AppSetup.pontosRef) {
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
                    if (pontoRef1.getBssidPost() != null) {
                        if (!pontoRef1.getBssidPost().isEmpty()) {
                            i++;
                            mapMacs.put(i, formataBSSID(pontoRef1.getBssidPost()));
                            buscaProfundidade.adicionaAresta(principal, i);
                        }
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
                    }
                }

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiManager.startScan();
                do {
                    scanResults = wifiManager.getScanResults();
                } while (scanResults.isEmpty());
                AppSetup.wiFiDetalhes.clear();
                first:
                for (ScanResult result : scanResults) {
                    if (AppSetup.listaMacs.containsValue(formataBSSID(result.BSSID))) {
                        for (Map.Entry<Integer, String> map : mapMacs.entrySet()) {
                            if (map.getValue().equals(formataBSSID(result.BSSID))) {
                                origem = map.getKey();
                                break first;
                            }
                        }
                    }
                }
                if (origem != -1){
                    if (AppSetup.listaSalas.containsValue(acBuscaRota.getText().toString().toLowerCase())) {
                        second:
                        for (Sala sala : AppSetup.salas) {
                            if (sala.getNome().equalsIgnoreCase(String.valueOf(acBuscaRota.getText()))) {
                                for (PontoRef pontoRef : AppSetup.pontosRef) {
                                    if (formataBSSID(pontoRef.getBssid()).equals(formataBSSID(sala.getBssid_prox1())) || formataBSSID(pontoRef.getBssid()).equals(formataBSSID(sala.getBssid_prox2()))) {
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
                    }
                }else{
                    publishProgress(AppSetup.rotas);
                }
                Log.d("origin", String.valueOf(origem));
                Log.d("destino", String.valueOf(destino));
                Log.d("matriz numerica", buscaProfundidade.toString());
                Log.d("matriz", buscaProfundidade.toString2(mapMacs));
                if (destino != -1){
                    caminho = buscaProfundidade.getCaminho(origem, destino);
                    Log.d("matriz", caminho.toString());
                    if (!caminho.isEmpty()) {
                        AppSetup.rotas.clear();
                        for (Integer id : caminho) {
                            Rota rota = new Rota();
                            rota.setId(id);
                            rota.setBssid(mapMacs.get(id));
                            for (PontoRef pontoRef : AppSetup.pontosRef) {
                                if (pontoRef.getBssid().contains(formataBSSID(mapMacs.get(id)))) {
                                    rota.setLocal(pontoRef.getLocal());
                                    break;
                                }
                            }
                            AppSetup.rotas.add(rota);
                            Log.d("id", rota.toString());
                        }
                    }else{
                        AppSetup.rotas.clear();
                    }
                    Log.d("caminho", AppSetup.rotas.toString());

                    publishProgress(AppSetup.rotas);
                }else{
                    publishProgress(AppSetup.rotas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<Rota>... values) {
            super.onProgressUpdate(values);
            if (flag) {
                dismissWait();
                flag = false;
                if (origem != -1){
                    lvRota.setAdapter(new RotaAdapter(RotaActivity.this, AppSetup.rotas));
                }else{
                    Toast.makeText(RotaActivity.this, "Local de origin não encontrada", Toast.LENGTH_SHORT).show();
                }
                if (destino == -1 || caminho.isEmpty()){
                    Toast.makeText(RotaActivity.this, "Nenhum caminho possivel", Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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


}
