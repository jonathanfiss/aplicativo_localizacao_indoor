package com.example.aplicativo_localizacao_indoor.service;

import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PontoRefService {
    @GET("pontoref/read.php/")
    Call<PontoRefList> getPonto();

    //    @GET("resto endereço")
//    Call<classe> getmetodo();
//
//    @GET("carros/tipo/{tipo}")
//    Call<CarroSync> getCarrosByTipo(@Path("tipo") String tipo);
//
    @POST("pontoref/create.php/")
    Call<Void> inserir(@Body PontoRef pontoRef);

    //
//    @PUT("pontoref/update.php/")
//    Call<Void> alterar(@Body PontoRef pontoRef);
//
    @DELETE("pontoref/delete.php/id={id}")
    Call<Void> excluir(@Path("id") String id);
}
