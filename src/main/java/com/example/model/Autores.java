package com.example.model;

public class Autores {

    private Integer id;
    private String nome;
    private String email;
    private String site;

    public Autores(Integer id, String nome, String email, String site) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.site = site;
    }

    public Autores(String nome, String email, String site) {
        this.nome = nome;
        this.email = email;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return nome;
    }

    

}
