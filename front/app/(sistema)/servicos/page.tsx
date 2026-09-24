"use client"
import { Servico } from "@/app/types/servico";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Servicos(){

    const [servicos, setServicos] = useState<Servico[]>([]);

useEffect( ()=>{
    carregarDados();
},[]);


    const carregarDados = async () => {
        
        try{
        const dados = await axios.get<Servico[]>("http://localhost:8080/servico");

        setServicos(dados.data)
        
    } catch (error){
        alert("Erro ao carregar dados")
    }
}

    return(
        <div className="min-h-screen bg-[#F8F5F0] px-6 py-10">

            <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

                <h1 className="text-3xl font-semibold text-[#5C5145]">
                    Gestão de serviços
                </h1>

                <Link 
                    href="/servicos/novo"
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
                                    Nome
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Descrição
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Valor
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Duração
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    Status
                                </th>

                            </tr>

                        </thead>

                        <tbody>

                           {servicos.map((servico)=>( 

                            <tr key={servico.id}className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]">

                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.id}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.nome}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.descricao}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.valor}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.duracaoMinutos}
                                </td>
                                <td className="px-6 py-4 text-sm text-[#6B6054]">
                                    {servico.status}
                                </td>
                                 <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        <Link href={`/servicos/${servico.id}/editar`}>Editar</Link>
                                    </td>

                            </tr>
                           ))}

                           {servicos.length ===0 &&
                           (
                            <tr>
                                <td colSpan={6} className="px-6 py-12 text-center text-[#6B6054] ">
                                    Nenhum serviço encontrado!
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