"use client"
import { atualizarAtendimento, salvarAtendimento } from "@/app/services/atendimentoService";
import { buscarListaClientes } from "@/app/services/clienteService";
import { buscarListaServicos } from "@/app/services/servicoService";
import { buscarListaUsuarios } from "@/app/services/usuarioService";
import { Atendimento, AtendimentoFormProps } from "@/app/types/atendimento";
import { Cliente } from "@/app/types/cliente";
import { Servico } from "@/app/types/servico";
import { Usuario } from "@/app/types/usuario";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function AtendimentosForm({atendimentoExistente}:AtendimentoFormProps) {
    const router = useRouter();

const  [atendimento, setAtendimento] = useState<Atendimento>(
    atendimentoExistente ||
    new Atendimento(null,"","",null,"","",null,"AGENDADO",null,null)
);

const [profissionais, setProfissionais] = useState<Usuario[]>([]);
const [clientes, setClientes] = useState<Cliente[]>([]);
const [servicos, setServicos] = useState<Servico[]>([]);

useEffect(()=>{

    carregarListas();

},[]);

// busca profissionais, clientes e serviços cadastrados (já vêm filtrados pela empresa)
const carregarListas = async () => {

    try{

        const usuarios = await buscarListaUsuarios();
        setProfissionais(usuarios.filter((usuario)=> usuario.status === "ATIVO" && usuario.role === "ROLE_PROFISSIONAL"));

        const listaClientes = await buscarListaClientes();
        setClientes(listaClientes.filter((cliente)=> cliente.statusCliente === "ATIVO"));

        const listaServicos = await buscarListaServicos();
        setServicos(listaServicos.filter((servico)=> servico.statusServico === "ATIVO"));

    } catch (error){

        alert("Erro ao carregar dados do atendimento")

    }

}

const handlerChange = ( campo : 'dataHora' | 'valor', valor:string) =>{
    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            valorAnterior.cliente,
            valorAnterior.profissional,
            valorAnterior.profissionalId,
            campo === 'dataHora' ? valor : valorAnterior.dataHora,
            valorAnterior.servico,
            campo === 'valor' ? (valor === "" ? null : Number(valor)) : valorAnterior.valor,
            valorAnterior.statusAtendimento,
            valorAnterior.clienteId,
            valorAnterior.servicoId
            
        )
    )
}

// Ao escolher o cliente, guarda o id (vai para o banco) e o nome (para exibição).
const handlerCliente = (valor:string) =>{

    const escolhido = clientes.find((cliente)=> String(cliente.id) === valor);

    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            escolhido ? escolhido.nome : "",
            valorAnterior.profissional,
            valorAnterior.profissionalId,
            valorAnterior.dataHora,
            valorAnterior.servico,
            valorAnterior.valor,
            valorAnterior.statusAtendimento,
            escolhido ? escolhido.id : null,
            valorAnterior.servicoId
            
        )
    )
}

// Ao escolher o profissional, guarda o id (para agenda/painel) e o nome (para exibição).
const handlerProfissional = (valor:string) =>{

    const escolhido = profissionais.find((usuario)=> String(usuario.id) === valor);

    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            valorAnterior.cliente,
            escolhido ? escolhido.nome : "",
            escolhido ? escolhido.id : null,
            valorAnterior.dataHora,
            valorAnterior.servico,
            valorAnterior.valor,
            valorAnterior.statusAtendimento,
            valorAnterior.clienteId,
            valorAnterior.servicoId
            
        )
    )
}

// Ao escolher o serviço, guarda o id, o nome e já preenche o valor com o preço cadastrado.
const handlerServico = (valor:string) =>{

    const escolhido = servicos.find((servico)=> String(servico.id) === valor);

    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            valorAnterior.cliente,
            valorAnterior.profissional,
            valorAnterior.profissionalId,
            valorAnterior.dataHora,
            escolhido ? escolhido.nome : "",
            escolhido ? escolhido.valor : valorAnterior.valor,
            valorAnterior.statusAtendimento,
            valorAnterior.clienteId,
            escolhido ? escolhido.id : null
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(atendimentoExistente){
        var dadosRetorno = await atualizarAtendimento(atendimento);

        if(dadosRetorno){
            alert("Atendimento foi salvo com sucesso!");
        } else {
            alert("Erro ao atualizar atendimento!");
    
            return;
        }

    }else{
    var dadosRetorno = await salvarAtendimento(atendimento);

    if(dadosRetorno){
        alert("Atendimento foi salvo com sucesso!");
    } else {
        alert("Erro ao salvar atendimento!");

        return;
    }
}

    router.push("/atendimentos");

    }

    return (

        <form action = {handlerSalvar} className="space-y-6">

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Cliente:
                    </label>

                    <select
                        name="clienteId"
                        value = {atendimento.clienteId ?? ""}
                        required
                        onChange={(e)=> handlerCliente(e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >

                        <option value="">Selecione o cliente</option>

                        {clientes.map((cliente)=>(

                            <option key={cliente.id} value={cliente.id ?? ""}>
                                {cliente.nome}
                            </option>

                        ))}

                    </select>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Profissional:
                    </label>

                    <select
                        name="profissionalId"
                        value = {atendimento.profissionalId ?? ""}
                        required
                        onChange={(e)=> handlerProfissional(e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >

                        <option value="">Selecione o profissional</option>

                        {profissionais.map((usuario)=>(

                            <option key={usuario.id} value={usuario.id ?? ""}>
                                {usuario.nome}
                            </option>

                        ))}

                    </select>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Data e Hora:
                    </label>

                    <input
                        name="dataHora"
                        value = {atendimento.dataHora}
                        placeholder="dd/mm/aaaa hh:mm"
                        type="datetime-local"
                        onChange={(e)=> handlerChange('dataHora', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Serviço:
                    </label>

                    <select
                        name="servicoId"
                        value = {atendimento.servicoId ?? ""}
                        required
                        onChange={(e)=> handlerServico(e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >

                        <option value="">Selecione o serviço</option>

                        {servicos.map((servico)=>(

                            <option key={servico.id} value={servico.id ?? ""}>
                                {servico.nome}
                            </option>

                        ))}

                    </select>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Valor (R$):
                    </label>

                    <input
                        name="valor"
                        type="number"
                        min="0"
                        step="0.01"
                        value = {atendimento.valor ?? ""}
                        required
                        placeholder="80.00"
                        onChange={(e)=> handlerChange('valor', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

            </div>
                        

            <div className="flex items-center justify-end space-x-4 pt-4 border-t border-[#E8DED0]">

                <Link
                    href="/atendimentos"
                    className="px-5 py-2.5 bg-[#EFE7DC] hover:bg-[#E8DED0] text-[#6B6054] hover:text-[#4F463D] font-medium text-sm rounded-xl transition-all duration-200 text-center border border-[#D8CBBB]"
                >
                    Cancelar
                </Link>

                <button
                    type="submit"
                    className="px-6 py-2.5 bg-[#B9A58D] hover:bg-[#A58F76] text-white font-semibold text-sm rounded-xl shadow-md transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-[#D8CBBB]"
                >
                    Salvar
                </button>

            </div>

        </form>

    );

}