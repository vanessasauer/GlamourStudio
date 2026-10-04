package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.ServicoResponse;
import com.example.glamourstudio.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServicoService {


    @Autowired
    private ServicoRepository servicoRepository;


    public List<ServicoResponse> listarTodosServicosTable() {

        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponse::new)
                .toList();
    }

}
