package com.example.glamourstudio.domain.entities;


import com.example.glamourstudio.domain.valueobject.CPF;
import com.example.glamourstudio.domain.valueobject.EMAIL;
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
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String descricao;


    @ManyToMany(mappedBy = "equipes")
    private Set<Usuario> profissionais = new HashSet<>();
}
