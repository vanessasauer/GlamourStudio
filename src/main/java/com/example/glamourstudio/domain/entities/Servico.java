package com.example.glamourstudio.domain.entities;


import com.example.glamourstudio.application.DTOs.CriarAdminRequest;
import com.example.glamourstudio.application.DTOs.CriarServicoRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private Integer duracaoMinutos;

    private String role = "ROLE_USER";

    private EnumStatusServico statusServico = EnumStatusServico.ATIVO;

    public Servico(CriarServicoRequest criarServicoRequest) {
        this.setNome(criarServicoRequest.nome());
        this.setDescricao(criarServicoRequest.descricao());
        this.setValor(criarServicoRequest.valor());
        this.setDuracaoMinutos(criarServicoRequest.duracaoMinutos());
        this.setRole("ROLE_ADMIN");
    }

}
