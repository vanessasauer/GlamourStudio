package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.Empresa;

public record EmpresaResponse(Long id, String razaoSocial, String cnpj, String nomeFantasia, EnumStatusEmpresa statusEmpresa) {

    public EmpresaResponse(Empresa empresaEntidade){

        this(

                empresaEntidade.getId(),
                empresaEntidade.getRazaoSocial(),
                empresaEntidade.getCnpj(),
                empresaEntidade.getNomeFantasia(),
                empresaEntidade.getStatusEmpresa()
        );
    }
}
