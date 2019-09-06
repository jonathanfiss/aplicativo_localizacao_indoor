package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.SalaList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SalaService {
    @GET("sala/read.php/")
    Call<SalaList> getSala();

//    @GET("resto endereço")
//    Call<classe> getmetodo();
//
@GET("sala/read_one.php/id={id}")
Call<SalaList> getSalaById(@Path("id") String id);

@POST("sala/create.php/")
Call<Void> inserir(@Body Sala sala);
//
//    @PUT ("carros")
//    Call<Void> alterar(@Body Carro carro);
//
//    @DELETE("carros/{id}")
//    Call<Void> excluir(@Path("id") String id);
}
