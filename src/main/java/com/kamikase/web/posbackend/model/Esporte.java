package com.kamikase.web.posbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Esporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String codigo;

    @Column(unique = true)
    private String nome;

    private String descricao;

    private String paisOrigem;

    private Integer quantidadeJogadores;

    private String tipoCampo;
}