package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.CriarAdminRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusUsuario;
import com.example.glamourstudio.application.DTOs.UsuarioRequest;
import com.example.glamourstudio.domain.valueobject.CPF;
import com.example.glamourstudio.domain.valueobject.EMAIL;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded // Acessa o CPF e armazena a propriedade cpf
    private CPF cpf;

    private String senha;

    @Embedded
    private EMAIL email;

    private String role = "ROLE_USER";

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    //é uma regra de negócio para criar o usuário admin
    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(new CPF(criarAdminRequest.cpf()));
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(new EMAIL(criarAdminRequest.email()));
        this.setRole("ROLE_ADMIN");
    }

    public Usuario(UsuarioRequest usuario) {
        this.setCpf(new CPF(usuario.cpf()));
        this.setNome(usuario.nome());
        this.setSenha(usuario.senha());
        this.setEmail(new EMAIL(usuario.email()));
        this.setRole("ROLE_USER");
    }

}