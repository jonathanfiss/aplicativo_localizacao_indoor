package com.example.aplicativo_localizacao_indoor.model;

import com.google.gson.annotations.SerializedName;

public class Local {
    @SerializedName("id_local")
    private Integer id;
    private Integer andar;
    private String descricao;
    private String corredor;
    private String predio;
    private String data_hora_modificado;
    private String modificado_por;
    private Boolean situacao;

    public Local() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
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

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", andar=" + andar +
                ", descricao='" + descricao + '\'' +
                ", corredor='" + corredor + '\'' +
                ", predio='" + predio + '\'' +
                ", data_hora_modificado='" + data_hora_modificado + '\'' +
                ", modificado_por='" + modificado_por + '\'' +
                ", situacao=" + situacao +
                '}';
    }
}

