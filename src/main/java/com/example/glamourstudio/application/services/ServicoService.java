package com.example.glamourstudio.application.services;
import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Empresa;
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

        Empresa empresa = empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null);

        // serviços da empresa + serviços padrão (cadastrados pelo admin, sem empresa)
        return servicoRepository.getServicosByEmpresa_IdOrEmpresaIsNull(usuarioLogado.empresa_id())
                .stream()
                .map(servico -> montarResposta(servico, empresa))
                .toList();
    }

    // se for um serviço padrão que a empresa desativou, mostra como INATIVO só para ela
    private ServicoResponse montarResposta(Servico servico, Empresa empresa) {

        boolean desativadoPelaEmpresa = servico.getEmpresa() == null
                && empresa != null
                && empresa.getServicosPadraoDesativados().stream()
                .anyMatch(s -> s.getId().equals(servico.getId()));

        if (desativadoPelaEmpresa) {
            return new ServicoResponse(servico, EnumStatusServico.INATIVO);
        }

        return new ServicoResponse(servico);
    }

    public CriarServicoResponse criarServicoAdmin(CriarServicoRequest criarServicoRequest) {

        if (!criarServicoRequest.secretKey().equals(secret)){
            return new CriarServicoResponse(0L,"Serviço salvo com sucesso!");
        }

        // serviço padrão: fica sem empresa e aparece para todas as empresas
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
            //Serviço existente: só encontra se for da empresa do usuário logado
            // (serviço padrão, sem empresa, não é encontrado aqui, então não pode ser editado)
            var usuarioLogado = usuarioService.getUsuarioLogado();
            Servico servicoBanco = servicoRepository
                    .getServicoByIdAndEmpresa_Id(id, usuarioLogado.empresa_id())
                    .orElse(null);

            if(servicoBanco!= null){
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

    // Procura primeiro um serviço da empresa; se não achar, procura um serviço padrão.
    private Servico buscarServicoDaEmpresaOuPadrao(Long id, Long empresaId) {
        return servicoRepository.getServicoByIdAndEmpresa_Id(id, empresaId)
                .or(() -> servicoRepository.getServicoByIdAndEmpresaIsNull(id))
                .orElse(null);
    }

    public ServicoResponse buscarPorId(Long id) {

        // só encontra se o serviço for da empresa do usuário logado ou for padrão
        var usuarioLogado = usuarioService.getUsuarioLogado();
        var servico = buscarServicoDaEmpresaOuPadrao(id, usuarioLogado.empresa_id());
        if (servico == null) {
            return null;
        }

        return new ServicoResponse(servico);
    }

    public boolean atualizarStatus(EnumStatusServico statusServico, Long id) {

        // só encontra se o serviço for da empresa do usuário logado ou for padrão
        var usuarioLogado = usuarioService.getUsuarioLogado();
        Servico servicoBanco = buscarServicoDaEmpresaOuPadrao(id, usuarioLogado.empresa_id());
        if(servicoBanco == null){
            return false;
        }

        // serviço padrão: ativa ou desativa só para a empresa de quem está logado
        if(servicoBanco.getEmpresa() == null){

            Empresa empresa = empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null);
            if(empresa == null){
                return false;
            }

            if(statusServico == EnumStatusServico.ATIVO){
                empresa.getServicosPadraoDesativados().removeIf(s -> s.getId().equals(servicoBanco.getId()));
            }else {
                empresa.getServicosPadraoDesativados().add(servicoBanco);
            }

            empresaRepository.save(empresa);
            return true;
        }

        // serviço da própria empresa: muda o status normalmente
        servicoBanco.setStatusServico(statusServico);
        servicoRepository.save(servicoBanco);
        return  true;

    }

}