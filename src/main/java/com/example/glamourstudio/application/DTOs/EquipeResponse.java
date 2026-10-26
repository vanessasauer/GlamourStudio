package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Equipe;

public record EquipeResponse(Long id, String nome, String descricao, EnumStatusEquipe statusEquipe) {

    public EquipeResponse(Equipe equipeEntidade){

        this(
                equipeEntidade.getId(),
                equipeEntidade.getNome(),
                equipeEntidade.getDescricao(),
                equipeEntidade.getStatusEquipe()
        );
    }
}
