import { Atendimento, PainelFaturamento } from "../types/atendimento";
import api from "./api";



export async function buscarListaAtendimentos() : Promise<Atendimento[]> {

    const dados = await api.get<Atendimento[]>("http://localhost:8080/atendimento");

    if(dados.status == 200){
        return dados.data;
    }

    return[];
    
}

export async function buscarMinhaAgenda(inicio?:string, fim?:string) : Promise<Atendimento[]> {
const dados = await
api.get<Atendimento[]>("http://localhost:8080/atendimento/minha-agenda", {params:{inicio: inicio || undefined, fim: fim || undefined}});
if(dados.status == 200){
return dados.data;
}
return[];
}

export async function buscarPainelMensal() : Promise<PainelFaturamento> {

    const dados = await api.get<PainelFaturamento>("http://localhost:8080/atendimento/painel");

    return dados.data;
    
}

export async function deletarAtendimento(id:number) : Promise<number> {

   var dados = await api.delete('http://localhost:8080/atendimento/'+id+'/excluir');

    return dados.status; 
}

export async function atualizarStatusAtendimento(atendimento:Atendimento) : Promise<void> {

    var novoStatus = {};

    if(atendimento.statusAtendimento ==="AGENDADO"){

        novoStatus = {statusAtendimento:"ATENDIDO"}

    }else{

        novoStatus = {statusAtendimento:"AGENDADO"}

    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/atendimento/'+atendimento.id+'/status',novoStatus);

    if(dadosRetorno.status==200){

        alert("Atulizado status com sucesso!");

    }else{

        alert(dadosRetorno.data);

        return;

    }
    
}

export async function consultaAtendimentoPorId(id:number):Promise<Atendimento>{
    const valorAtendimentoBack = await api.get<Atendimento>('http://localhost:8080/atendimento/'+id);
    return valorAtendimentoBack.data;

}

export async function atualizarAtendimento(atendimento:Atendimento): Promise<number> {
    var dadosRetorno = await
    api.put<number>('http://localhost:8080/atendimento/'+atendimento.id, atendimento);
    return dadosRetorno.data;
    
}

export async function  salvarAtendimento (atendimento:Atendimento): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/atendimento',atendimento);
    return dadosRetorno.data;
}