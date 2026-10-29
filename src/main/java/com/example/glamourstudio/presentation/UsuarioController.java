package com.example.glamourstudio.presentation;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.services.UsuarioService;
import com.example.glamourstudio.application.DTOs.EnumStatusUsuario;
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
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id){
        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);
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
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest usuario){
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario, 0L);
            return ResponseEntity.ok(usuarioBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do usuário por ID.",
            description = "Método responsável por atualizar o status do usuário pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

       var atualizarUsuario = usuarioService.atualizarStatus(statusRequest.status(),id);

       if (atualizarUsuario){
           return ResponseEntity.ok().build();
       }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um usuário por ID.",
            description = "Método responsável por atualizar os dados de um usuário existente filtrando pelo seu ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest usuario ){

        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,id);
            return ResponseEntity.ok(usuarioBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os usuário filtrando pelo seu ID.",
            description = "Método responsável por excluir um usuário existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarUsuario = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO,id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }

        return  ResponseEntity.notFound().build();

    }

    @PatchMapping("/{id}/equipe")
    @Operation(summary = "Método para adicionar um usuário em uma equipe.",
            description = "Método responsável por vincular um usuário existente a uma equipe pelo ID.")
    public ResponseEntity<?> adicionarEquipe(@PathVariable Long id, @RequestBody AdicionarUsuarioEquipeRequest equipeRequest) {

        boolean retornoEquipe = usuarioService.adicionarUsuarioNaEquipe(id, equipeRequest);

        if (retornoEquipe) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }



}
