package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.LocalList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LocalService {
    @GET("local/read.php/")
    Call<LocalList> getLocal();

//    @GET("resto endereço")
//    Call<classe> getmetodo();
//
//    @GET("carros/tipo/{tipo}")
//    Call<CarroSync> getCarrosByTipo(@Path("tipo") String tipo);
//
@POST("local/create.php/")
Call<Void> inserir(@Body Local local);
//
//    @PUT ("carros")
//    Call<Void> alterar(@Body Carro carro);
//
//    @DELETE("carros/{id}")
//    Call<Void> excluir(@Path("id") String id);
}
