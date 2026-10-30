import { Servico } from "../types/servico";
import api from "./api";



export async function buscarListaServicos() : Promise<Servico[]> {

    const dados = await api.get<Servico[]>("http://localhost:8080/servicos");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}

export async function deletarServico(id:number) : Promise<number> {

   var dados = await api.delete('http://localhost:8080/servicos/'+id+'/excluir');

    return dados.status; 
}

export async function atualizarStatusServico(servico:Servico) : Promise<void> {

    var novoStatus = {};

    if(servico.statusServico ==="ATIVO"){

        novoStatus = {status:"BLOQUEADO"}

    }else{

        novoStatus = {status:"ATIVO"}

    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/servico/'+servico.id+'/status',novoStatus);

    if(dadosRetorno.status==200){

        alert("Status atualizado com sucesso!");

    }else{

        alert(dadosRetorno.data);

        return;

    }
    
}

export async function consultaServicoPorId(id:number):Promise<Servico>{
    const valorServicoBack = await api.get<Servico>('http://localhost:8080/servicos/'+id);
    return valorServicoBack.data;

}

export async function atualizarServico(servico:Servico): Promise<number> {
    var dadosRetorno = await
    api.put<number>('http://localhost:8080/servicos/'+servico.id, servico);
    return dadosRetorno.data;
    
}

export async function  salvarServico (servico:Servico): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/servicos',servico);
    return dadosRetorno.data;
}