package com.example.aplicativo_localizacao_indoor.model;

public class WiFiDetalhe {
    private String SSID;
    private String BSSID;
    private int wiFiSignal;
    private Double distacia;
    private Integer frequencia;

    public WiFiDetalhe() {
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getWiFiSignal() {
        return wiFiSignal;
    }

    public void setWiFiSignal(int wiFiSignal) {
        this.wiFiSignal = wiFiSignal;
    }

    public Double getDistacia() {
        return distacia;
    }

    public void setDistacia(Double distacia) {
        this.distacia = distacia;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public double calculaDistancia(int frequency, int level) {
        double DISTANCE_MHZ_M = 27.55;
        return Math.pow(10.0, (DISTANCE_MHZ_M - (20 * Math.log10(frequency)) + Math.abs(level)) / 20.0);
    }

    @Override
    public String toString() {
        return "WiFiDetalhe{" +
                "SSID='" + SSID + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", wiFiSignal=" + wiFiSignal +
                ", distacia=" + distacia +
                ", frequencia=" + frequencia +
                '}';
    }
}