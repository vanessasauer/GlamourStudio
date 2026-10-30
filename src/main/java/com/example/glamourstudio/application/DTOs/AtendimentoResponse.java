package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Atendimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtendimentoResponse(Long id, String cliente, String profissional, Long profissionalId, LocalDateTime dataHora, String servico, BigDecimal valor, EnumStatusAtendimento statusAtendimento) {

    public AtendimentoResponse(Atendimento atendimentoEntidade){

        this(
                atendimentoEntidade.getId(),
                atendimentoEntidade.getCliente(),
                atendimentoEntidade.getProfissional(),
                atendimentoEntidade.getProfissionalId(),
                atendimentoEntidade.getDataHora(),
                atendimentoEntidade.getServico(),
                atendimentoEntidade.getValor(),
                atendimentoEntidade.getStatusAtendimento()
        );
    }
}

