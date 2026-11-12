package com.example.glamourstudio.application.DTOs;
import com.example.glamourstudio.domain.entities.Servico;

import java.math.BigDecimal;

public record ServicoResponse(Long id, String nome, String descricao, BigDecimal valor, Integer duracaoMinutos, EnumStatusServico statusServico) {

    public ServicoResponse(Servico servicoEntidade){

        this(servicoEntidade, servicoEntidade.getStatusServico());
    }

    // usado quando o status mostrado é o da empresa (serviço padrão desativado por ela)
    public ServicoResponse(Servico servicoEntidade, EnumStatusServico statusServico){

        this(
                servicoEntidade.getId(),
                servicoEntidade.getNome(),
                servicoEntidade.getDescricao(),
                servicoEntidade.getValor(),
                servicoEntidade.getDuracaoMinutos(),
                statusServico
        );
    }

}