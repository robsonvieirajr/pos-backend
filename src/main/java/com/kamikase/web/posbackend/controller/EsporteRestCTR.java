package com.kamikase.web.posbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kamikase.web.posbackend.dto.EsporteDTO;
import com.kamikase.web.posbackend.service.EsporteRN;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/esporte")
@Tag(name = "open-api")
public class EsporteRestCTR {

    private final EsporteRN service;

    @Operation(summary = "Listar esportes de uma API externa pelo ID")
    @GetMapping("/Api-Externa")
    public ResponseEntity<String> listaEsporte() {
        String esportes = service.listarEsporteFormatadoPorId();
        return ResponseEntity.ok(esportes);
    }

    @Operation(summary = "Obter detalhes de um esporte pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EsporteDTO> getEsporte(@PathVariable Long id) {
        EsporteDTO esporte = service.retornaEsporte(id);
        return ResponseEntity.ok(esporte);
    }

    @Operation(summary = "Criar um novo esporte")
    @PostMapping
    public ResponseEntity<EsporteDTO> criarEsporte(@Valid @RequestBody EsporteDTO esporteDTO) {
        EsporteDTO novoEsporte = service.criarEsporte(esporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEsporte);
    }

    @Operation(summary = "Atualizar os detalhes de um esporte existente")
    @PutMapping("/{id}")
    public ResponseEntity<EsporteDTO> atualizarEsporte(@PathVariable Long id, @Valid @RequestBody EsporteDTO esporteDTO) {
        EsporteDTO esporteAtualizado = service.atualizarEsporte(id, esporteDTO);
        return ResponseEntity.ok(esporteAtualizado);
    }

    @Operation(summary = "Deletar um esporte pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEsporte(@PathVariable Long id) {
        service.deletarEsporte(id);
        return ResponseEntity.noContent().build();
    }
}
