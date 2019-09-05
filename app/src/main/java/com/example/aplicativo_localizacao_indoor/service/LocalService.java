package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.LocalList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LocalService {
    @GET("local/read.php/")
    Call<LocalList> getLocal();

//    @GET("resto endereço")
//    Call<classe> getmetodo();
//
    @GET("local/read.php/{id_local}")
    Call<LocalList> getLocalByTipo(@Path("id_local") String id_local);
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
