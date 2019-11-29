package com.example.aplicativo_localizacao_indoor.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

public class Usuario {

    private FirebaseUser firebaseUser;
    private String nome;
    private String sobrenome;
    private String matricula;
    private String funcao;
    private String email;
    private boolean situacao;
    private String key;

    public Usuario() {
    }

    @Exclude
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    @Exclude
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "Usuario{" +
                "firebaseUser=" + firebaseUser +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", funcao='" + funcao + '\'' +
                ", email='" + email + '\'' +
                ", situacao=" + situacao +
                ", key='" + key + '\'' +
                '}';
    }
}