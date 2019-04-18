package com.example.aplicativo_localizacao_indoor.model;

import java.util.Date;

public class local {
    private int id;
    private String ambiente;
    private String descricao;
    private String corredor;
    private Date data_hora_modificado;
    private String modificado_por;

    public local() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCorredor() {
        return corredor;
    }

    public void setCorredor(String corredor) {
        this.corredor = corredor;
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
