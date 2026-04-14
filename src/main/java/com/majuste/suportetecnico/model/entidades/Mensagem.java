package com.majuste.suportetecnico.model.entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "chamado_id")
    private Chamado chamado;

    public Mensagem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
