package com.example.glamourstudio.domain.repository;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    // serviços da empresa + serviços padrão (cadastrados pelo admin, sem empresa)
    List<Servico> getServicosByEmpresa_IdOrEmpresaIsNull(Long empresa);

    // serviço da própria empresa
    // SELECT * FROM servico WHERE id = ? AND empresa_id = ?
    Optional<Servico> getServicoByIdAndEmpresa_Id(Long id, Long empresaId);

    // serviço padrão (cadastrado pelo admin, sem empresa)
    // SELECT * FROM servico WHERE id = ? AND empresa_id IS NULL
    Optional<Servico> getServicoByIdAndEmpresaIsNull(Long id);

}