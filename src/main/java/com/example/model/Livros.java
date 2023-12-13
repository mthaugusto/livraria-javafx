package com.example.model;

import java.math.BigDecimal;

public class Livros { 
    private Integer id;
    private String titulo;
    private String genero;
    private Integer paginas;
    private BigDecimal valor;
    private Autores autor;
    
    public Livros(String titulo, String genero, Integer paginas, BigDecimal valor, Autores autor) {
        this.titulo = titulo;
        this.genero = genero;
        this.paginas = paginas;
        this.valor = valor;
        this.autor = autor;
    }

    public Livros(Integer id, String titulo, String genero, Integer paginas, BigDecimal valor, Autores autor) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.paginas = paginas;
        this.valor = valor;
        this.autor = autor;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Livro [titulo=" + titulo + ", genero=" + genero + ", paginas=" + paginas + ", valor=" + valor + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livros titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Livros genero(String genero) {
        this.genero = genero;
        return this;
    }

    public Livros paginas(Integer paginas) {
        this.paginas = paginas;
        return this;
    }

    public Livros valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    
}
