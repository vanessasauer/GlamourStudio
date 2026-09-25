"use client"
import { Cliente, ClienteFormProps } from "@/app/types/cliente";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function ClientesForm({clienteExistente}:ClienteFormProps) {
    const router = useRouter();

const  [cliente, setCliente] = useState<Cliente>(
    clienteExistente ||
    new Cliente(null,"","","","","")
);

const handlerChange = ( campo : 'nome' | 'dataNascimento' | 'email' | 'telefone', valor:string) =>{
    setCliente(
        valorAnterior => 
        new Cliente(

            valorAnterior.id,
            campo === 'nome' ? valor : valorAnterior.nome,
            campo === 'dataNascimento' ? valor : valorAnterior.dataNascimento,
            campo === 'email' ? valor : valorAnterior.email,
            campo === 'telefone' ? valor : valorAnterior.telefone,
            valorAnterior.status
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(clienteExistente){
        var dadosRetorno = await axios.put<number>('http://localhost:8080/cliente/'+cliente.id, cliente);

        if(dadosRetorno.status==200){
            alert("Cliente foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);
    
            return;
        }

    }else{
    var dadosRetorno = await axios.post<number>('http://localhost:8080/cliente',cliente)

    if(dadosRetorno.status==200){
        alert("Cliente foi salvo com sucesso!");
    } else {
        alert(dadosRetorno.data);

        return;
    }
}

    router.push("/clientes");

    }

    return (

        <form action = {handlerSalvar} className="space-y-6">

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Nome do Cliente:
                    </label>

                    <input
                        name="nome"
                        value = {cliente.nome}
                        required 
                        onChange={(e)=> handlerChange('nome', e.target.value)}
                        placeholder="Maria da Silva"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Data de Nascimento:
                    </label>

                    <input
                        name="dataNascimento"
                        value = {cliente.dataNascimento}
                        required
                        onChange={(e)=> handlerChange('dataNascimento', e.target.value)}
                        placeholder="01/01/2000"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Email:
                    </label>

                    <input
                        name="email"
                        value = {cliente.email}
                        required
                        onChange={(e)=> handlerChange('email', e.target.value)}
                        placeholder="maria.silva@example.com"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Telefone:
                    </label>

                    <input
                        name="telefone"
                        value = {cliente.telefone}
                        required
                        onChange={(e)=> handlerChange('telefone', e.target.value)}
                        placeholder="(11) 99999-9999"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

            </div>
                       

            <div className="flex items-center justify-end space-x-4 pt-4 border-t border-[#E8DED0]">

                <Link
                    href="/clientes"
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