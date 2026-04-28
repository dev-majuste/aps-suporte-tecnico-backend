package com.majuste.suportetecnico.model.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.majuste.suportetecnico.model.enums.Cargo;
import jakarta.persistence.*;

//Sempre que me referir sobre Decorador, decorator ou anotação tenham em mente que é a mesma coisa

//Decorador de entidade, ele marca a classe como uma entidade JPA
@Entity
//Decorator que define o nome da entidade no banco de dados, sem ela o nome da tabela seria o mesmo da classe, nesse projeto nao muda mundo
@Table(name = "usuarios")
public class Usuario {
    //Decorator de id, ele que indica que o atributo é a chave primaria da tabela
    @Id
    //Esse trem aqui define como que vai ser o auto_increment da chave, estou usando o IDENTITY, mas tbm existem outros
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    //Esse decorator impede que a senha apareça no Json quando buscar um usuario pela API, no caso ele permite apenas a escrita
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    //O cargo é um Enum que ta na pasta enums
    //Decorator do enum eo EnumType para String
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    //Construtor vazio
    public Usuario() {}

    //Getters e setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
