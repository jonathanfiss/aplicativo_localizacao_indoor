package com.example.aplicativo_localizacao_indoor.model;

import java.util.Date;

public class sala {
    private int id;
    private String numero;
    private String descricao;
    private Date data_hora_modificado;
    private String modificado_por;

    public sala() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getData_hora_modificado() {
        return data_hora_modificado;
    }

    public void setData_hora_modificado(Date data_hora_modificado) {
        this.data_hora_modificado = data_hora_modificado;
    }

    public String getModificado_por() {
        return modificado_por;
    }

    public void setModificado_por(String modificado_por) {
        this.modificado_por = modificado_por;
    }
}
