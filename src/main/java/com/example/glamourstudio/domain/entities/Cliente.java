package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.ClienteRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusCliente;
import com.example.glamourstudio.domain.valueobject.TELEFONE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    @Embedded
    private TELEFONE telefone;

    private String role = "ROLE_CLIENTE";

    private EnumStatusCliente statusCliente = EnumStatusCliente.ATIVO;

    public Cliente(ClienteRequest cliente) {
        this.setNome(cliente.nome());
        this.setDataNascimento(cliente.dataNascimento());
        this.setEmail(cliente.email());
        this.setTelefone(new TELEFONE(cliente.telefone()));
        this.setRole("ROLE_CLIENTE");
    }
}
