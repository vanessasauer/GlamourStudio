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

    private String cliente;

    private String profissional;

    // id do Usuario que vai atender (usado para filtrar a agenda pelo logado)
    private Long profissionalId;

    private LocalDateTime dataHora;

    private String servico;

    // valor cobrado neste atendimento (fica fixo mesmo se o preço do serviço mudar depois)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.EAGER) //no momento em que carregar o serviço, irá carregar as empresas junto.
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    private EnumStatusAtendimento statusAtendimento = EnumStatusAtendimento.AGENDADO;

    public Atendimento(AtendimentoRequest atendimento) {
        this.setCliente(atendimento.cliente());
        this.setProfissional(atendimento.profissional());
        this.setProfissionalId(atendimento.profissionalId());
        this.setDataHora(atendimento.dataHora());
        this.setServico(atendimento.servico());
        this.setValor(atendimento.valor());
    }


}
