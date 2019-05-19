package com.example.aplicativo_localizacao_indoor.model;

public class Ponto {
    private String bssid;
    private String ssid;
    private String patrimonio;
    private Double coodLatitude;
    private Double coodLongitude;
    private Double coodAltura;
    private float coodVelocidade;
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

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Double getCoodLatitude() {
        return coodLatitude;
    }

    public void setCoodLatitude(Double coodLatitude) {
        this.coodLatitude = coodLatitude;
    }

    public Double getCoodLongitude() {
        return coodLongitude;
    }

    public void setCoodLongitude(Double coodLongitude) {
        this.coodLongitude = coodLongitude;
    }

    public Double getCoodAltura() {
        return coodAltura;
    }

    public void setCoodAltura(Double coodAltura) {
        this.coodAltura = coodAltura;
    }

    public float getCoodVelocidade() {
        return coodVelocidade;
    }

    public void setCoodVelocidade(float coodVelocidade) {
        this.coodVelocidade = coodVelocidade;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }


}
