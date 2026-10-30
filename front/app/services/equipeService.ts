import { Equipe } from "../types/equipe";
import api from "./api";



export async function buscarListaEquipes() : Promise<Equipe[]> {

    const dados = await api.get<Equipe[]>("http://localhost:8080/equipe");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}

export async function deletarEquipe(id:number) : Promise<number> {

   var dados = await api.delete('http://localhost:8080/equipe/'+id+'/excluir');

    return dados.status; 
}

export async function atualizarStatusEquipe(equipe:Equipe) : Promise<void> {

    var novoStatus = {};

    if(equipe.statusEquipe ==="ATIVO"){

        novoStatus = {statusEquipe:"INATIVO"}

    }else{

        novoStatus = {statusEquipe:"ATIVO"}

    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/equipe/'+equipe.id+'/status',novoStatus);

    if(dadosRetorno.status==200){

        alert("Status atualizado com sucesso!");

    }else{

        alert(dadosRetorno.data);

        return;

    }
    
}

export async function consultaEquipePorId(id:number):Promise<Equipe>{
    const valorEquipeBack = await api.get<Equipe>('http://localhost:8080/equipe/'+id);
    return valorEquipeBack.data;

}

export async function atualizarEquipe(equipe:Equipe): Promise<number> {
    var dadosRetorno = await
    api.put<number>('http://localhost:8080/equipe/'+equipe.id, equipe);
    return dadosRetorno.data;
    
}

export async function  salvarEquipe (equipe:Equipe): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/equipe',equipe);
    return dadosRetorno.data;
}