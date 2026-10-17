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

    private LocalDateTime dataHora;

    private String servico;

    private String role = "ROLE_ATENDIMENTO";

    private EnumStatusAtendimento statusAtendimento = EnumStatusAtendimento.AGENDADO;

    public Atendimento(AtendimentoRequest atendimento) {
        this.setCliente(atendimento.cliente());
        this.setProfissional(atendimento.profissional());
        this.setDataHora(atendimento.dataHora());
        this.setServico(atendimento.servico());
        this.setRole("ROLE_ATENDIMENTO");
    }

}
