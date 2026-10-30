package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.application.DTOs.EnumStatusAtendimento;
import com.example.glamourstudio.domain.entities.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    // Agenda do profissional num intervalo (usado para o dia), em ordem de horário.
    List<Atendimento> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(Long profissionalId, LocalDateTime inicio, LocalDateTime fim);

    // Atendimentos de um profissional, com um status, num intervalo (usado no painel do mês).
    List<Atendimento> findByProfissionalIdAndStatusAtendimentoAndDataHoraBetween(Long profissionalId, EnumStatusAtendimento status, LocalDateTime inicio, LocalDateTime fim);

}
