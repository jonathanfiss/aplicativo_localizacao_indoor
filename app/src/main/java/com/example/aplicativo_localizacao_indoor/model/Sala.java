package com.example.aplicativo_localizacao_indoor.model;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.google.firebase.database.Exclude;

public class Sala {
    private String key;
    private String nome;
    private String numero;
    private String descricao;
    private String bssid_prox1;
    private String bssid_prox2;
    private String bssid_prox3;
    private Local local;

    public Sala() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBssid_prox1() {
        return bssid_prox1;
    }

    public void setBssid_prox1(String bssid_prox1) {
        this.bssid_prox1 = bssid_prox1;
    }

    public String getBssid_prox2() {
        return bssid_prox2;
    }

    public void setBssid_prox2(String bssid_prox2) {
        this.bssid_prox2 = bssid_prox2;
    }

    public String getBssid_prox3() {
        return bssid_prox3;
    }

    public void setBssid_prox3(String bssid_prox3) {
        this.bssid_prox3 = bssid_prox3;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "key='" + key + '\'' +
                ", nome='" + nome + '\'' +
                ", numero='" + numero + '\'' +
                ", descricao='" + descricao + '\'' +
                ", bssid_prox1='" + bssid_prox1 + '\'' +
                ", bssid_prox2='" + bssid_prox2 + '\'' +
                ", bssid_prox3='" + bssid_prox3 + '\'' +
                ", local=" + local +
                '}';
    }
}
