package com.example.glamourstudio.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DddApiResponse {

    @JsonProperty("state") // a BrasilAPI devolve os campos em inglês
    private String estado;

    @JsonProperty("cities")
    private List<String> cidades;

}
