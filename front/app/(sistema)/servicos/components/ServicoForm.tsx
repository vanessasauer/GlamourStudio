"use client"
import { Servico, ServicoFormProps } from "@/app/types/servico";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function ServicoForm({servicoExistente}:ServicoFormProps) {
    const router = useRouter();

const  [servico, setServico] = useState<Servico>(
    servicoExistente ||
    new Servico(null,"","",0,0,"")
);

const handlerChange = ( campo : 'nome' | 'descricao' | 'valor' | 'duracaoMinutos' | 'status', valor:string) =>{
    setServico(
        valorAnterior => 
        new Servico(

            valorAnterior.id,
            campo === 'nome' ? valor : valorAnterior.nome,
            campo === 'descricao' ? valor : valorAnterior.descricao,
            campo === 'valor' ? Number(valor) : valorAnterior.valor,
            campo === 'duracaoMinutos' ? Number(valor) : valorAnterior.duracaoMinutos,
            valorAnterior.status 
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(servicoExistente){
        var dadosRetorno = await axios.put<number>('http://localhost:8080/servico'+servico.id, servico);

        if(dadosRetorno.status==200){
            alert("Serviço foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);
    
            return;
        }

    }else{
    var dadosRetorno = await axios.post<number>('http://localhost:8080/servico',servico)

    if(dadosRetorno.status==200){
        alert("Serviço foi salvo com sucesso!");
    } else {
        alert(dadosRetorno.data);

        return;
    }
}

    router.push("/servicos");

    }

    return (

        <form action = {handlerSalvar} className="space-y-6">

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Nome do Serviço:
                    </label>

                    <input
                        name="nome"
                        value = {servico.nome}
                        required 
                        onChange={(e)=> handlerChange('nome', e.target.value)}
                        placeholder="Escova Progressiva"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Descrição:
                    </label>

                    <input
                        name="descricao"
                        value = {servico.descricao}
                        placeholder="Descrição do serviço"
                        onChange={(e)=> handlerChange('descricao', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Valor:
                    </label>

                    <input
                        name="valor"
                        value = {servico.valor}
                        placeholder="0,00"
                        onChange={(e)=> handlerChange('valor', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Duração (minutos):
                    </label>

                    <input
                        name="duracaoMinutos"
                        value = {servico.duracaoMinutos}
                        placeholder="0"
                        onChange={(e)=> handlerChange('duracaoMinutos', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

            </div>

            <div className="flex items-center justify-end space-x-4 pt-4 border-t border-[#E8DED0]">

                <Link
                    href="/servicos"
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