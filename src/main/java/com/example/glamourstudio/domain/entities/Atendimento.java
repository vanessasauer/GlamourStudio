package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.AtendimentoRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusAtendimento;
import com.example.glamourstudio.application.DTOs.ServicoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    private String profissional;

    // id do Usuario que vai atender (usado para filtrar a agenda pelo logado)
    private Long profissionalId;

    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    private Servico servico;

    // valor cobrado neste atendimento (fica fixo mesmo se o preço do serviço mudar depois)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY) // só carrega a empresa se ela for usada.
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    private EnumStatusAtendimento statusAtendimento = EnumStatusAtendimento.AGENDADO;

    public Atendimento(AtendimentoRequest atendimento) {
        this.setProfissional(atendimento.profissional());
        this.setProfissionalId(atendimento.profissionalId());
        this.setDataHora(atendimento.dataHora());
        this.setValor(atendimento.valor());
    }


}
