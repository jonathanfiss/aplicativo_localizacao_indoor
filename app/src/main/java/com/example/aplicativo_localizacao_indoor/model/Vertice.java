package com.example.aplicativo_localizacao_indoor.model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {

    private String mac;
    public List<Aresta> arestas;

    public Vertice() {
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    @Override
    public String toString() {
        return "Vertice{" +
                "mac='" + mac + '\'' +
                ", arestas=" + arestas +
                '}';
    }
}
