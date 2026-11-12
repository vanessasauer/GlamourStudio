package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Atendimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtendimentoResponse(Long id, String cliente, Long clienteId, String profissional, Long profissionalId, LocalDateTime dataHora, String servico, Long servicoId, BigDecimal valor, EnumStatusAtendimento statusAtendimento) {

    public AtendimentoResponse(Atendimento atendimentoEntidade){

        this(
                atendimentoEntidade.getId(),
                atendimentoEntidade.getCliente() != null ? atendimentoEntidade.getCliente().getNome() : null,
                atendimentoEntidade.getCliente() != null ? atendimentoEntidade.getCliente().getId() : null,
                atendimentoEntidade.getProfissional(),
                atendimentoEntidade.getProfissionalId(),
                atendimentoEntidade.getDataHora(),
                atendimentoEntidade.getServico() != null ? atendimentoEntidade.getServico().getNome() : null,
                atendimentoEntidade.getServico() != null ? atendimentoEntidade.getServico().getId() : null,
                atendimentoEntidade.getValor(),
                atendimentoEntidade.getStatusAtendimento()
        );
    }
}
