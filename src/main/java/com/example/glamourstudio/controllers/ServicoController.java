package com.example.glamourstudio.controllers;


import com.example.glamourstudio.DTOs.AtualizarStatusRequest;
import com.example.glamourstudio.entities.EnumStatusServico;
import com.example.glamourstudio.entities.Servico;
import com.example.glamourstudio.repository.ServicoRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id){
        Servico servicoBanco = servicoRepository.findById(id).orElse(null);
        if(servicoBanco != null){
            return ResponseEntity.ok(servicoBanco);
        }
        return ResponseEntity.notFound().build();
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        Servico servicoBanco = servicoRepository.findById(id).orElse(null); //vai trazer o estado atual do id do serviço no banco
        if ( servicoBanco!=null){
            servicoBanco.setStatusServico(statusRequest.statusServico());
            servicoRepository.save(servicoBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @RequestBody Servico servico ){

        try {

            Servico servicoBanco = servicoRepository.findById(id).orElse(null);
            if ( servicoBanco != null){
                servicoBanco.setStatusServico(servico.getStatusServico());
                servicoBanco.setNome(servico.getNome());
                servicoBanco.setDescricao(servico.getDescricao());
                servicoBanco.setValor(servico.getValor());
                servicoBanco.setDuracaoMinutos(servico.getDuracaoMinutos());
                servicoRepository.save(servicoBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Servico servicoBanco = servicoRepository.findById(id).orElse(null);
        if ( servicoBanco!=null){
            servicoBanco.setStatusServico(EnumStatusServico.EXCLUIDO);
           servicoRepository.save(servicoBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }












}
