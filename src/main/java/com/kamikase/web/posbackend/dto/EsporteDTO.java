package com.kamikase.web.posbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EsporteDTO {
    private long id;

    @NotBlank(message = "O código não pode estar em branco")
    @Size(min = 3, max = 10, message = "O código deve ter entre 3 e 10 caracteres")
    private String codigo;

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "A descrição não pode estar em branco")
    private String descricao;

    @NotBlank(message = "O país de origem não pode estar em branco")
    private String paisOrigem;

    @NotNull(message = "A quantidade de jogadores não pode ser nula")
    private Integer quantidadeJogadores;

    @NotBlank(message = "O tipo de campo não pode estar em branco")
    private String tipoCampo;

}