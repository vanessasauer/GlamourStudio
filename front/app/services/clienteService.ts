import { Cliente } from "../types/cliente";
import api from "./api";



export async function buscarListaClientes() : Promise<Cliente[]> {

    const dados = await api.get<Cliente[]>("http://localhost:8080/cliente");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}

export async function deletarCliente(id:number) : Promise<number> {

   var dados = await api.delete('http://localhost:8080/cliente/'+id+'/excluir');

    return dados.status; 
}

export async function atualizarStatusCliente(cliente:Cliente) : Promise<void> {

    var novoStatus = {};

    if(cliente.statusCliente ==="ATIVO"){

        novoStatus = {statusCliente:"INATIVO"}

    }else{

        novoStatus = {statusCliente:"ATIVO"}

    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/cliente/'+cliente.id+'/status',novoStatus);

    if(dadosRetorno.status==200){

        alert("Status atualizado com sucesso!");

    }else{

        alert(dadosRetorno.data);

        return;

    }
    
}

export async function consultaClientePorId(id:number):Promise<Cliente>{
    const valorClienteBack = await api.get<Cliente>('http://localhost:8080/cliente/'+id);
    return valorClienteBack.data;

}

export async function atualizarCliente(cliente:Cliente): Promise<number> {
    var dadosRetorno = await
    api.put<number>('http://localhost:8080/cliente/'+cliente.id, cliente);
    return dadosRetorno.data;
    
}

export async function  salvarCliente (cliente:Cliente): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/cliente',cliente);
    return dadosRetorno.data;
}