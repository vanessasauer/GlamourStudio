package com.example.glamourstudio.presentation;

import com.example.glamourstudio.application.DTOs.AtualizarStatusRequest;
import com.example.glamourstudio.application.DTOs.CriarAdminRequest;
import com.example.glamourstudio.application.DTOs.CriarAdminResponse;
import com.example.glamourstudio.application.DTOs.UsuarioResponse;
import com.example.glamourstudio.application.services.UsuarioService;
import com.example.glamourstudio.domain.entities.EnumStatusUsuario;
import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de usuários do sistema!")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;



    @GetMapping
    @Operation(summary = "Método de consulta de lista de usuários!",
            description = "Método responsável em efetuar a consulta de todos os usuários sem filtro.")
    public ResponseEntity<List<UsuarioResponse>> listarTodos(){

        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    @PostMapping("/admin")
    public ResponseEntity<CriarAdminResponse> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest){

        try {

            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);

            return ResponseEntity.ok(respostaSalvar);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }






    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de usuários por ID",
            description = "Método responsável em efetuar a consulta dos usuários filtrando por ID.")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de Criação de usuários!",
            description = "Método responsável em efetuar a criação de novos usuários."
    )
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){

        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do usuário por ID.",
            description = "Método responsável por atualizar o status do usuário pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null); //vai trazer o estado atual do id do usuário no banco
        if ( usuarioBanco!=null){
            usuarioBanco.setStatus(statusRequest.status());
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um usuário por ID.",
            description = "Método responsável por atualizar os dados de um usuário existente filtrando pelo seu ID.")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario ){

        try {

            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if ( usuarioBanco != null){
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());
                usuarioRepository.save(usuarioBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os usuário filtrando pelo seu ID.",
            description = "Método responsável por excluir um usuário existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if ( usuarioBanco!=null){
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }



}
