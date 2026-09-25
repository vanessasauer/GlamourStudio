"use client"
import { Atendimento, AtendimentoFormProps } from "@/app/types/atendimento";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function AtendimentosForm({atendimentoExistente}:AtendimentoFormProps) {
    const router = useRouter();

const  [atendimento, setAtendimento] = useState<Atendimento>(
    atendimentoExistente ||
    new Atendimento(null,"","","","","AGENDADO")
);

const handlerChange = ( campo : 'cliente' | 'profissional' | 'dataHora' | 'servico', valor:string) =>{
    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            campo === 'cliente' ? valor : valorAnterior.cliente,
            campo === 'profissional' ? valor : valorAnterior.profissional,
            campo === 'dataHora' ? valor : valorAnterior.dataHora,
            campo === 'servico' ? valor : valorAnterior.servico,
            valorAnterior.statusAtendimento
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(atendimentoExistente){
        var dadosRetorno = await axios.put<number>('http://localhost:8080/atendimento/'+atendimento.id, atendimento);

        if(dadosRetorno.status==200){
            alert("Atendimento foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);
    
            return;
        }

    }else{
    var dadosRetorno = await axios.post<number>('http://localhost:8080/atendimento',atendimento)

    if(dadosRetorno.status==200){
        alert("Atendimento foi salvo com sucesso!");
    } else {
        alert(dadosRetorno.data);

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

                    <input
                        name="cliente"
                        value = {atendimento.cliente}
                        required 
                        onChange={(e)=> handlerChange('cliente', e.target.value)}
                        placeholder="Maria da Silva"
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Profissional:
                    </label>

                    <input
                        name="profissional"
                        value = {atendimento.profissional}
                        placeholder="Karina Oliveira"
                        onChange={(e)=> handlerChange('profissional', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

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

                    <input
                        name="servico"
                        value = {atendimento.servico}
                        placeholder="Manicure"
                        onChange={(e)=> handlerChange('servico', e.target.value)}
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