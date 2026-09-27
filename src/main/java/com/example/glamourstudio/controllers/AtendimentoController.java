package com.example.glamourstudio.controllers;

import com.example.glamourstudio.DTOs.AtualizarStatusRequest;
import com.example.glamourstudio.entities.Atendimento;
import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.entities.EnumStatusAtendimento;
import com.example.glamourstudio.entities.EnumStatusCliente;
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

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de atendimentos por ID",
            description = "Método responsável em efetuar a consulta dos atendimentos por ID.")
    public ResponseEntity<Atendimento> buscarPorId(@PathVariable Long id){
        Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
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
    public ResponseEntity<Atendimento> criar(@RequestBody Atendimento atendimento){

        var atendimentoBanco = atendimentoRepository.save(atendimento);
        return ResponseEntity.ok(atendimentoBanco);

    }


    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do atendimento por ID.",
            description = "Método responsável por atualizar o status do atendimento pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null); //vai trazer o estado atual do id do atendimento no banco
        if ( atendimentoBanco!=null){
            atendimentoBanco.setStatusAtendimento(statusRequest.statusAtendimento());
            atendimentoRepository.save(atendimentoBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um atendimento por ID.",
            description = "Método responsável por atualizar os dados de um atendimento existente pelo ID.")
    public ResponseEntity<Atendimento> atualizar(@PathVariable Long id, @RequestBody Atendimento atendimento ){

        try {

            Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
            if ( atendimentoBanco != null){
                atendimentoBanco.setStatusAtendimento(atendimento.getStatusAtendimento());
                atendimentoBanco.setCliente(atendimento.getCliente());
                atendimentoBanco.setProfissional(atendimento.getProfissional());
                atendimentoBanco.setDataHora(atendimento.getDataHora());
                atendimentoBanco.setServico(atendimento.getServico());
                atendimentoRepository.save(atendimentoBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os atendimentos filtrando pelo seu ID.",
            description = "Método responsável por excluir um atendimento existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
        if ( atendimentoBanco!=null){
            atendimentoBanco.setStatusAtendimento(EnumStatusAtendimento.CANCELADO);
            atendimentoRepository.save(atendimentoBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


}
