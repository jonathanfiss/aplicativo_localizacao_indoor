package com.example.aplicativo_localizacao_indoor.model;

import android.widget.EditText;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

public class PontoRef {
    @Exclude
    private String key;
    private String bssid;
    private String ssid;
    private Integer patrimonio;
    private String bssidAnt;
    private String bssidAnt2;
    private String bssidPost;
    private String bssidPost2;
    private Local local;

    public PontoRef() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Integer getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Integer patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getBssidAnt() {
        return bssidAnt;
    }

    public void setBssidAnt(String bssidAnt) {
        this.bssidAnt = bssidAnt;
    }

    public String getBssidAnt2() {
        return bssidAnt2;
    }

    public void setBssidAnt2(String bssidAnt2) {
        this.bssidAnt2 = bssidAnt2;
    }

    public String getBssidPost() {
        return bssidPost;
    }

    public void setBssidPost(String bssidPost) {
        this.bssidPost = bssidPost;
    }

    public String getBssidPost2() {
        return bssidPost2;
    }

    public void setBssidPost2(String bssidPost2) {
        this.bssidPost2 = bssidPost2;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "PontoRef{" +
                "key='" + key + '\'' +
                ", bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", patrimonio=" + patrimonio +
                ", bssidAnt='" + bssidAnt + '\'' +
                ", bssidAnt2='" + bssidAnt2 + '\'' +
                ", bssidPost='" + bssidPost + '\'' +
                ", bssidPost2='" + bssidPost2 + '\'' +
                ", local=" + local +
                '}';
    }
}
