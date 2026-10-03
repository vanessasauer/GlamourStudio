package com.example.glamourstudio.application.DTOs;

import com.example.glamourstudio.domain.entities.EnumStatusAtendimento;
import com.example.glamourstudio.domain.entities.EnumStatusCliente;
import com.example.glamourstudio.domain.entities.EnumStatusServico;
import com.example.glamourstudio.domain.entities.EnumStatusUsuario;

public record AtualizarStatusRequest(EnumStatusUsuario status, EnumStatusServico statusServico,
                                     EnumStatusCliente statusCliente, EnumStatusAtendimento statusAtendimento) {
}
