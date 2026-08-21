package com.example.glamourstudio.controllers;

import com.example.glamourstudio.entities.Atendimento;
import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.repository.AtendimentoRepository;
import com.example.glamourstudio.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(atendimentoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Atendimento> criar(@RequestBody Atendimento atendimento){

        var atendimentoBanco = atendimentoRepository.save(atendimento);
        return ResponseEntity.ok(atendimentoBanco);

    }

}
