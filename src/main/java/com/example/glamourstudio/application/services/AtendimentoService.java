package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Atendimento;
import com.example.glamourstudio.domain.entities.Cliente;
import com.example.glamourstudio.domain.entities.Servico;
import com.example.glamourstudio.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioService usuarioService;


    public List<AtendimentoResponse> listarTodosAtendimentosTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return atendimentoRepository.findAll()
                    .stream()
                    .map(AtendimentoResponse::new)
                    .toList();
        }

        return atendimentoRepository.getAtendimentosByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(AtendimentoResponse::new)
                .toList();
    }

    // Agenda do profissional logado num período (o id vem do token, nunca do front).
    // Sem datas, mostra HOJE. Com só uma das datas, mostra aquele dia.
    public List<AtendimentoResponse> listarMinhaAgenda(LocalDate inicio, LocalDate fim) {
        var usuarioLogado = usuarioService.getUsuarioLogado();

        LocalDate dataInicio = (inicio != null) ? inicio : (fim != null ? fim : LocalDate.now());
        LocalDate dataFim = (fim != null) ? fim : dataInicio;

        return atendimentoRepository
                .findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
                        usuarioLogado.id(),
                        dataInicio.atStartOfDay(),
                        dataFim.atTime(LocalTime.MAX))
                .stream()
                .map(AtendimentoResponse::new)
                .toList();
    }


    public AtendimentoResponse salvarAtendimento(AtendimentoRequest atendimento, Long id) {
        var usuarioLogado = usuarioService.getUsuarioLogado();
        validarProfissional(atendimento.profissionalId(), usuarioLogado.empresa_id());

        //Novo Atendimento
        if(id == 0L) {
            Atendimento atendimentoBanco = new Atendimento(atendimento);

            atendimentoBanco.setCliente(buscarClienteDaEmpresa(atendimento.clienteId(), usuarioLogado.empresa_id()));
            atendimentoBanco.setServico(buscarServicoDaEmpresa(atendimento.servicoId(), usuarioLogado.empresa_id()));

            // o atendimento fica com a empresa de quem está cadastrando
            atendimentoBanco.setEmpresa(empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null));

            atendimentoRepository.save(atendimentoBanco);

            return new AtendimentoResponse(atendimentoBanco);
        }else {
            //Atendimento existente (só encontra se for da empresa do usuário logado)
            Atendimento atendimentoBanco = atendimentoRepository
                    .getAtendimentoByIdAndEmpresa_Id(id, usuarioLogado.empresa_id())
                    .orElse(null);
            if(atendimentoBanco!= null ){
                atendimentoBanco.setCliente(buscarClienteDaEmpresa(atendimento.clienteId(), usuarioLogado.empresa_id()));
                atendimentoBanco.setProfissional(atendimento.profissional());
                atendimentoBanco.setProfissionalId(atendimento.profissionalId());
                atendimentoBanco.setDataHora(atendimento.dataHora());
                atendimentoBanco.setServico(buscarServicoDaEmpresa(atendimento.servicoId(), usuarioLogado.empresa_id()));
                atendimentoBanco.setValor(atendimento.valor());
                atendimentoRepository.save(atendimentoBanco);
                return  new AtendimentoResponse(atendimentoBanco);
            }

        }

        return null;
    }

    // Sem relação no banco, então confere aqui se o profissional informado existe
    // e se ele é da mesma empresa do usuário logado.
    private void validarProfissional(Long profissionalId, Long empresaId) {
        if (profissionalId != null && !usuarioRepository.existsUsuarioByIdAndEmpresa_Id(profissionalId, empresaId)) {
            throw new RuntimeException("Profissional não encontrado!");
        }
    }


    // O cliente do atendimento precisa ser da mesma empresa do usuário logado.
    private Cliente buscarClienteDaEmpresa(Long clienteId, Long empresaId) {
        return clienteRepository
                .getClienteByIdAndEmpresa_Id(clienteId, empresaId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    // O serviço do atendimento precisa ser da mesma empresa do usuário logado ou ser um serviço padrão.
    private Servico buscarServicoDaEmpresa(Long servicoId, Long empresaId) {
        return servicoRepository.getServicoByIdAndEmpresa_Id(servicoId, empresaId)
                .or(() -> servicoRepository.getServicoByIdAndEmpresaIsNull(servicoId))
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));
    }


    public AtendimentoResponse buscarPorId(Long id) {

        // só encontra se o atendimento for da empresa do usuário logado
        var usuarioLogado = usuarioService.getUsuarioLogado();
        var atendimento = atendimentoRepository
                .getAtendimentoByIdAndEmpresa_Id(id, usuarioLogado.empresa_id())
                .orElse(null);
        if (atendimento == null) {
            return null;
        }

        return new AtendimentoResponse(atendimento);
    }

    public boolean atualizarStatus(EnumStatusAtendimento statusAtendimento, Long id) {

        // só encontra se o atendimento for da empresa do usuário logado
        var usuarioLogado = usuarioService.getUsuarioLogado();
        Atendimento atendimentoBanco = atendimentoRepository
                .getAtendimentoByIdAndEmpresa_Id(id, usuarioLogado.empresa_id())
                .orElse(null);
        if(atendimentoBanco!= null ){
            atendimentoBanco.setStatusAtendimento(statusAtendimento);
            atendimentoRepository.save(atendimentoBanco);
            return  true;
        }
        return false;


    }
}