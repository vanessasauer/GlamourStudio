package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Equipe;

import java.util.List;

public record EquipeResponse(Long id, String nome, String descricao, EnumStatusEquipe statusEquipe, List<UsuarioResponse> profissionais) {

    public EquipeResponse(Equipe equipeEntidade){

        this(
                equipeEntidade.getId(),
                equipeEntidade.getNome(),
                equipeEntidade.getDescricao(),
                equipeEntidade.getStatusEquipe(),
                equipeEntidade.getProfissionais()
                        .stream()
                        .map(UsuarioResponse::new)
                        .toList()
        );
    }
}