import { buscarMinhaAgenda } from "@/app/services/atendimentoService";
import { Atendimento } from "@/app/types/atendimento";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { logout } from "./authSlice";

interface AgendaState {
    // atendimentos com status ATENDIDO do profissional logado no mês vigente
    atendidosMes: Atendimento[]
    mes: string            // "2026-11"
    carregando: boolean
    erro: string | null
}

const initialState: AgendaState = {
    atendidosMes: [],
    mes: "",
    carregando: false,
    erro: null
}

// "2026-11-01" e "2026-11-30" do mês atual (horário local)
const periodoMesVigente = () => {
    const agora = new Date();
    const inicio = new Date(agora.getFullYear(), agora.getMonth(), 1);
    const fim = new Date(agora.getFullYear(), agora.getMonth() + 1, 0);
    return {
        inicio: inicio.toLocaleDateString("sv-SE"),
        fim: fim.toLocaleDateString("sv-SE"),
        mes: inicio.toLocaleDateString("sv-SE").slice(0, 7)
    };
}

// O endpoint minha-agenda já filtra pelo profissional logado (token);
// aqui só restringimos ao mês vigente e ao status ATENDIDO.
export const carregarAtendidosMes = createAsyncThunk(
    "agenda/carregarAtendidosMes",
    async () => {
        const { inicio, fim, mes } = periodoMesVigente();
        const agenda = await buscarMinhaAgenda(inicio, fim);
        return {
            mes,
            atendidos: agenda.filter((a) => a.statusAtendimento === "ATENDIDO")
        };
    }
);

const agendaSlice = createSlice({
    name: "agenda",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(carregarAtendidosMes.pending, (state) => {
                state.carregando = true;
                state.erro = null;
            })
            .addCase(carregarAtendidosMes.fulfilled, (state, action) => {
                state.carregando = false;
                state.mes = action.payload.mes;
                state.atendidosMes = action.payload.atendidos;
            })
            .addCase(carregarAtendidosMes.rejected, (state, action) => {
                state.carregando = false;
                state.erro = action.error.message ?? "Erro ao carregar atendimentos do mês";
            })
            // ao deslogar, limpa os dados do profissional anterior
            .addCase(logout, () => initialState);
    }
});

// Selectors (tipados de forma estrutural para evitar import circular com o store)
type ComAgenda = { agenda: AgendaState };

export const selectAtendidosMes = (state: ComAgenda) => state.agenda.atendidosMes;
export const selectMesVigente = (state: ComAgenda) => state.agenda.mes;
export const selectQuantidadeAtendidosMes = (state: ComAgenda) => state.agenda.atendidosMes.length;
export const selectTotalFaturadoMes = (state: ComAgenda) =>
    state.agenda.atendidosMes.reduce((soma, a) => soma + (a.valor ?? 0), 0);

export default agendaSlice.reducer;
