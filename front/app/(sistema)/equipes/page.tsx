"use client"

import { atualizarStatusEquipe, buscarListaEquipes, deletarEquipe } from "@/app/services/equipeService";
import { Equipe } from "@/app/types/equipe";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Equipes(){

    const [equipes, setEquipes] = useState<Equipe[]>([]);

    useEffect( ()=>{

        carregarDados();

    },[]);

    const carregarDados = async () => {

        try{

            const dados = await buscarListaEquipes()

            setEquipes(dados);

        } catch (error){

            alert("Erro ao carregar dados")

        }

        carregarDados();

    }

    const handleDeletarEquipe = async(equipe:Equipe) =>{

        var dadosRetorno = await deletarEquipe(equipe.id || 0);

        if(dadosRetorno ==200){

            alert("Excluído com sucesso!");

        } else {

            alert("Erro ao excluir usuários!");

            return;

        }

        carregarDados();

    }

    const handleAlterarStatusEquipe = async(equipe:Equipe) =>{

       await atualizarStatusEquipe(equipe);

        carregarDados();

    }

    return (

        <div className="w-full bg-[#F8F5F0] p-6 md:p-8 font-sans">

            <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

                <h1 className="text-3xl font-semibold text-[#5C5145]">
                    Gestão de equipes
                </h1>

                <Link
                    href="/equipes/novo"
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
                                    Nome
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Descrição
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

                            {equipes.map((equipe)=>( 

                                <tr 
                                    key={equipe.id} 
                                    className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]"
                                >

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {equipe.id}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054] font-medium">
                                        {equipe.nome}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {equipe.descricao}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        <span
                                            className={`inline-flex rounded-full px-3 py-1 text-xs font-medium ${
                                                equipe.statusEquipe === "INATIVO"
                                                    ? "bg-amber-50 text-amber-700"
                                                    : equipe.statusEquipe === "ATIVO"
                                                    ? "bg-emerald-50 text-emerald-700"
                                                    : "bg-red-50 text-red-600"
                                            }`}
                                        >
                                            {equipe.statusEquipe}
                                        </span>
                                
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">

                                        <div className="flex items-center gap-3">

                                            <Link
                                                href={`/equipes/${equipe.id}/editar`}
                                                className="inline-flex items-center justify-center rounded-lg border border-[#D8CBBB] bg-[#FFFDF9] px-3 py-1.5 text-sm font-medium text-[#6B6054] transition-all duration-200 hover:bg-[#EFE7DC] hover:text-[#4F463D]"
                                            >
                                                Editar
                                            </Link>

                                            <button onClick={()=> handleDeletarEquipe(equipe)}
                                            className="inline-flex items-center justify-center rounded-lg border border-[#E7C9C3] bg-[#FFF7F5] px-3.5 py-2 text-sm font-medium text-[#A65F55] shadow-sm transition-all duration-200 hover:-translate-y-0.5 hover:bg-[#FCEDEA] hover:border-[#DFAFA6] hover:text-[#8F4F46] hover:shadow-md focus:outline-none focus:ring-2 focus:ring-[#F0D7D2]"
                                            >
                                                Deletar
                                                </button>


                                            <button onClick={()=> handleAlterarStatusEquipe(equipe)}
                                     className={`relative inline-flex h-7 w-12 shrink-0 items-center rounded-full transition-all duration-300 ${
                                        equipe.statusEquipe === "ATIVO"
                                           ? "bg-emerald-500": "bg-amber-400"
                                     }`} >
                                            <span
                                   className={`inline-block h-5 w-5 transform rounded-full bg-white shadow-sm transition-transform duration-300 ${
                                          equipe.statusEquipe === "ATIVO"
                                           ? "translate-x-6" : "translate-x-1"
                                        }`}
                                            ></span>
                                        </button>

                                        </div>

                                    </td>

                                </tr>

                            ))}

                            {equipes.length ===0 && (

                                <tr>

                                    <td
                                        colSpan={5}
                                        className="px-6 py-12 text-center text-[#8A7D70]"
                                    >
                                        Nenhuma equipe encontrada!
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