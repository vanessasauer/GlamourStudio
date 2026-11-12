package com.example.glamourstudio.application.services;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.repository.EmpresaRepository;
import com.example.glamourstudio.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private ServicoRepository servicoRepository;

    @Value("${spring.secretkey}")
    private String secret;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<ServicoResponse> listarTodosServicosTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return servicoRepository.findAll()
                    .stream()
                    .map(ServicoResponse::new)
                    .toList();
        }

        return servicoRepository.getServicosByEmpresa_Id(usuarioLogado.empresa_id())
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

            // o serviço fica com a empresa de quem está cadastrando
            var usuarioLogado = usuarioService.getUsuarioLogado();
            servicoBanco.setEmpresa(empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null));

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
