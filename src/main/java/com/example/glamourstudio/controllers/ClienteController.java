package com.example.glamourstudio.controllers;

import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.entities.Usuario;
import com.example.glamourstudio.repository.ClienteRepository;
import com.example.glamourstudio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente){

        var clienteBanco = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteBanco);

    }







}
