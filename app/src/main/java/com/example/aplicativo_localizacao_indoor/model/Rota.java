package com.example.aplicativo_localizacao_indoor.model;

public class Rota {
    private Integer id;
    private String bssid;
    private Local local;

    public Rota() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Rota{" +
                "id='" + id + '\'' +
                ", bssid='" + bssid + '\'' +
                ", local=" + local +
                '}';
    }
}
