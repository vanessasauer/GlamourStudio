package com.example.glamourstudio.repository;

import com.example.glamourstudio.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Optional<Usuario>: pode conter um Usuario (se o e-mail existir) ou vir vazio (se não existir).
    // Evita retornar null e força checar com isEmpty()/isPresent() antes de usar .get()

    Optional<Usuario> findByEmail(String email);
}
