package com.example.aplicativo_localizacao_indoor.model;

import java.util.ArrayList;

public class Busca {
    private Vertice inicio, procurando;
    private ArrayList<Vertice> visitados;

    public Busca(Vertice inicio, Vertice procurando) {
        this.inicio = inicio;
        this.procurando = procurando;
        visitados = new ArrayList<Vertice>();
    }

    public Busca() {
    }

    public Vertice getInicio() {
        return inicio;
    }

    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }

    public Vertice getProcurando() {
        return procurando;
    }

    public void setProcurando(Vertice procurando) {
        this.procurando = procurando;
    }

    public ArrayList<Vertice> getVisitados() {
        return visitados;
    }

    public void setVisitados(ArrayList<Vertice> visitados) {
        this.visitados = visitados;
    }

    public void inserirVisitados(Vertice v){
        this.getVisitados().add(v);
    }
}
