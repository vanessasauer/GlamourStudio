package com.example.glamourstudio.domain.repository;
import com.example.glamourstudio.domain.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
