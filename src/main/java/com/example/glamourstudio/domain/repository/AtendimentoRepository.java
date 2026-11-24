package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Atendimento;
import com.example.glamourstudio.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    // Agenda do profissional num intervalo (usado para o dia), em ordem de horário.
    List<Atendimento> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(Long profissionalId, LocalDateTime inicio, LocalDateTime fim);

    List<Atendimento> getAtendimentosByEmpresa_Id(Long empresa);

    // busca o atendimento pelo id, mas só se ele for da empresa informada
    // SELECT * FROM atendimento WHERE id = ? AND empresa_id = ?
    Optional<Atendimento> getAtendimentoByIdAndEmpresa_Id(Long id, Long empresaId);
}
