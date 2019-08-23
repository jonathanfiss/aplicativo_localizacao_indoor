package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.PontoRefList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PontoRefService {
    @GET("pontoref/read.php/")
    Call<PontoRefList> getPonto();

//    @GET("resto endereço")
//    Call<classe> getmetodo();
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
