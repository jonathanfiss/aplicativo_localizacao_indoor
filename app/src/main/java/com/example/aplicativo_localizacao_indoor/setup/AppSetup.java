package com.example.aplicativo_localizacao_indoor.setup;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Ponto;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;

import java.util.ArrayList;
import java.util.List;

public class AppSetup {
    public static List<WiFiDetalhes> wiFiDetalhes = new ArrayList<>();
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Sala> salas = new ArrayList<>();
    public static List<Ponto> pontosRef = new ArrayList<>();
    public static List<Local> locais = new ArrayList<>();
    public static Ponto ponto = null;
    public static List<Local> local = new ArrayList<>();

}
