package com.example.aplicativo_localizacao_indoor.activity;

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
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.SalaList;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RotaActivity extends BaseActivity {

    private Button btBuscaRota;
    private AutoCompleteTextView acBuscaRota;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


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
        final BuscaProfundidade buscaProfundidade = new BuscaProfundidade(6);
        DatabaseReference myPonto = database.getReference("dados/pontosref");
        myPonto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PontoRef pontoRef = ds.getValue(PontoRef.class);
                    AppSetup.pontosRef.add(pontoRef);
                    buscaProfundidade.adicionaAresta(pontoRef.getBssid());
                }
                Log.d("pontosRef", "Value is: " + AppSetup.pontosRef.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rota", "Failed to read value.", error.toException());
            }
        });
//        Call<SalaList> callSalas = new RetrofitSetup().getSalaRefService().getSala();
//
//        callSalas.enqueue(new Callback<SalaList>() {
//            @Override
//            public void onResponse(Call<SalaList> call, Response<SalaList> response) {
//                if (response.isSuccessful()) {
//                    SalaList salaList = response.body();
//                    AppSetup.salas.addAll(salaList.getSalasLists());
//                    for (Sala sala : salaList.getSalasLists()) {
//                        informacoes.add(sala.getNome());
//                        informacoes.add(sala.getNumero());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SalaList> call, Throwable t) {
//                Toast.makeText(RotaActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Call<LocalList> callLocais = new RetrofitSetup().getLocalService().getLocal();
//
//        callLocais.enqueue(new Callback<LocalList>() {
//            @Override
//            public void onResponse(Call<LocalList> call, Response<LocalList> response) {
//                if (response.isSuccessful()) {
//                    LocalList localList = response.body();
//                    AppSetup.locais.addAll(localList.getLocalLists());
////                    for (Local local : localList.getLocalLists()){
//////                        informacoes.add(local.getCorredor());
////                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LocalList> call, Throwable t) {
//                Toast.makeText(RotaActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Call<PontoRefList> callPonto = new RetrofitSetup().getPontoRefService().getPonto();

//        callPonto.enqueue(new Callback<PontoRefList>() {
//            @Override
//            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
//                if (response.isSuccessful()) {
//                    PontoRefList pontoRefList = response.body();
//                    AppSetup.pontosRef.addAll(pontoRefList.getPontoref());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PontoRefList> call, Throwable t) {
//                Toast.makeText(RotaActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
//            }
//        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, informacoes);
        acBuscaRota.setAdapter(adapter);

        btBuscaRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWait(RotaActivity.this, R.string.builder_rota);
                for (Sala sala : AppSetup.salas) {
                    if (sala.getNome().equalsIgnoreCase(String.valueOf(acBuscaRota.getText()))) {
                        for (PontoRef pontoRef : AppSetup.pontosRef) {
                            if (pontoRef.getBssid().equals(sala.getBssid_prox())) {
                                Log.d("aqui", "chegou eeeee");
                            }
                        }
                        dismissWait();
                        break;
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
