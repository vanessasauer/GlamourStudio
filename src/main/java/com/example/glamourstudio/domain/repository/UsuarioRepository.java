package com.example.glamourstudio.domain.repository;

import com.example.glamourstudio.application.DTOs.EnumStatusUsuario;
import com.example.glamourstudio.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByEmailAndSenha(String email, String senha);

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    List<Usuario> getUsuariosByEmpresa_Id(Long empresa); // faz um filtro pela empresa_id.

    // busca o usuário pelo id, mas só se ele for da empresa informada
    // SELECT * FROM usuario WHERE id = ? AND empresa_id = ?
    Optional<Usuario> getUsuarioByIdAndEmpresa_Id(Long id, Long empresaId);

    // confere se existe um usuário com esse id dentro da empresa informada
    boolean existsUsuarioByIdAndEmpresa_Id(Long id, Long empresaId);

}
