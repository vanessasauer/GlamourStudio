package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.AtendimentoRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusAtendimento;
import com.example.glamourstudio.application.DTOs.ServicoRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private String profissional;

    // id do Usuario que vai atender (usado para filtrar agenda e painel pelo logado)
    private Long profissionalId;

    private LocalDateTime dataHora;

    private String servico;

    // valor cobrado neste atendimento (fica fixo mesmo se o preço do serviço mudar depois)
    private BigDecimal valor;


    private EnumStatusAtendimento statusAtendimento = EnumStatusAtendimento.AGENDADO;

    public Atendimento(AtendimentoRequest atendimento) {
        this.setCliente(atendimento.cliente());
        this.setProfissional(atendimento.profissional());
        this.setProfissionalId(atendimento.profissionalId());
        this.setDataHora(atendimento.dataHora());
        this.setServico(atendimento.servico());
        this.setValor(atendimento.valor());
    }

    // Regra de negócio: soma dos valores de uma lista de atendimentos (ignora os sem valor).
    public static BigDecimal somarValores(List<Atendimento> atendimentos) {
        return atendimentos.stream()
                .map(Atendimento::getValor)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
