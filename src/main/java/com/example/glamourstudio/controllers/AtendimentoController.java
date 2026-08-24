package com.example.glamourstudio.controllers;

import com.example.glamourstudio.entities.Atendimento;
import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.repository.AtendimentoRepository;
import com.example.glamourstudio.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimento")
@Tag(name = "Atendimentos", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de atendimentos do sistema!")
public class AtendimentoController {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de atendimentos!",
            description = "Método responsável em efetuar a consulta de todos os atendimentos sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(atendimentoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de atendimentos!",
            description = "Método responsável em efetuar a criação de novos atendimentos."
    )
    public ResponseEntity<Atendimento> criar(@RequestBody Atendimento atendimento){

        var atendimentoBanco = atendimentoRepository.save(atendimento);
        return ResponseEntity.ok(atendimentoBanco);

    }

}
