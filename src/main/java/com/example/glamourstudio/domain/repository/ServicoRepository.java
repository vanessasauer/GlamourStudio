package com.example.glamourstudio.domain.repository;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> getServicosByEmpresa_Id(Long empresa);

}
