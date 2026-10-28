package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Cliente;

import java.time.LocalDate;

public record ClienteResponse(Long id, String nome, LocalDate dataNascimento, String email, String telefone, EnumStatusCliente statusCliente) {

    public ClienteResponse(Cliente clienteEntidade){

        this(
                clienteEntidade.getId(),
                clienteEntidade.getNome(),
                clienteEntidade.getDataNascimento(),
                clienteEntidade.getEmail(),
                clienteEntidade.getTelefone().toString(),
                clienteEntidade.getStatusCliente()
        );
    }
}
