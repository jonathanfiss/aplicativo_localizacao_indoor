package com.example.aplicativo_localizacao_indoor.model;

import android.widget.EditText;

public class Ponto {
    private String bssid;
    private String ssid;
    private int patrimonio;
    private String bssidAnt;
    private String bssidPost;

    private boolean situacao;

    public Ponto() {
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getBssidAnt() {
        return bssidAnt;
    }

    public void setBssidAnt(String bssidAnt) {
        this.bssidAnt = bssidAnt;
    }

    public String getBssidPost() {
        return bssidPost;
    }

    public void setBssidPost(String bssidPost) {
        this.bssidPost = bssidPost;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
}
