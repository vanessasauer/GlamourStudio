package com.example.glamourstudio.controllers;

import com.example.glamourstudio.DTOs.AtualizarStatusRequest;
import com.example.glamourstudio.entities.Cliente;
import com.example.glamourstudio.entities.EnumStatusCliente;
import com.example.glamourstudio.repository.ClienteRepository;
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

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de clientes por ID",
            description = "Método responsável em efetuar a consulta dos clientes filtrando por ID.")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
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
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente){

        var clienteBanco = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do cliente por ID.",
            description = "Método responsável por atualizar o status do cliente pelo seu ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null); //vai trazer o estado atual do id do cliente no banco
        if ( clienteBanco!=null){
            clienteBanco.setStatusCliente(statusRequest.statusCliente());
            clienteRepository.save(clienteBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualizar as informações de um cliente por ID.",
            description = "Método responsável por atualizar os dados de um cliente existente filtrando pelo seu ID.")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente ){

        try {

            Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
            if ( clienteBanco != null){
               clienteBanco.setStatusCliente(cliente.getStatusCliente());
                clienteBanco.setNome(cliente.getNome());
                clienteBanco.setDataNascimento(cliente.getDataNascimento());
                clienteBanco.setEmail(cliente.getEmail());
                clienteBanco.setTelefone(cliente.getTelefone());
                clienteRepository.save(clienteBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de excluir os clientes filtrando pelo seu ID.",
            description = "Método responsável por excluir um cliente existente pelo ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if ( clienteBanco!=null){
            clienteBanco.setStatusCliente(EnumStatusCliente.EXCLUIDO);
            clienteRepository.save(clienteBanco);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }







}
