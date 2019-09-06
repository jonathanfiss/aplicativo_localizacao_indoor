package com.example.aplicativo_localizacao_indoor.model;

import com.example.aplicativo_localizacao_indoor.model.Local;
import com.google.firebase.database.Exclude;

public class Sala {
    private String nome;
    private String numero;
    private String descricao;
    private String bssid_prox;
    private String data_hora_modificado;
    private String modificado_por;
    private boolean situacao;
    private Local local;
    private String local_id;

    public Sala() {
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

    public String getData_hora_modificado() {
        return data_hora_modificado;
    }

    public void setData_hora_modificado(String data_hora_modificado) {
        this.data_hora_modificado = data_hora_modificado;
    }

    public String getBssid_prox() {
        return bssid_prox;
    }

    public void setBssid_prox(String bssid_prox) {
        this.bssid_prox = bssid_prox;
    }

    public String getModificado_por() {
        return modificado_por;
    }

    public void setModificado_por(String modificado_por) {
        this.modificado_por = modificado_por;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }
}
