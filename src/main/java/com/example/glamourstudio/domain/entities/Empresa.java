package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.EmpresaRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusEmpresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    private String cnpj;

    private String nomeFantasia;

    @OneToMany(mappedBy = "empresa") //pra apontar a coluna da tabela seguinte, com a chave estrangeira
    private List<Usuario> usuarios;

    private String role = "ROLE_EMPRESA";

    private EnumStatusEmpresa statusEmpresa = EnumStatusEmpresa.ATIVO;


    public Empresa(EmpresaRequest empresa) {
        this.setRazaoSocial(empresa.razaoSocial());
        this.setCnpj(empresa.cnpj());
        this.setNomeFantasia(empresa.nomeFantasia());
        this.setRole("ROLE_EMPRESA");
    }

}
