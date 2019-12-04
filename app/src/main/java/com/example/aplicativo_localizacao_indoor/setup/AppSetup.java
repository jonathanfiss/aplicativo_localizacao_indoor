package com.example.aplicativo_localizacao_indoor.setup;

import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.Rota;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppSetup {
    //Listas com os dados do banco
    public static List<Sala> salas = new ArrayList<>();
    public static List<PontoRef> pontosRef = new ArrayList<>();
    public static List<Local> locais = new ArrayList<>();

    public static HashMap<String, String> listaMacs = new HashMap<String, String>();
    public static HashMap<String, PontoRef> listaPontoRef = new HashMap<String, PontoRef>();
    public static HashMap<String, String> listaCorredores = new HashMap<String, String>();
    public static HashMap<String, String> listaSalas = new HashMap<String, String>();

    //unico objeto
    public static PontoRef pontoRef = null;
    public static Sala sala = null;
    public static Local local = null;
    public static Usuario usuario = null;

    public static List<Sala> salasProx = new ArrayList<>();
    public static List<String> pontosProx = new ArrayList<>();


    public static List<WiFiDetalhe> wiFiDetalhes = new ArrayList<>();
    public static List<WiFiDetalhe> wiFiDetalhesSelecionados = new ArrayList<>();
    public static List<Rota> rotas = new ArrayList<>();

    public static WiFiDetalhe pontoWiFi = null;
    public static WiFiDetalhe pontoAnt = null;
    public static WiFiDetalhe pontoAnt2 = null;
    public static WiFiDetalhe pontoPost = null;
    public static WiFiDetalhe pontoPost2 = null;



}
