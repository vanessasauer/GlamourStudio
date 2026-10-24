package com.example.glamourstudio.application.services;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.DTOs.EnumStatusUsuario;
import com.example.glamourstudio.domain.entities.Equipe;
import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.repository.EquipeRepository;
import com.example.glamourstudio.domain.repository.UsuarioRepository;
import com.example.glamourstudio.domain.valueobject.CPF;
import com.example.glamourstudio.domain.valueobject.EMAIL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EquipeRepository equipeRepository;

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

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }

        return usuarioRepository.getUsuariosByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminResponse(0L,"Usuario Salvo com sucesso!");

        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(),"Usuario Salvo com sucesso!");
    }

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        var usuarioLogado = getUsuarioLogado();
        //Novo Usuario
        if(id == 0L) {
            Usuario usuarioBanco = new Usuario(usuario);
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        }else {
            //Usuario existente
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if(usuarioBanco!= null ){
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(new CPF(usuario.cpf()));
                usuarioBanco.setEmail(new EMAIL(usuario.email()));
                usuarioBanco.setSenha(usuario.senha());
                usuarioRepository.save(usuarioBanco);
                return  new UsuarioResponse(usuarioBanco);
            }

        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco!= null ){
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return  true;
        }
        return false;

    }

    public boolean adicionarUsuarioNaEquipe(Long id, EquipeRequest equipeRequest) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        Equipe equipeBanco = equipeRepository.findById(equipeRequest.equipe_id()).orElse(null);
        if(usuarioBanco!= null && equipeBanco!= null) {

            var equipe =  usuarioBanco.getEquipes().stream()
                    .anyMatch(u->u.getId().equals(equipeBanco.getId()));

            if(equipe){
                return true;
            }else {
                usuarioBanco.getEquipes().add(equipeBanco);

                usuarioRepository.save(usuarioBanco);


                return true;
            }

        }
        return false;

    }

}
