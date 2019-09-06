package com.example.aplicativo_localizacao_indoor.setup;

import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AppSetup {
    public static List<WiFiDetalhe> wiFiDetalhes = new ArrayList<>();
    public static List<WiFiDetalhe> wiFiDetalhesSelecionados = new ArrayList<>();
    public static Usuario usuario = null;
    public static PontoRef pontoRef = null;
    public static Sala sala = null;
    public static Local local = null;
    public static WiFiDetalhe pontoAnt = null;
    public static WiFiDetalhe pontoPost = null;
    public static List<Sala> salas = new ArrayList<>();
    public static List<PontoRef> pontosRef = new ArrayList<>();
    public static List<Local> locais = new ArrayList<>();
    public static String urlapi = "192.168.0.106/api-project/";

}
