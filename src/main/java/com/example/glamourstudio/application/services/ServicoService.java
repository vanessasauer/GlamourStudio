package com.example.glamourstudio.application.services;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {


    @Autowired
    private ServicoRepository servicoRepository;

    @Value("${spring.secretkey}")
    private String secret;


    public List<ServicoResponse> listarTodosServicosTable() {

        return servicoRepository.findAll()
                .stream()
                .map(ServicoResponse::new)
                .toList();
    }

    public CriarServicoResponse criarServicoAdmin(CriarServicoRequest criarServicoRequest) {

        if (!criarServicoRequest.secretKey().equals(secret)){
            return new CriarServicoResponse(0L,"Serviço salvo com sucesso!");
        }

        Servico servicoAdminSalvar = new Servico(criarServicoRequest);
        servicoRepository.save(servicoAdminSalvar);

        return new CriarServicoResponse(servicoAdminSalvar.getId(),"Serviço salvo com sucesso");
    }

    public ServicoResponse salvarServico(ServicoRequest servico, Long id) {

        //Novo Servico
        if(id == 0L) {
            Servico servicoBanco = new Servico(servico);
            servicoRepository.save(servicoBanco);

            return new ServicoResponse(servicoBanco);
        }else {
            //Serviço existente
            Servico servicoBanco = servicoRepository.findById(id).orElse(null);
            if(servicoBanco!= null ){
                servicoBanco.setNome(servico.nome());
                servicoBanco.setDescricao(servico.descricao());
                servicoBanco.setValor(servico.valor());
                servicoBanco.setDuracaoMinutos(servico.duracaoMinutos());
                servicoRepository.save(servicoBanco);
                return  new ServicoResponse(servicoBanco);
            }

        }

        return null;
    }

    public ServicoResponse buscarPorId(Long id) {

        var servico = servicoRepository.findById(id).orElse(null);
        if (servico == null) {
            return null;
        }

        return new ServicoResponse(servico);
    }

    public boolean atualizarStatus(EnumStatusServico statusServico, Long id) {

        Servico servicoBanco = servicoRepository.findById(id).orElse(null);
        if(servicoBanco!= null ){
            servicoBanco.setStatusServico(statusServico);
            servicoRepository.save(servicoBanco);
            return  true;
        }
        return false;


    }

}
