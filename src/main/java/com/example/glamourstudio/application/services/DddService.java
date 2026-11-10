package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.DddResponse;
import com.example.glamourstudio.domain.interfaces.IDddExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DddService {

    @Autowired
    private IDddExternalRepository dddExternalRepository;


    public DddResponse buscarDdd(String ddd) {

        var dddCompleto = dddExternalRepository.buscarDdd(ddd);

        return new DddResponse(ddd,
                dddCompleto.getEstado(),
                dddCompleto.getCidades());

    }
}
