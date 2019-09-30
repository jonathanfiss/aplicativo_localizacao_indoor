package com.example.aplicativo_localizacao_indoor.model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {

    String nome;
    List<Aresta> adj;

    public Vertice(String nome) {
        this.nome = nome;
        this.adj = new ArrayList<Aresta>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aresta> getAdj() {
        return adj;
    }

    public void setAdj(List<Aresta> adj) {
        this.adj = adj;
    }

    @Override
    public String toString() {
        return "Vertice{" +
                "nome='" + nome + '\'' +
                ", adj=" + adj +
                '}';
    }

    public void addAdj(Aresta e){
        adj.add(e);
    }


}
