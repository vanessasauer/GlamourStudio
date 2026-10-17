package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Atendimento;
import com.example.glamourstudio.domain.entities.Usuario;
import com.example.glamourstudio.domain.repository.AtendimentoRepository;
import com.example.glamourstudio.domain.valueobject.CPF;
import com.example.glamourstudio.domain.valueobject.EMAIL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;


    public List<AtendimentoResponse> listarTodosAtendimentosTable() {

        return atendimentoRepository.findAll()
                .stream()
                .map(AtendimentoResponse::new)
                .toList();
    }

    public AtendimentoResponse salvarAtendimento(AtendimentoRequest atendimento, Long id) {

        //Novo Atendimento
        if(id == 0L) {
            Atendimento atendimentoBanco = new Atendimento(atendimento);
            atendimentoRepository.save(atendimentoBanco);

            return new AtendimentoResponse(atendimentoBanco);
        }else {
            //Atendimento existente
            Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
            if(atendimentoBanco!= null ){
                atendimentoBanco.setCliente(atendimento.cliente());
                atendimentoBanco.setProfissional(atendimento.profissional());
                atendimentoBanco.setDataHora(atendimento.dataHora());
                atendimentoBanco.setServico(atendimento.servico());
                atendimentoRepository.save(atendimentoBanco);
                return  new AtendimentoResponse(atendimentoBanco);
            }

        }

        return null;
    }

    public AtendimentoResponse buscarPorId(Long id) {

        var atendimento = atendimentoRepository.findById(id).orElse(null);
        if (atendimento == null) {
            return null;
        }

        return new AtendimentoResponse(atendimento);
    }

    public boolean atualizarStatus(EnumStatusAtendimento statusAtendimento, Long id) {

        Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
        if(atendimentoBanco!= null ){
            atendimentoBanco.setStatusAtendimento(statusAtendimento);
            atendimentoRepository.save(atendimentoBanco);
            return  true;
        }
        return false;


    }
}
