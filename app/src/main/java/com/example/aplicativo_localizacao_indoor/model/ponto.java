package com.example.aplicativo_localizacao_indoor.model;

import java.util.Date;

public class ponto {
    private int id;
    private String mac;
    private int patrimonio;
    private Date data_hora_modificado;
    private String modificado_por;

    public ponto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
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
