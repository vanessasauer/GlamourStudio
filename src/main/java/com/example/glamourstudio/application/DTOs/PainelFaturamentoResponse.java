package com.example.glamourstudio.application.DTOs;

import java.math.BigDecimal;

public record PainelFaturamentoResponse(String mes, int quantidadeAtendimentos, BigDecimal totalFaturado) {
}
