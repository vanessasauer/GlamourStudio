package com.example.glamourstudio.controllers;

import com.example.glamourstudio.DTOs.CadastrarRequest;
import com.example.glamourstudio.DTOs.EsqueciSenhaRequest;
import com.example.glamourstudio.DTOs.LoginRequest;
import com.example.glamourstudio.entities.Usuario;
import com.example.glamourstudio.repository.UsuarioRepository;
import com.example.glamourstudio.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação", name = "Autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de usuários")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest) {

        if (loginRequest.email().equals("string")&& loginRequest.senha().equals("string")) {
            // gerar token
            var token = tokenService.gerarToken(loginRequest.email());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")  // define que este método responde a requisições POST em /auth/cadastrar
    @Operation(description = "Cadastro de novo usuário", summary = "Cadastrar usuário")
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarRequest cadastrarRequest) {
        // @RequestBody: converte o JSON recebido no corpo da requisição em um objeto CadastrarRequest

        Usuario usuario = new Usuario(); // cria uma entidade Usuario vazia, que será preenchida e salva no banco

        // copia os dados do DTO (o que chegou na requisição) para a entidade (o que será persistido)
        usuario.setNome(cadastrarRequest.nome());
        usuario.setCpf(cadastrarRequest.cpf());
        usuario.setEmail(cadastrarRequest.email());
        usuario.setSenha(cadastrarRequest.senha());

        var usuarioSalvo = usuarioRepository.save(usuario);
        // save(): insere o usuário no banco (id ainda era nulo) e retorna o objeto já com o id gerado

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        // retorna HTTP 201 (Created) e o usuário salvo no corpo da resposta
    }

    @PostMapping("/esqueci-senha")
    @Operation(description = "Redefine a senha do usuário pelo e-mail", summary = "Esqueci a senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody EsqueciSenhaRequest esqueciSenhaRequest) {
        // @RequestBody: converte o JSON (email + novaSenha) em um objeto EsqueciSenhaRequest

        var usuarioOptional = usuarioRepository.findByEmail(esqueciSenhaRequest.email());
        // busca no banco um usuário com esse e-mail
        // Optional: pode vir preenchido (achou) ou vazio (não achou) — evita retornar null

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail não encontrado");
            // se não encontrou ninguém com esse e-mail, interrompe aqui e retorna HTTP 400
        }

        Usuario usuario = usuarioOptional.get();
        // "abre" o Optional e pega o Usuario de dentro (seguro, pois já garantimos que não está vazio)

        usuario.setSenha(esqueciSenhaRequest.novaSenha());
        // substitui a senha atual pela nova senha enviada na requisição

        usuarioRepository.save(usuario);
        // como o usuario já tem id (veio do banco), o save() aqui faz um UPDATE, não um INSERT

        return ResponseEntity.ok("Senha atualizada com sucesso");
        // retorna HTTP 200 com uma mensagem de confirmação
    }






}
