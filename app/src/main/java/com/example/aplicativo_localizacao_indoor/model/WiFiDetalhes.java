package com.example.aplicativo_localizacao_indoor.model;

public class WiFiDetalhes {
    private  String SSID;
    private  String BSSID;
    private  String capabilities;
    private  int wiFiSignal;

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

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public int getWiFiSignal() {
        return wiFiSignal;
    }

    public void setWiFiSignal(int wiFiSignal) {
        this.wiFiSignal = wiFiSignal;
    }
}
