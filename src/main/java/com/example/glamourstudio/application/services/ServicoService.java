package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class ServicoService {


    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secretkey}")
    private String secret;


    public List<ServicoResponse> listarTodosServicosTable() {

        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponse::new)
                .toList();
    }

    public CriarServicoResponse criarServicoAdmin(CriarServicoRequest criarServicoRequest) {

        if (!criarServicoRequest.secretkey().equals(secret)){
            return new CriarServicoResponse(0L,"Serviço salvo com sucesso!");
        }

        Servico servicoAdminSalvar = new Servico(criarServicoRequest);
        servicoRepository.save(servicoAdminSalvar);

        return new CriarServicoResponse(servicoAdminSalvar.getId(),"Serviço salvo com sucesso");
    }

}
