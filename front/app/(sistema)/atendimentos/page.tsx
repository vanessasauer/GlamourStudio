"use client"

import { Atendimento } from "@/app/types/atendimento";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Atendimentos(){

    const [atendimentos, setAtendimentos] = useState<Atendimento[]>([]);

    useEffect( ()=>{

        carregarDados();

    },[]);

    const carregarDados = async () => {

        try{

            const dados = await axios.get<Atendimento[]>("http://localhost:8080/atendimento");

            setAtendimentos(dados.data)

        } catch (error){

            alert("Erro ao carregar dados")

        }

    }

    const handleDeletarAtendimento = async(atendimento:Atendimento) =>{

        var dadosRetorno = await 
        axios.delete('http://localhost:8080/atendimento/'+atendimento.id+'/excluir');

        if(dadosRetorno.status==200){

            alert("Atendimento cancelado com sucesso!");

        } else {

            alert(dadosRetorno.data);

            return;

        }

        carregarDados();

    }

     const handleAlterarStatusAtendimento = async(atendimento:Atendimento) =>{

        var novoStatus = {};

        if(atendimento.statusAtendimento ==="AGENDADO"){

            novoStatus = {statusAtendimento:"ATENDIDO"}

        }else{

            novoStatus = {statusAtendimento:"AGENDADO"}

        }

        var dadosRetorno = await  
        axios.patch('http://localhost:8080/atendimento/'+atendimento.id+'/status',novoStatus);

        if(dadosRetorno.status==200){

            alert("Atulizado status com sucesso!");

        }else{

            alert(dadosRetorno.data);

            return;

        }

        carregarDados();

    }

    return(

        <div className="w-full bg-[#F8F5F0] p-6 md:p-8 font-sans">

            <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

                <h1 className="text-3xl font-semibold text-[#5C5145]">
                    Gestão de atendimentos
                </h1>

                <Link 
                    href="/atendimentos/novo"
                    className="rounded-lg bg-[#B9A58D] px-5 py-2.5 text-sm font-medium text-white transition duration-200 hover:bg-[#A58F76] shadow-sm"
                >
                    Novo
                </Link>

            </div>

            <div className="mx-auto w-full max-w-5xl">

                <div className="overflow-hidden rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] shadow-sm">

                    <table className="w-full text-left">

                        <thead className="bg-[#EFE7DC]">

                            <tr>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Código
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Cliente
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Profissional
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Data e Hora
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Serviço
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Status
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
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

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {atendimento.id}
                                    </td>

                                    <td className="px-6 py-4 text-sm font-medium text-[#6B6054]">
                                        {atendimento.cliente}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {atendimento.profissional}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {atendimento.dataHora}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {atendimento.servico}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">

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

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">

                                        <div className="flex items-center gap-2">

                                            <Link
                                                href={`/atendimentos/${atendimento.id}/editar`}
                                                className="inline-flex items-center justify-center rounded-lg border border-[#D8CBBB] bg-[#FFFDF9] px-3 py-1.5 text-sm font-medium text-[#6B6054] transition-all duration-200 hover:bg-[#EFE7DC] hover:text-[#4F463D]"
                                            >
                                                Editar
                                            </Link>

                                            <button
                                onClick={()=> handleAlterarStatusAtendimento(atendimento)}
                                className="inline-flex items-center gap-2 rounded-lg border border-[#C9D7C8] bg-[#F1F6F0] px-3.5 py-2 text-sm font-semibold text-[#5F745C] shadow-sm transition-all duration-200 hover:-translate-y-0.5 hover:bg-[#E7F0E5] hover:shadow-md focus:outline-none focus:ring-2 focus:ring-[#D4E1D2]"
>
                                     <span className="h-2 w-2 rounded-full bg-[#7E9C79]"></span>
                                         Status
                                        </button>

                                            <button
                                                onClick={()=> handleDeletarAtendimento(atendimento)}
                                                className="inline-flex items-center justify-center rounded-lg border border-red-200 bg-red-50 px-3 py-1.5 text-sm font-medium text-red-600 transition-all duration-200 hover:border-red-300 hover:bg-red-100 hover:text-red-700"
                                            >
                                                Cancelar
                                            </button>

                                        </div>

                                    </td>

                                </tr>

                            ))}

                            {atendimentos.length ===0 && (

                                <tr>

                                    <td
                                        colSpan={7}
                                        className="px-6 py-12 text-center text-[#8A7D70]"
                                    >
                                        Nenhum atendimento encontrado!
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