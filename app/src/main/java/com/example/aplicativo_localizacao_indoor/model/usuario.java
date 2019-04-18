package com.example.aplicativo_localizacao_indoor.model;

import java.util.Date;

public class usuario {
    private String nome;
    private int matricula;
    private Date data_hora_modificado;
    private String modificado_por;

    public usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
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
