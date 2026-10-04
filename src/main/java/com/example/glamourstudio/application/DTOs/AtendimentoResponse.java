package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Atendimento;
import com.example.glamourstudio.domain.entities.EnumStatusAtendimento;
import java.time.LocalDateTime;

public record AtendimentoResponse(Long id, String cliente, String profissional, LocalDateTime dataHora, String servico, EnumStatusAtendimento statusAtendimento) {

    public AtendimentoResponse(Atendimento atendimentoEntidade){

        this(
                atendimentoEntidade.getId(),
                atendimentoEntidade.getCliente(),
                atendimentoEntidade.getProfissional(),
                atendimentoEntidade.getDataHora(),
                atendimentoEntidade.getServico(),
                atendimentoEntidade.getStatusAtendimento()
        );
    }
}
