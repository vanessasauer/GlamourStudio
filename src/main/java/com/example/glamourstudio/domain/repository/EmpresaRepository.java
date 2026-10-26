package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
