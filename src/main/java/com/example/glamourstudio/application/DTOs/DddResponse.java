package com.example.glamourstudio.application.DTOs;

import java.util.List;

public record DddResponse(String ddd, String estado, List<String> cidades) {
}
