package com.example.glamourstudio.repository;

import com.example.glamourstudio.entities.EnumStatusServico;
import com.example.glamourstudio.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
}
