package com.example.glamourstudio.domain.interfaces;

import com.example.glamourstudio.infra.dto.DddApiResponse;

public interface IDddExternalRepository {

    DddApiResponse buscarDdd(String ddd);
}
