package com.example.glamourstudio.application.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtendimentoRequest(Long clienteId, String profissional, Long profissionalId, LocalDateTime dataHora, Long servicoId, BigDecimal valor) {
}
