package com.example.glamourstudio.presentation;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.application.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Grupo de APIs responsável por controlar a estrutura de criação e consulta de clientes do sistema!")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de clientes!",
            description = "Método responsável em efetuar a consulta de todos os clientes sem filtro.")
    public ResponseEntity<List<ClienteResponse>> listarTodos(){

        return ResponseEntity.ok(clienteService.listarTodosClientesTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de clientes por ID",
            description = "Método responsável em efetuar a consulta dos clientes filtrando por ID.")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        ClienteResponse clienteBanco = clienteService.buscarPorId(id);
        if(clienteBanco != null){
            return ResponseEntity.ok(clienteBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de clientes!",
            description = "Método responsável em efetuar a criação de novos clientes."
    )
    public ResponseEntity<?> criar(@RequestBody ClienteRequest cliente){

        try {
            ClienteResponse clienteBanco = clienteService.salvarCliente(cliente, 0L);
            return ResponseEntity.ok(clienteBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do cliente por ID.",
            description = "Método responsável por atualizar o status do cliente pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        var atualizarCliente = clienteService.atualizarStatus(statusRequest.statusCliente(),id);

        if (atualizarCliente){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um cliente por ID.",
            description = "Método responsável por atualizar os dados de um cliente existente filtrando pelo seu ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ClienteRequest cliente ){

        try {
            ClienteResponse clienteBanco = clienteService.salvarCliente(cliente,id);
            return ResponseEntity.ok(clienteBanco);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os clientes filtrando pelo seu ID.",
            description = "Método responsável por excluir um cliente existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarCliente = clienteService.atualizarStatus(EnumStatusCliente.EXCLUIDO,id);

        if(atualizarCliente){
            return ResponseEntity.ok().build();
        }

        return  ResponseEntity.notFound().build();

    }


}
