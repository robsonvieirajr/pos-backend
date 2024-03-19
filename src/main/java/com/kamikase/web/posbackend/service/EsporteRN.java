package com.kamikase.web.posbackend.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamikase.web.posbackend.client.SportsApiClient;
import com.kamikase.web.posbackend.dto.EsporteDTO;
import com.kamikase.web.posbackend.model.Esporte;
import com.kamikase.web.posbackend.repository.EsporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class EsporteRN {

	private final EsporteRepository repository;
	private final ModelMapper modelMapper;
	private final SportsApiClient sportsApiClient;

	public String listarEsporteFormatadoPorId() {
		log.info("Chamando a API externa para obter esporte por Id!");
		String esporte = sportsApiClient.obterEsportePorId();

		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder esporteFormatado = new StringBuilder();

		try {
			JsonNode formataEsporte = objectMapper.readTree(esporte).get("sports");

			for (JsonNode sporte : formataEsporte) {
				esporteFormatado.append("ID do Esporte: ").append(sporte.get("idSport").asText()).append("\n");
				esporteFormatado.append("Nome do Esporte: ").append(sporte.get("strSport").asText()).append("\n");
				esporteFormatado.append("Formato do Esporte: ").append(sporte.get("strFormat").asText()).append("\n");
				esporteFormatado.append("Descrição do Esporte: ").append(sporte.get("strSportDescription").asText()).append("\n");
				esporteFormatado.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao formatar os esportes";
		}

		return esporteFormatado.toString();
	}


	public EsporteDTO retornaEsporte(Long id) {
		log.info("Buscando esporte por ID: {}", id);
		Optional<Esporte> esporteOptional = repository.findById(id);
		if (esporteOptional.isPresent()) {
			return convertToDto(esporteOptional.get());
		} else {
			log.error("Esporte não encontrado para o ID: {}", id);
			throw new IllegalArgumentException("Esporte não encontrado para o ID: " + id);
		}
	}

	public EsporteDTO criarEsporte(EsporteDTO esporteDTO) {
		log.info("Criando um novo esporte: {}", esporteDTO);
		Esporte esporte = convertToEntity(esporteDTO);
		try {
			Esporte savedEsporte = repository.save(esporte);
			return convertToDto(savedEsporte);
		} catch (DataIntegrityViolationException e) {
			log.error("Erro ao criar o esporte. Dados inválidos: {}", esporteDTO, e);
			throw new IllegalArgumentException("Dados inválidos para criar o esporte");
		}
	}

	public EsporteDTO atualizarEsporte(Long id, EsporteDTO esporteDTO) {
		log.info("Atualizando o esporte com o ID: {}", id);
		Optional<Esporte> esporteOptional = repository.findById(id);
		if (esporteOptional.isPresent()) {
			Esporte esporte = esporteOptional.get();
			esporte.setCodigo(esporteDTO.getCodigo());
			esporte.setNome(esporteDTO.getNome());
			esporte.setDescricao(esporteDTO.getDescricao());
			esporte.setPaisOrigem(esporteDTO.getPaisOrigem());
			Esporte atualizaEsporte = repository.save(esporte);
			return convertToDto(atualizaEsporte);
		} else {
			log.error("Esporte não encontrado para o ID: {}", id);
			throw new IllegalArgumentException("Esporte não encontrado para o ID: " + id);
		}
	}

	public void deletarEsporte(Long id) {
		log.info("Deletando o esporte com o ID: {}", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			log.error("Esporte não encontrado para o ID: {}", id);
			throw new IllegalArgumentException("Esporte não encontrado para o ID: " + id);
		}
	}

	private EsporteDTO convertToDto(Esporte esporte) {
		return modelMapper.map(esporte, EsporteDTO.class);
	}

	private Esporte convertToEntity(EsporteDTO esporteDTO) {
		return modelMapper.map(esporteDTO, Esporte.class);
	}
}