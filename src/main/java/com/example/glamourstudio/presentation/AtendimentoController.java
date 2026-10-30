package com.example.glamourstudio.presentation;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.services.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/atendimento")
@Tag(name = "Atendimentos", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de atendimentos do sistema!")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de atendimentos!",
            description = "Método responsável em efetuar a consulta de todos os atendimentos sem filtro.")
    public ResponseEntity<List<AtendimentoResponse>> listarTodos(){

        return ResponseEntity.ok(atendimentoService.listarTodosAtendimentosTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de atendimentos por ID",
            description = "Método responsável em efetuar a consulta dos atendimentos por ID.")
    public ResponseEntity<AtendimentoResponse> buscarPorId(@PathVariable Long id){
        AtendimentoResponse atendimentoBanco = atendimentoService.buscarPorId(id);
        if(atendimentoBanco != null){
            return ResponseEntity.ok(atendimentoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de atendimentos!",
            description = "Método responsável em efetuar a criação de novos atendimentos."
    )
    public ResponseEntity<?> criar(@RequestBody AtendimentoRequest atendimento){

        try {
            AtendimentoResponse atendimentoBanco = atendimentoService.salvarAtendimento(atendimento, 0L);
            return ResponseEntity.ok(atendimentoBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do atendimento por ID.",
            description = "Método responsável por atualizar o status do atendimento pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        var atualizarAtendimento = atendimentoService.atualizarStatus(statusRequest.statusAtendimento(),id);

        if (atualizarAtendimento){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um atendimento por ID.",
            description = "Método responsável por atualizar os dados de um atendimento existente pelo ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtendimentoRequest atendimento ){

        try {
            AtendimentoResponse atendimentoBanco = atendimentoService.salvarAtendimento(atendimento,id);
            return ResponseEntity.ok(atendimentoBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os atendimentos filtrando pelo seu ID.",
            description = "Método responsável por excluir um atendimento existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarAtendimento = atendimentoService.atualizarStatus(EnumStatusAtendimento.CANCELADO,id);

        if(atualizarAtendimento){
            return ResponseEntity.ok().build();
        }

        return  ResponseEntity.notFound().build();

    }

    @GetMapping("/minha-agenda")
    @Operation(summary = "Agenda do profissional logado por período.", description = "Retorna os atendimentos do período informado (ou de hoje, se não informar) em que o usuário logado é o profissional.")
    public ResponseEntity<List<AtendimentoResponse>> minhaAgenda(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim){
        return ResponseEntity.ok(atendimentoService.listarMinhaAgenda(inicio, fim));
    }

    @GetMapping("/painel")
    @Operation(summary = "Painel de faturamento do mês do profissional logado.",
            description = "Retorna a soma dos atendimentos ATENDIDO do usuário logado no mês vigente.")
    public ResponseEntity<PainelFaturamentoResponse> painel(){

        return ResponseEntity.ok(atendimentoService.gerarPainelMensal());
    }


}
