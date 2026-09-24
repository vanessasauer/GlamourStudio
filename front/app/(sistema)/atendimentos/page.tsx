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

    return(

        <div className="min-h-screen bg-[#F8F5F0] px-6 py-10">

            <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

                <h1 className="text-3xl font-semibold text-[#5C5145]">
                    Gestão de atendimentos
                </h1>

                <Link 
                    href="/atendimentos/novo"
                    className="rounded-lg bg-[#B9A58D] px-5 py-2.5 text-sm font-medium text-white transition duration-200 hover:bg-[#A58F76]"
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

                            </tr>

                        </thead>

                        <tbody>

                           {atendimentos.map((atendimento)=>( 

                            <tr key={atendimento.id}className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]">

                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {atendimento.id}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
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
                                    {atendimento.status}
                                </td>
                                
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        <Link href={`/atendimentos/${atendimento.id}/editar`}>Editar</Link>
                                    </td>
                            </tr>
                           ))}

                           {atendimentos.length ===0 &&
                           (
                            <tr>
                                <td colSpan={6} className="px-6 py-12 text-center text-[#6B6054] ">
                                    Nenhum atendimento encontrado!
                                </td>
                            </tr>
                           )

                           }

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    )

}