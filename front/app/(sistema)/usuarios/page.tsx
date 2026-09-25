"use client"

import { Usuario } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Usuarios(){

    const [usuarios, setUsuarios] = useState<Usuario[]>([]);

    useEffect( ()=>{

        carregarDados();

    },[]);

    const carregarDados = async () => {

        try{

            const dados = await axios.get<Usuario[]>("http://localhost:8080/usuarios");

            setUsuarios(dados.data)

        } catch (error){

            alert("Erro ao carregar dados")

        }

        carregarDados();

    }

    const handleDeletarUsuario = async(usuario:Usuario) =>{

        var dadosRetorno = await 
        axios.delete('http://localhost:8080/usuarios/'+usuario.id+'/excluir');

        if(dadosRetorno.status==200){

            alert("Excluído com sucesso!");

        } else {

            alert(dadosRetorno.data);

            return;

        }

        carregarDados();

    }

    const handleAlterarStatusUsuario = async(usuario:Usuario) =>{

        var novoStatus = {};

        if(usuario.status ==="ATIVO"){

            novoStatus = {status:"BLOQUEADO"}

        }else{

            novoStatus = {status:"ATIVO"}

        }

        var dadosRetorno = await  
        axios.patch('http://localhost:8080/usuarios/'+usuario.id+'/status',novoStatus);

        if(dadosRetorno.status==200){

            alert("Atulizado status com sucesso!");

        }else{

            alert(dadosRetorno.data);

            return;

        }

        carregarDados();

    }

    return (

        <div className="w-full bg-[#F8F5F0] p-6 md:p-8 font-sans">

            <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

                <h1 className="text-3xl font-semibold text-[#5C5145]">
                    Gestão de usuários
                </h1>

                <Link
                    href="/usuarios/novo"
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
                                    CPF
                                </th>

                                <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                    E-mail
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

                            {usuarios.map((usuario)=>( 

                                <tr 
                                    key={usuario.id} 
                                    className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]"
                                >

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {usuario.id}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054] font-medium">
                                        {usuario.nome}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {usuario.cpf}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">
                                        {usuario.email}
                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">

                                        <span
                                            className={`inline-flex rounded-full px-3 py-1 text-xs font-medium ${
                                                usuario.status === "ATIVO"
                                                    ? "bg-emerald-50 text-emerald-700"
                                                    : "bg-amber-50 text-amber-700"
                                            }`}
                                        >
                                            {usuario.status}
                                        </span>

                                    </td>

                                    <td className="px-6 py-4 text-sm text-[#6B6054]">

                                        <div className="flex items-center gap-3">

                                            <Link
                                                href={`/usuarios/${usuario.id}/editar`}
                                                className="inline-flex items-center justify-center rounded-lg border border-[#D8CBBB] bg-[#FFFDF9] px-3 py-1.5 text-sm font-medium text-[#6B6054] transition-all duration-200 hover:bg-[#EFE7DC] hover:text-[#4F463D]"
                                            >
                                                Editar
                                            </Link>

                                            <button
                                                onClick={()=> handleAlterarStatusUsuario(usuario)}
                                                className={`relative inline-flex h-7 w-12 items-center rounded-full transition-all duration-300 ${
                                                    usuario.status === "ATIVO"
                                                        ? "bg-emerald-500"
                                                        : "bg-amber-400"
                                                }`}
                                            >

                                                <span
                                                    className={`inline-block h-5 w-5 transform rounded-full bg-white shadow-sm transition-transform duration-300 ${
                                                        usuario.status === "ATIVO"
                                                            ? "translate-x-6"
                                                            : "translate-x-1"
                                                    }`}
                                                ></span>

                                            </button>

                                        </div>

                                    </td>

                                </tr>

                            ))}

                            {usuarios.length ===0 && (

                                <tr>

                                    <td
                                        colSpan={5}
                                        className="px-6 py-12 text-center text-[#8A7D70]"
                                    >
                                        Nenhum usuário encontrado!
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