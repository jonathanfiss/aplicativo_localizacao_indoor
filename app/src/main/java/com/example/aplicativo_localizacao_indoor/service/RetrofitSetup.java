package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetup {
    private final Retrofit retrofit;

    public RetrofitSetup() {

        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.0.101/api-project/")
                .baseUrl("https://puff-altitude.000webhostapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //cria o serviço
    public PontoRefService getPontoRefService(){
        return retrofit.create(PontoRefService.class);
    }
    public LocalService getLocalService(){
        return retrofit.create(LocalService.class);
    }
    public SalaService getSalaRefService(){
        return retrofit.create(SalaService.class);
    }
}
