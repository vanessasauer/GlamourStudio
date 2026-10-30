package com.example.glamourstudio.application.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtendimentoRequest(String cliente, String profissional, Long profissionalId, LocalDateTime dataHora, String servico, BigDecimal valor) {
}
