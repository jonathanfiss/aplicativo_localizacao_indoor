package com.example.aplicativo_localizacao_indoor.model;

import java.util.ArrayList;

public class Vertice {
    private int posicao;
    private String chave;
    private String nome;
    private String pai;
    private ArrayList sucessores = new ArrayList<Vertice>();

    public Vertice() {
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public ArrayList getSucessores() {
        return sucessores;
    }

    public void setSucessores(ArrayList sucessores) {
        this.sucessores = sucessores;
    }
}
