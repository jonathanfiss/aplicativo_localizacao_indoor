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
    @SerializedName("bssid_ant")
    private String bssidAnt2;
    @SerializedName("bssid_post")
    private String bssidPost;
    @SerializedName("bssid_post")
    private String bssidPost2;
    private Boolean Situacao;
    private Integer local_id;
    private Local local;

    public PontoRef() {
    }

    public Long getId_ponto() {
        return id_ponto;
    }

    public void setId_ponto(Long id_ponto) {
        this.id_ponto = id_ponto;
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

    public Boolean getSituacao() {
        return Situacao;
    }

    public void setSituacao(Boolean situacao) {
        Situacao = situacao;
    }

    public Integer getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Integer local_id) {
        this.local_id = local_id;
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
                "id_ponto=" + id_ponto +
                ", bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", patrimonio=" + patrimonio +
                ", bssidAnt='" + bssidAnt + '\'' +
                ", bssidAnt2='" + bssidAnt2 + '\'' +
                ", bssidPost='" + bssidPost + '\'' +
                ", bssidPost2='" + bssidPost2 + '\'' +
                ", Situacao=" + Situacao +
                ", local_id=" + local_id +
                ", local=" + local +
                '}';
    }
}
