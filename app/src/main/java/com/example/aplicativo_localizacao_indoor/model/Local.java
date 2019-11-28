package com.example.aplicativo_localizacao_indoor.model;

import com.google.gson.annotations.SerializedName;

public class Local {
    @SerializedName("id_local")
    private String key;
    private Integer andar;
    private String descricao;
    private String corredor;
    private String predio;

    public Local() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }


    @Override
    public String toString() {
        return "Local{" +
                "key='" + key + '\'' +
                ", andar=" + andar +
                ", descricao='" + descricao + '\'' +
                ", corredor='" + corredor + '\'' +
                ", predio='" + predio + '\'' +
                '}';
    }
}

