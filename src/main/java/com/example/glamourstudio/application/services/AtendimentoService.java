package com.example.glamourstudio.application.services;

import com.example.glamourstudio.application.DTOs.*;
import com.example.glamourstudio.domain.entities.Atendimento;
import com.example.glamourstudio.domain.repository.AtendimentoRepository;
import com.example.glamourstudio.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;



    public List<AtendimentoResponse> listarTodosAtendimentosTable() {

        return atendimentoRepository.findAll()
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

    // Soma dos atendimentos ATENDIDO do profissional logado no mês vigente.
    public PainelFaturamentoResponse gerarPainelMensal() {

        var usuarioLogado = usuarioService.getUsuarioLogado();
        YearMonth mesAtual = YearMonth.now();

        var atendimentos = atendimentoRepository
                .findByProfissionalIdAndStatusAtendimentoAndDataHoraBetween(
                        usuarioLogado.id(),
                        EnumStatusAtendimento.ATENDIDO,
                        mesAtual.atDay(1).atStartOfDay(),
                        mesAtual.atEndOfMonth().atTime(LocalTime.MAX));

        return new PainelFaturamentoResponse(
                mesAtual.toString(),
                atendimentos.size(),
                Atendimento.somarValores(atendimentos));
    }


    public AtendimentoResponse salvarAtendimento(AtendimentoRequest atendimento, Long id) {
        validarProfissional(atendimento.profissionalId());

        //Novo Atendimento
        if(id == 0L) {
            Atendimento atendimentoBanco = new Atendimento(atendimento);
            atendimentoRepository.save(atendimentoBanco);

            return new AtendimentoResponse(atendimentoBanco);
        }else {
            //Atendimento existente
            Atendimento atendimentoBanco = atendimentoRepository.findById(id).orElse(null);
            if(atendimentoBanco!= null ){
                atendimentoBanco.setCliente(atendimento.cliente());
                atendimentoBanco.setProfissional(atendimento.profissional());
                atendimentoBanco.setProfissionalId(atendimento.profissionalId());
                atendimentoBanco.setDataHora(atendimento.dataHora());
                atendimentoBanco.setServico(atendimento.servico());
                atendimentoBanco.setValor(atendimento.valor());
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
