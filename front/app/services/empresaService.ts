import { Empresa } from "../types/empresa";
import api from "./api";



export async function buscarListaEmpresas() : Promise<Empresa[]> {

    const dados = await api.get<Empresa[]>("http://localhost:8080/empresa");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}

export async function deletarEmpresa(id:number) : Promise<number> {

   var dados = await api.delete('http://localhost:8080/empresa/'+id+'/excluir');

    return dados.status; 
}

export async function atualizarStatusEmpresa(empresa:Empresa) : Promise<void> {

    var novoStatus = {};

    if(empresa.statusEmpresa ==="ATIVO"){

        novoStatus = {statusEmpresa:"INATIVO"}

    }else{

        novoStatus = {statusEmpresa:"ATIVO"}

    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/empresa/'+empresa.id+'/status',novoStatus);

    if(dadosRetorno.status==200){

        alert("Status atualizado com sucesso!");

    }else{

        alert(dadosRetorno.data);

        return;

    }
    
}

export async function consultaEmpresaPorId(id:number):Promise<Empresa>{
    const valorEmpresaBack = await api.get<Empresa>('http://localhost:8080/empresa/'+id);
    return valorEmpresaBack.data;

}

export async function atualizarEmpresa(empresa:Empresa): Promise<number> {
    var dadosRetorno = await
    api.put<number>('http://localhost:8080/empresa/'+empresa.id, empresa);
    return dadosRetorno.data;
    
}

export async function  salvarEmpresa (empresa:Empresa): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/empresa',empresa);
    return dadosRetorno.data;
}