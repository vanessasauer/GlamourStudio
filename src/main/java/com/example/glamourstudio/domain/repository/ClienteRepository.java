package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Cliente;
import com.example.glamourstudio.domain.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> getClientesByEmpresa_Id(Long empresa);
}
