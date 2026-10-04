package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.ClienteResponse;
import com.example.glamourstudio.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public List<ClienteResponse> listarTodosClientesTable() {

        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponse::new)
                .toList();
    }
}
