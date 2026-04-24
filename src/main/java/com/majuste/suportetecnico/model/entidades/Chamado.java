package com.majuste.suportetecnico.model.entidades;

import com.majuste.suportetecnico.model.enums.StatusChamada;
import jakarta.persistence.*;

import java.time.LocalDate;

//Decoradores de entidade e tabela
@Entity
@Table(name = "chamados")
public class Chamado {
    //Id (chave pk) e o auto_incremente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataResolvido;
    //Aqui o decorator indica pro banco de dados que é um Enum e o tipo dele que deve ser armazenado no banco
    @Enumerated(EnumType.STRING)
    private StatusChamada status;
    //Decorator indica que é uma chave estrangeira, no caso recebe um Objeto, o Jpa ja relaciona automaticamen
    //Nesse relacionamento é 1:n, um cliente(usuario) pode estar em N chamados, mas 1 chamado pode ter 1 cliente
    @ManyToOne
    //Decorator indifca o nome da fk, o nullable significa que não pode ser nulo, por padrao ja vem como true
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    //Construtor vazio
    public Chamado() {}

    //Getters e setters
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataResolvido() {
        return dataResolvido;
    }

    public void setDataResolvido(LocalDate dataResolvido) {
        this.dataResolvido = dataResolvido;
    }

    public StatusChamada getStatus() {
        return status;
    }

    public void setStatus(StatusChamada status) {
        this.status = status;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }
}
