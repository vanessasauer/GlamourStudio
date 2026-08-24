package com.example.glamourstudio.controllers;

import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.entities.Usuario;
import com.example.glamourstudio.repository.ClienteRepository;
import com.example.glamourstudio.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de clientes do sistema!")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de clientes!",
            description = "Método responsável em efetuar a consulta de todos os clientes sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de clientes!",
            description = "Método responsável em efetuar a criação de novos clientes."
    )
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente){

        var clienteBanco = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteBanco);

    }







}
