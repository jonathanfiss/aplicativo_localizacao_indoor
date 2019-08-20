package com.example.aplicativo_localizacao_indoor.model;

import android.widget.EditText;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

public class PontoRef {
    private Long id_ponto;
    private String bssid;
    private String ssid;
    private Integer patrimonio;
   @SerializedName("bssid_ant")
    private String bssidAnt;
    @SerializedName("bssid_post")
    private String bssidPost;
    private Local local;
    private String key;

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    private boolean situacao;

    public PontoRef() {
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

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PontoRef{" +
                "id_ponto=" + id_ponto +
                ", bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", patrimonio=" + patrimonio +
                ", bssidAnt='" + bssidAnt + '\'' +
                ", bssidPost='" + bssidPost + '\'' +
                ", local=" + local +
                ", key='" + key + '\'' +
                ", situacao=" + situacao +
                '}';
    }
}
