package com.example.glamourstudio.controllers;


import com.example.glamourstudio.entities.Servico;
import com.example.glamourstudio.entities.Usuario;
import com.example.glamourstudio.repository.ServicoRepository;
import com.example.glamourstudio.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico")
@Tag(name = "Serviços", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de serviços do sistema!")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de serviços!",
            description = "Método responsável em efetuar a consulta de todos os serviços sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(servicoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de serviços!",
            description = "Método responsável em efetuar a criação de novos serviços."
    )
    public ResponseEntity<Servico> criar(@RequestBody Servico servico){

        var servicoBanco = servicoRepository.save(servico);
        return ResponseEntity.ok(servicoBanco);

    }












}
