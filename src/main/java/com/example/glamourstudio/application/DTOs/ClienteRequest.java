package com.example.glamourstudio.application.DTOs;

import java.time.LocalDate;

public record ClienteRequest(String nome, LocalDate dataNascimento, String email, String telefone) {
}
