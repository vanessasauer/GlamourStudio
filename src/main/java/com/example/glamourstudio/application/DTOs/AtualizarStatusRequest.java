package com.example.glamourstudio.application.DTOs;

public record AtualizarStatusRequest(EnumStatusUsuario status, EnumStatusServico statusServico,
                                     EnumStatusCliente statusCliente, EnumStatusAtendimento statusAtendimento,
                                     EnumStatusEmpresa statusEmpresa, EnumStatusEquipe statusEquipe) {
}
