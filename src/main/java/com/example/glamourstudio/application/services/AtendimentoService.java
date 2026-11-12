package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Atendimento;
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
        validarProfissional(atendimento.profissionalId());

        //Novo Atendimento
        if(id == 0L) {
            Atendimento atendimentoBanco = new Atendimento(atendimento);

            atendimentoBanco.setCliente(clienteRepository.findById(atendimento.clienteId()).orElse(null));
            atendimentoBanco.setServico(servicoRepository.findById(atendimento.servicoId()).orElse(null));

            // o atendimento fica com a empresa de quem está cadastrando
            var usuarioLogado = usuarioService.getUsuarioLogado();
            atendimentoBanco.setEmpresa(empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null));

            atendimentoRepository.save(atendimentoBanco);

            return new AtendimentoResponse(atendimentoBanco);
        }else {
            //Atendimento existente
            Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
            if(atendimentoBanco!= null ){
                atendimentoBanco.setCliente(clienteRepository.findById(atendimento.clienteId()).orElse(null));                atendimentoBanco.setProfissional(atendimento.profissional());
                atendimentoBanco.setProfissionalId(atendimento.profissionalId());
                atendimentoBanco.setDataHora(atendimento.dataHora());
                atendimentoBanco.setServico(servicoRepository.findById(atendimento.servicoId()).orElse(null));                atendimentoBanco.setValor(atendimento.valor());
                atendimentoRepository.save(atendimentoBanco);
                return  new AtendimentoResponse(atendimentoBanco);
            }

        }

        return null;
    }

    // Sem relação no banco, então confere aqui se o profissional informado existe.
    private void validarProfissional(Long profissionalId) {
        if (profissionalId != null && !usuarioRepository.existsById(profissionalId)) {
            throw new RuntimeException("Profissional não encontrado!");
        }
    }


    public AtendimentoResponse buscarPorId(Long id) {

        var atendimento = atendimentoRepository.findById(id).orElse(null);
        if (atendimento == null) {
            return null;
        }

        return new AtendimentoResponse(atendimento);
    }

    public boolean atualizarStatus(EnumStatusAtendimento statusAtendimento, Long id) {

        Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
        if(atendimentoBanco!= null ){
            atendimentoBanco.setStatusAtendimento(statusAtendimento);
            atendimentoRepository.save(atendimentoBanco);
            return  true;
        }
        return false;


    }
}
