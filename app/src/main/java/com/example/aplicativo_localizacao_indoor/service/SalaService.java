package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.model.Sala;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SalaService {
    @GET("sala/read.php/")
    Call<PontoRefList> getSala();

//    @GET("resto endereço")
//    Call<classe> getmetodo();
//
//    @GET("carros/tipo/{tipo}")
//    Call<CarroSync> getCarrosByTipo(@Path("tipo") String tipo);
//
@POST("sala/create.php/")
Call<Void> inserir(@Body Sala sala);
//
//    @PUT ("carros")
//    Call<Void> alterar(@Body Carro carro);
//
//    @DELETE("carros/{id}")
//    Call<Void> excluir(@Path("id") String id);
}
