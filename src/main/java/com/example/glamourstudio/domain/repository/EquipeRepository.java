package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Equipe;
import com.example.glamourstudio.domain.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    List<Equipe> getEquipesByEmpresa_Id(Long empresa);
}
