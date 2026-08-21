package com.example.glamourstudio.controllers;


import com.example.glamourstudio.entities.Servico;
import com.example.glamourstudio.entities.Usuario;
import com.example.glamourstudio.repository.ServicoRepository;
import com.example.glamourstudio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(servicoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Servico> criar(@RequestBody Servico servico){

        var servicoBanco = servicoRepository.save(servico);
        return ResponseEntity.ok(servicoBanco);

    }












}
