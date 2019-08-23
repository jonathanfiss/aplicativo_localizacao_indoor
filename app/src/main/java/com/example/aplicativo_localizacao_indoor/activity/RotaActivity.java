package com.example.aplicativo_localizacao_indoor.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaPontosRefAdapter;
import com.example.aplicativo_localizacao_indoor.service.PontoRefService;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RotaActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        verificaWifi();
        verificaPermissao(RotaActivity.this);
        final ListView rota = findViewById(R.id.lvrota);

        Call<PontoRefList> call = new RetrofitSetup().getPontoRefService().getPonto();

        call.enqueue(new Callback<PontoRefList>() {
            @Override
            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                if (response.isSuccessful()) {
                    PontoRefList pontoRefList = response.body();
                    for (PontoRef pontoRef : pontoRefList.getPontoref()){
                        AppSetup.pontosRef.add(pontoRef);
                    }
                    rota.setAdapter(new ListaPontosRefAdapter(RotaActivity.this, AppSetup.pontosRef));
//                        Log.d("retorno api apos for", AppSetup.pontosRef.toString());

                }
            }

            @Override
            public void onFailure(Call<PontoRefList> call, Throwable t) {
                Toast.makeText(RotaActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
