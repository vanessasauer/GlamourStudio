package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.AtendimentoResponse;
import com.example.glamourstudio.domain.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;


    public List<AtendimentoResponse> listarTodosAtendimentosTable() {

        return atendimentoRepository.findAll()
                .stream()
                .map(AtendimentoResponse::new)
                .toList();
    }
}
