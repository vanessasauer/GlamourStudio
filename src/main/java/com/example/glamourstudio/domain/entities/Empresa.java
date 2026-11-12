package com.example.glamourstudio.domain.entities;

import com.example.glamourstudio.application.DTOs.EmpresaRequest;
import com.example.glamourstudio.application.DTOs.EnumStatusEmpresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // serviços padrão (do admin) que esta empresa escolheu não usar
    @ManyToMany
    @JoinTable(
            name = "tb_empresa_servico_desativado",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicosPadraoDesativados = new HashSet<>();

    private String role = "ROLE_EMPRESA";

    private EnumStatusEmpresa statusEmpresa = EnumStatusEmpresa.ATIVO;


    public Empresa(EmpresaRequest empresa) {
        this.setRazaoSocial(empresa.razaoSocial());
        this.setCnpj(empresa.cnpj());
        this.setNomeFantasia(empresa.nomeFantasia());
        this.setRole("ROLE_EMPRESA");
    }

}
