package com.example.glamourstudio.DTOs;

import com.example.glamourstudio.entities.EnumStatusAtendimento;
import com.example.glamourstudio.entities.EnumStatusCliente;
import com.example.glamourstudio.entities.EnumStatusServico;
import com.example.glamourstudio.entities.EnumStatusUsuario;

public record AtualizarStatusRequest(EnumStatusUsuario status, EnumStatusServico statusServico,
                                     EnumStatusCliente statusCliente, EnumStatusAtendimento statusAtendimento) {
}
