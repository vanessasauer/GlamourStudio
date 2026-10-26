package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Equipe;
import com.example.glamourstudio.domain.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;


    public List<EquipeResponse> listarTodasEquipesTable() {

        return equipeRepository.findAll()
                .stream()
                .map(EquipeResponse::new)
                .toList();
    }

    public EquipeResponse salvarEquipe(EquipeRequest equipe, Long id) {

        //Nova Equipe
        if(id == 0L) {
            Equipe equipeBanco = new Equipe(equipe);
            equipeRepository.save(equipeBanco);

            return new EquipeResponse(equipeBanco);
        }else {
            //Equipe existente
            Equipe equipeBanco = equipeRepository.findById(id).orElse(null);
            if(equipeBanco!= null ){
                equipeBanco.setNome(equipe.nome());
                equipeBanco.setDescricao(equipe.descricao());
                equipeRepository.save(equipeBanco);
                return  new EquipeResponse(equipeBanco);
            }

        }

        return null;
    }

    public EquipeResponse buscarPorId(Long id) {

        var equipe = equipeRepository.findById(id).orElse(null);
        if (equipe == null) {
            return null;
        }

        return new EquipeResponse(equipe);
    }

    public boolean atualizarStatus(EnumStatusEquipe statusEquipe, Long id) {

        Equipe equipeBanco = equipeRepository.findById(id).orElse(null);
        if(equipeBanco!= null ){
            equipeBanco.setStatusEquipe(statusEquipe);
            equipeRepository.save(equipeBanco);
            return  true;
        }
        return false;


    }
}
