package com.example.glamourstudio.domain.interfaces;

import com.example.glamourstudio.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {


    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);



}
