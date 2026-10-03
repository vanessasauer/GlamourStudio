package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.EnumStatusUsuario;
import com.example.glamourstudio.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String cpf, String email, EnumStatusUsuario status) {

    public UsuarioResponse(Usuario usuarioEntidade){

        this(
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getStatus()
        );
    }

    //Nesta record é possível mascarar o cpf, email...
}
