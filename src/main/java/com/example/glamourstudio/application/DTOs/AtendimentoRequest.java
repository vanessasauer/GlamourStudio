package com.example.glamourstudio.application.DTOs;

import java.time.LocalDateTime;

public record AtendimentoRequest(String cliente, String profissional, LocalDateTime dataHora, String servico) {
}
