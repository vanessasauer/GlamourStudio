package com.example.glamourstudio.domain.entities;


import com.example.glamourstudio.application.DTOs.EnumStatusEquipe;
import com.example.glamourstudio.application.DTOs.EquipeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String descricao;

    @ManyToMany(mappedBy = "equipes")
    private Set<Usuario> profissionais = new HashSet<>();

    private String role = "ROLE_EQUIPE";

    private EnumStatusEquipe statusEquipe = EnumStatusEquipe.ATIVO;

    public Equipe(EquipeRequest equipe) {
        this.setNome(equipe.nome());
        this.setDescricao(equipe.descricao());
        this.setRole("ROLE_EQUIPE");
    }
}
