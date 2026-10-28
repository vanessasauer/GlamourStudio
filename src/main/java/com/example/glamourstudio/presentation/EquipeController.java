package com.example.glamourstudio.presentation;


import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.services.EmpresaService;
import com.example.glamourstudio.application.services.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@Tag(name = "Equipes", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de equipes do sistema!")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de equipes!",
            description = "Método responsável em efetuar a consulta de todas as equipes sem filtro.")
    public ResponseEntity<List<EquipeResponse>> listarTodos(){

        return ResponseEntity.ok(equipeService.listarTodasEquipesTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de equipes por ID",
            description = "Método responsável em efetuar a consulta das equipes filtrando por ID.")
    public ResponseEntity<EquipeResponse> buscarPorId(@PathVariable Long id){
        EquipeResponse equipeBanco = equipeService.buscarPorId(id);
        if(equipeBanco != null){
            return ResponseEntity.ok(equipeBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de equipes!",
            description = "Método responsável em efetuar a criação de novas equipes."
    )
    public ResponseEntity<?> criar(@RequestBody EquipeRequest equipe){

        try {
            EquipeResponse equipeBanco = equipeService.salvarEquipe(equipe, 0L);
            return ResponseEntity.ok(equipeBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status da equipe por ID.",
            description = "Método responsável por atualizar o status da equipe pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        var atualizarEquipe = equipeService.atualizarStatus(statusRequest.statusEquipe(),id);

        if (atualizarEquipe){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de uma equipe por ID.",
            description = "Método responsável por atualizar os dados de uma equipe existente filtrando pelo seu ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EquipeRequest equipe ){

        try {
            EquipeResponse equipeBanco = equipeService.salvarEquipe(equipe,id);
            return ResponseEntity.ok(equipeBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir as equipes filtrando pelo seu ID.",
            description = "Método responsável por excluir uma equipe existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarEquipe = equipeService.atualizarStatus(EnumStatusEquipe.EXCLUIDO,id);

        if(atualizarEquipe){
            return ResponseEntity.ok().build();
        }

        return  ResponseEntity.notFound().build();

    }

}
