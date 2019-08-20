package com.example.aplicativo_localizacao_indoor.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.api.LocalizaApi;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106/api-project/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocalizaApi service = retrofit.create(LocalizaApi.class);

        Call<PontoRefList> call = service.getPonto();

        call.enqueue(new Callback<PontoRefList>() {
            @Override
            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                if (response.isSuccessful()) {
                    PontoRefList endereço = response.body();
//                    Log.d("retorno api", response.toString());

                    for (PontoRef pontoRef : endereço.pontoref){
                        Log.d("retorno api", pontoRef.toString());

                    }
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
