package com.example.glamourstudio.infra.external;

import com.example.glamourstudio.domain.interfaces.IDddExternalRepository;
import com.example.glamourstudio.infra.dto.DddApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class DddExternalRepository implements IDddExternalRepository {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DddApiResponse buscarDdd(String ddd) {
        var url = "https://brasilapi.com.br/api/ddd/v1/" + ddd.replaceAll("[^0-9]", "");

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), DddApiResponse.class); // pega o texto do body e transforma em um objeto
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar DDD!");
        }

        throw new RuntimeException("DDD inválido!");
    }
}
