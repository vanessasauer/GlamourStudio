package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Empresa;
import com.example.glamourstudio.domain.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {


}
