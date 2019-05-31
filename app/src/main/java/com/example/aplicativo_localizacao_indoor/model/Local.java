package com.example.aplicativo_localizacao_indoor.model;

import com.google.firebase.database.Exclude;

public class Local {
    private Long id;
    private Integer andar;
    private String descricao;
    private String corredor;
    private String data_hora_modificado;
    private String modificado_por;
    private boolean situacao;
    private String key; //atributo apenas local


    public Local() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
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

    public String getData_hora_modificado() {
        return data_hora_modificado;
    }

    public void setData_hora_modificado(String data_hora_modificado) {
        this.data_hora_modificado = data_hora_modificado;
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

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}

