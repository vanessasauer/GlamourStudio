package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.valueobject.CPF;
import com.example.glamourstudio.domain.valueobject.EMAIL;

public record UsuarioLogado(Long id, EMAIL email, CPF cpf,
                            Long empresa_id,
                            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {
    public UsuarioLogado(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getEmpresa()!=null? usuario.getEmpresa().getId(): null,
                usuario.getAuthorities()
        );
    }
}
