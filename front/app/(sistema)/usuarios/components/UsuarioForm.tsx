'use client'

import { buscarListaEmpresas } from "@/app/services/empresaService";
import { atualizarUsuario, salvarUsuario } from "@/app/services/usuarioService";
import { Empresa } from "@/app/types/empresa";
import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function UsuarioForm({usuarioExistente}:UsuarioFormProps) {
    const router = useRouter();

const  [usuario, setUsuario] = useState<Usuario>(
    usuarioExistente
    ? { ...usuarioExistente, senha: "", role: (usuarioExistente.role ?? "RECEPCIONISTA").replace("ROLE_", "") }
    : new Usuario(null,"","","ATIVO","","","RECEPCIONISTA", null)
);

// lista de empresas cadastradas, para o admin escolher
const [empresas, setEmpresas] = useState<Empresa[]>([]);

useEffect(() => {
    buscarListaEmpresas().then(setEmpresas);
}, []);

const handlerChange = ( campo : 'nome' | 'email' | 'cpf' | 'senha' | 'role' | 'empresaId', valor:string) =>{
    setUsuario(
        valorAnterior => 
        new Usuario(

            valorAnterior.id,
            campo === 'nome' ? valor : valorAnterior.nome,
            campo === 'email' ? valor : valorAnterior.email,
            valorAnterior.status,
            campo === 'cpf' ? valor : valorAnterior.cpf,
            campo === 'senha' ? valor : valorAnterior.senha,
            campo === 'role' ? valor : valorAnterior.role,
            campo === 'empresaId' ? (valor ? Number(valor) : null) : valorAnterior.empresaId
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(usuarioExistente){
        var dadosRetorno = await atualizarUsuario(usuario);

        if(dadosRetorno){
            alert("Usuário foi salvo com sucesso!");
        } else {
            alert("Erro ao atualizar o usuário!");
    
            return;
        }

    }else{
    var dadosRetorno = await salvarUsuario(usuario);
    if(dadosRetorno){
        alert("Usuário foi salvo com sucesso!");
    }else{
        alert("Erro ao salvar Usuário!");

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
                        required={!usuarioExistente}
                        placeholder="************"
                        onChange={(e)=> handlerChange('senha', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Perfil de acesso:
                    </label>

                    <select
                        name="role"
                        value = {usuario.role}
                        onChange={(e)=> handlerChange('role', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                        <option value="RECEPCIONISTA">Recepcionista</option>
                        <option value="PROFISSIONAL">Profissional</option>
                    </select>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Empresa:
                    </label>

                    <select
                        name="empresa"
                        value = {usuario.empresaId ?? ""}
                        required
                        onChange={(e)=> handlerChange('empresaId', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                        <option value="">Selecione a empresa</option>

                        {empresas.map((empresa) => (
                            <option key={empresa.id} value={empresa.id ?? ""}>
                                {empresa.nomeFantasia}
                            </option>
                        ))}

                    </select>

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