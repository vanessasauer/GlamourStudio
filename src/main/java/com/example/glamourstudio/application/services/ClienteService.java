package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Cliente;
import com.example.glamourstudio.domain.repository.ClienteRepository;
import com.example.glamourstudio.domain.repository.EmpresaRepository;
import com.example.glamourstudio.domain.valueobject.TELEFONE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaRepository empresaRepository;


    public List<ClienteResponse> listarTodosClientesTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return clienteRepository.findAll()
                    .stream()
                    .map(ClienteResponse::new)
                    .toList();
        }

        return clienteRepository.getClientesByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(ClienteResponse::new)
                .toList();
    }

    public ClienteResponse salvarCliente(ClienteRequest cliente, Long id) {

        //Novo Cliente
        if(id == 0L) {
            Cliente clienteBanco = new Cliente(cliente);

            // o cliente fica com a empresa de quem está cadastrando
            var usuarioLogado = usuarioService.getUsuarioLogado();
            clienteBanco.setEmpresa(empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null));

            clienteRepository.save(clienteBanco);

            return new ClienteResponse(clienteBanco);
        }else {
            //Cliente existente
            Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
            if(clienteBanco!= null ){
                clienteBanco.setNome(cliente.nome());
                clienteBanco.setDataNascimento(cliente.dataNascimento());
                clienteBanco.setEmail(cliente.email());
                clienteBanco.setTelefone(new TELEFONE(cliente.telefone()));
                clienteRepository.save(clienteBanco);
                return  new ClienteResponse(clienteBanco);
            }

        }

        return null;
    }

    public ClienteResponse buscarPorId(Long id) {

        var cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return null;
        }

        return new ClienteResponse(cliente);
    }

    public boolean atualizarStatus(EnumStatusCliente statusCliente, Long id) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco!= null ){
            clienteBanco.setStatusCliente(statusCliente);
            clienteRepository.save(clienteBanco);
            return  true;
        }
        return false;


    }

}
