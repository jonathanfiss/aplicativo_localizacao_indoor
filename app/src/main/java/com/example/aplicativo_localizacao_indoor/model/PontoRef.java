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
    private Boolean Situacao;
    private Integer local_id;
    private Local local;

    public PontoRef() {
    }

    public PontoRef(Long id_ponto, String bssid, String ssid, Integer patrimonio, String bssidAnt, String bssidPost, Boolean situacao, Integer local_id, Local local) {
        this.id_ponto = id_ponto;
        this.bssid = bssid;
        this.ssid = ssid;
        this.patrimonio = patrimonio;
        this.bssidAnt = bssidAnt;
        this.bssidPost = bssidPost;
        Situacao = situacao;
        this.local_id = local_id;
        this.local = local;
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

    public String getBssidPost() {
        return bssidPost;
    }

    public void setBssidPost(String bssidPost) {
        this.bssidPost = bssidPost;
    }

    public Boolean getSituacao() {
        return Situacao;
    }

    public void setSituacao(Boolean situacao) {
        Situacao = situacao;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Integer getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Integer local_id) {
        this.local_id = local_id;
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
                ", Situacao=" + Situacao +
                ", local_id=" + local_id +
                ", local=" + local +
                '}';
    }
}
