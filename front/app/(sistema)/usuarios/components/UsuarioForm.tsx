'use client'

import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function UsuarioForm({usuarioExistente}:UsuarioFormProps) {
    const router = useRouter();

const  [usuario, setUsuario] = useState<Usuario>(
    usuarioExistente ||
    new Usuario(null,"","","ATIVO","","")
);

const handlerChange = ( campo : 'nome' | 'email' | 'cpf' | 'senha', valor:string) =>{
    setUsuario(
        valorAnterior => 
        new Usuario(

            valorAnterior.id,
            campo === 'nome' ? valor : valorAnterior.nome,
            campo === 'email' ? valor : valorAnterior.email,
            valorAnterior.status,
            campo === 'cpf' ? valor : valorAnterior.cpf,
            campo === 'senha' ? valor : valorAnterior.senha
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(usuarioExistente){
        var dadosRetorno = await axios.put<number>('http://localhost:8080/usuarios'+usuario.id, usuario);

        if(dadosRetorno.status==200){
            alert("Usuário foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);
    
            return;
        }

    }else{
    var dadosRetorno = await axios.post<number>('http://localhost:8080/usuarios',usuario)

    if(dadosRetorno.status==200){
        alert("Usuário foi salvo com sucesso!");
    } else {
        alert(dadosRetorno.data);

        return;
    }
}

    router.push("/usuarios");

    }

    return (

        <form action = {handlerSalvar} className="space-y-6">

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Nome completo:
                    </label>

                    <input
                        name="nome"
                        value = {usuario.nome}
                        required 
                        onChange={(e)=> handlerChange('nome', e.target.value)}
                        placeholder="Maria da Silva"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        CPF:
                    </label>

                    <input
                        name="CPF"
                        value = {usuario.cpf}
                        placeholder="000.000.000-00"
                        onChange={(e)=> handlerChange('cpf', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        E-mail
                    </label>

                    <input
                        name="email"
                        value = {usuario.email}
                        placeholder="EmaildaMaria@silva.com.br"
                        onChange={(e)=> handlerChange('email', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Senha:
                    </label>

                    <input
                        name="Senha"
                        value = {usuario.senha}
                        type="password"
                        placeholder="************"
                        onChange={(e)=> handlerChange('senha', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

            </div>

            <div className="flex items-center justify-end space-x-4 pt-4 border-t border-[#E8DED0]">

                <Link
                    href="/usuarios"
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