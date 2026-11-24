package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> getClientesByEmpresa_Id(Long empresa);

    // busca o cliente pelo id, mas só se ele for da empresa informada
    // SELECT * FROM cliente WHERE id = ? AND empresa_id = ?
    Optional<Cliente> getClienteByIdAndEmpresa_Id(Long id, Long empresaId);
}
