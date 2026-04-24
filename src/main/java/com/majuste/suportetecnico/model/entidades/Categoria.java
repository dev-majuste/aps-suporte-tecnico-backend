package com.majuste.suportetecnico.model.entidades;

import jakarta.persistence.*;

//Decorators de entidade e tabela
@Entity
@Table(name = "categorias")
public class Categoria {
    //Id e auto_infcrement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    //Construtor vazio
    public Categoria() {}

    //Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
