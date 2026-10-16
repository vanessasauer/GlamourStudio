package com.example.glamourstudio.application.services;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.DTOs.EnumStatusUsuario;
import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secretkey}")
    private String secret;

    public LoginResponse validarUsuarioAutenticadoRetornaToken (LoginRequest request) {

        if (usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }


    public List<UsuarioResponse> listarTodosUsuariosTable() {

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if (!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminResponse(0L,"Usuário salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(),"Usuário salvo com sucesso");
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario) {

        Usuario usuarioBanco = new Usuario(usuario);

        usuarioRepository.save(usuarioBanco);
        return new UsuarioResponse(usuarioBanco);
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status) {

    }

}
