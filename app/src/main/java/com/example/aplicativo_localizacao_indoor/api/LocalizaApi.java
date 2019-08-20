package com.example.aplicativo_localizacao_indoor.api;

import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface LocalizaApi {
    @GET("pontoref/read.php/")
    Call<PontoRefList> getPonto();

//    @GET("carros")
//    Call<CarroSync> getCarros();
//
//    @GET("carros/tipo/{tipo}")
//    Call<CarroSync> getCarrosByTipo(@Path("tipo") String tipo);
//
//    @POST("carros")
//    Call<Void> inserir(@Body Carro carro);
//
//    @PUT ("carros")
//    Call<Void> alterar(@Body Carro carro);
//
//    @DELETE("carros/{id}")
//    Call<Void> excluir(@Path("id") String id);
}
