package com.example.aplicativo_localizacao_indoor.model;

public class WiFiDetalhes {
    private String SSID;
    private String BSSID;
    private int wiFiSignal;
    private Double distacia;

    public WiFiDetalhes() {
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
}