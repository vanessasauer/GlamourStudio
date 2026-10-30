"use client"
import { atualizarStatusAtendimento, buscarMinhaAgenda, buscarPainelMensal } from "@/app/services/atendimentoService";
import { Atendimento, PainelFaturamento } from "@/app/types/atendimento";
import { useEffect, useState } from "react";

export default function MinhaAgenda(){

const hoje = new Date().toLocaleDateString("sv-SE");

const [atendimentos, setAtendimentos] = useState<Atendimento[]>([]);
const [painel, setPainel] = useState<PainelFaturamento | null>(null);
const [dataInicio, setDataInicio] = useState(hoje);
const [dataFim, setDataFim] = useState(hoje);

useEffect( ()=>{
carregarDados();
},[dataInicio, dataFim]);

const carregarDados = async () => {
try{
const [agenda, dadosPainel] = await Promise.all([
buscarMinhaAgenda(dataInicio, dataFim),
buscarPainelMensal()
]);
setAtendimentos(agenda);
setPainel(dadosPainel);
} catch (error){
alert("Erro ao carregar dados")
}
}

// Ao mudar o status, recarrega a agenda e o painel (o total do mês muda junto)
const handleAlterarStatusAtendimento = async(atendimento:Atendimento) =>{
await atualizarStatusAtendimento(atendimento);
carregarDados();
}

// Se a data inicial passar da final, a final acompanha
const handleAlterarDataInicio = (valor:string) => {
if(!valor){
return;
}
setDataInicio(valor);
if(dataFim < valor){
setDataFim(valor);
}
}

const handleAlterarDataFim = (valor:string) => {
if(!valor){
return;
}
setDataFim(valor);
}

const formatarDataHora = (dataHora:string) => {
return new Date(dataHora).toLocaleString("pt-BR", {dateStyle:"short", timeStyle:"short"});
}

const formatarMoeda = (valor:number | null) => {
return (valor ?? 0).toLocaleString("pt-BR", {style:"currency", currency:"BRL"});
}

// o back manda "2026-10", mostra como "10/2026"
const formatarMes = (mes:string) => {
return mes.split("-").reverse().join("/");
}

// o input guarda "2026-10-30", mostra como "30/10/2026"
const formatarData = (data:string) => {
return data.split("-").reverse().join("/");
}

return(
<div className="w-full bg-[#F8F5F0] p-6 md:p-8 font-sans">

<div className="mx-auto mb-8 flex w-full max-w-6xl flex-wrap items-end justify-between gap-4">
<div>
<h1 className="text-3xl font-semibold text-[#5C5145]">
Minha agenda
</h1>
<p className="mt-1 text-sm text-[#8A7D70]">
Período de {formatarData(dataInicio)} a {formatarData(dataFim)}
</p>
</div>
<div className="flex items-end gap-3">
<div className="flex flex-col gap-1">
<label className="text-sm font-medium text-[#6B6054]">
De
</label>
<input
type="date"
value={dataInicio}
onChange={(e)=> handleAlterarDataInicio(e.target.value)}
className="rounded-lg border border-[#D8CBBB] bg-[#FAF7F2] px-4 py-2.5 text-sm text-[#4F463D] outline-none transition focus:border-[#B9A58D] focus:ring-2 focus:ring-[#D8CBBB]/50"
/>
</div>
<div className="flex flex-col gap-1">
<label className="text-sm font-medium text-[#6B6054]">
Até
</label>
<input
type="date"
value={dataFim}
min={dataInicio}
onChange={(e)=> handleAlterarDataFim(e.target.value)}
className="rounded-lg border border-[#D8CBBB] bg-[#FAF7F2] px-4 py-2.5 text-sm text-[#4F463D] outline-none transition focus:border-[#B9A58D] focus:ring-2 focus:ring-[#D8CBBB]/50"
/>
</div>
</div>
</div>

<div className="mx-auto mb-8 grid w-full max-w-6xl grid-cols-1 gap-4 md:grid-cols-3">
<div className="rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] p-6 shadow-sm">
<p className="text-sm text-[#8A7D70]">Mês vigente</p>
<p className="mt-2 text-2xl font-semibold text-[#5C5145]">
{painel ? formatarMes(painel.mes) : "-"}
</p>
</div>
<div className="rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] p-6 shadow-sm">
<p className="text-sm text-[#8A7D70]">Atendimentos finalizados</p>
<p className="mt-2 text-2xl font-semibold text-[#5C5145]">
{painel ? painel.quantidadeAtendimentos : 0}
</p>
</div>
<div className="rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] p-6 shadow-sm">
<p className="text-sm text-[#8A7D70]">Total faturado no mês</p>
<p className="mt-2 text-2xl font-semibold text-[#5F745C]">
{formatarMoeda(painel ? painel.totalFaturado : 0)}
</p>
</div>
</div>

<div className="mx-auto w-full max-w-6xl">
<div className="overflow-hidden rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] shadow-sm">
<table className="w-full text-left">
<thead className="bg-[#EFE7DC]">
<tr>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Data e Hora
</th>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Cliente
</th>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Serviço
</th>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Valor
</th>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Status
</th>
<th className="px-4 py-4 text-sm font-semibold text-[#5C5145]">
Ações
</th>
</tr>
</thead>
<tbody>
{atendimentos.map((atendimento)=>(
<tr
key={atendimento.id}
className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]"
>
<td className="px-4 py-4 text-sm font-medium text-[#6B6054]">
{formatarDataHora(atendimento.dataHora)}
</td>
<td className="px-4 py-4 text-sm font-medium text-[#6B6054]">
{atendimento.cliente}
</td>
<td className="px-4 py-4 text-sm text-[#6B6054]">
{atendimento.servico}
</td>
<td className="px-4 py-4 text-sm text-[#6B6054]">
{formatarMoeda(atendimento.valor)}
</td>
<td className="px-4 py-4 text-sm text-[#6B6054]">
<span
className={`inline-flex rounded-full px-3 py-1 text-xs font-medium ${
atendimento.statusAtendimento === "AGENDADO"
? "bg-amber-50 text-amber-700"
: atendimento.statusAtendimento === "ATENDIDO"
? "bg-emerald-50 text-emerald-700"
: "bg-red-50 text-red-600"
}`}
>
{atendimento.statusAtendimento}
</span>
</td>
<td className="px-4 py-4 text-sm text-[#6B6054]">
{atendimento.statusAtendimento !== "CANCELADO" && (
<button
onClick={()=> handleAlterarStatusAtendimento(atendimento)}
className="inline-flex items-center gap-2 rounded-lg border border-[#C9D7C8] bg-[#F1F6F0] px-3 py-1.5 text-sm font-medium text-[#5F745C] transition-all duration-200 hover:bg-[#E7F0E5]"
>
<span className="h-2 w-2 rounded-full bg-[#7E9C79]"></span>
{atendimento.statusAtendimento === "AGENDADO" ? "Finalizar" : "Reabrir"}
</button>
)}
</td>
</tr>
))}
{atendimentos.length ===0 && (
<tr>
<td
colSpan={6}
className="px-4 py-12 text-center text-[#8A7D70]"
>
Nenhum atendimento neste período!
</td>
</tr>
)}
</tbody>
</table>
</div>
</div>
</div>
)
}