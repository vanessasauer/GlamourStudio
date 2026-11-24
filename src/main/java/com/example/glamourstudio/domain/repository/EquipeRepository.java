package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    List<Equipe> getEquipesByEmpresa_Id(Long empresa);

    // busca a equipe pelo id, mas só se ela for da empresa informada
    // SELECT * FROM equipe WHERE id = ? AND empresa_id = ?
    Optional<Equipe> getEquipeByIdAndEmpresa_Id(Long id, Long empresaId);
}
