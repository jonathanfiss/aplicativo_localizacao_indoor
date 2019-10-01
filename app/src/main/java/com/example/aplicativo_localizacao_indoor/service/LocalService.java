package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.LocalList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LocalService {
    @GET("local/read.php/")
    Call<LocalList> getLocal();
    //    @GET("resto endereço")
//    Call<classe> getmetodo();
//
    @GET("local/read_one.php/id={id}")
    Call<LocalList> getLocalById(@Path("id") String id);

    //
    @POST("local/create.php/")
    Call<Void> inserir(@Body Local local);
//
//    @PUT ("carros")
//    Call<Void> alterar(@Body Carro carro);

    @DELETE("local/delete.php/id={id}")
    Call<Void> excluir(@Path("id") String id);
}
