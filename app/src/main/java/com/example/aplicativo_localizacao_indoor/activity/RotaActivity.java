package com.example.aplicativo_localizacao_indoor.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.BuscaProfundidade;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotaActivity extends BaseActivity {

    private Button btBuscaRota;
    private AutoCompleteTextView acBuscaRota;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String macs[];
    private HashMap<Integer, String> mapMacs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        habilitarVoltar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        acBuscaRota = findViewById(R.id.acBuscaRota);
        btBuscaRota = findViewById(R.id.btBuscaRota);

        final List<String> informacoes = new ArrayList<>();

        DatabaseReference myLocais = database.getReference("dados/locais");
        myLocais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Local local = ds.getValue(Local.class);
                    AppSetup.locais.add(local);
//                    informacoes.add(local.getCorredor());
                }
                Log.d("locais", "Value is: " + AppSetup.locais.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rota", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference mySala = database.getReference("dados/salas");
        mySala.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Sala sala = ds.getValue(Sala.class);
                    AppSetup.salas.add(sala);
                    informacoes.add(sala.getNome());
                    informacoes.add(sala.getNumero());
                }
                Log.d("salas", "Value is: " + AppSetup.salas.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rota", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference myPonto = database.getReference("dados/pontosref");
        myPonto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PontoRef pontoRef = ds.getValue(PontoRef.class);
                    AppSetup.pontosRef.add(pontoRef);
                }
                Log.d("pontosRef", "Value is: " + AppSetup.pontosRef.toString());
                criaMatriz();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rota", "Failed to read value.", error.toException());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, informacoes);
        acBuscaRota.setAdapter(adapter);

        btBuscaRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWait(RotaActivity.this, R.string.builder_rota);
                for (Sala sala : AppSetup.salas) {
                    if (sala.getNome().equalsIgnoreCase(String.valueOf(acBuscaRota.getText()))) {
                        for (PontoRef pontoRef : AppSetup.pontosRef) {
                            if (pontoRef.getBssid().equals(sala.getBssid_prox1())) {
                                Log.d("aqui", "chegou eeeee");
                            }
                        }
                        dismissWait();
                        break;
                    }
                }
            }
        });
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

    public void criaMatriz() {
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
        buscaProfundidade.getCaminho(1, 4);
    }


}
