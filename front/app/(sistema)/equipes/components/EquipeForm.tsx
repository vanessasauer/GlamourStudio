'use client'

import { atualizarEquipe, salvarEquipe } from "@/app/services/equipeService";
import { Equipe, EquipeFormProps } from "@/app/types/equipe";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function EquipeForm({equipeExistente}:EquipeFormProps) {
    const router = useRouter();

const  [equipe, setEquipe] = useState<Equipe>(
    equipeExistente ||
    new Equipe(null,"","","ATIVO")
);

const handlerChange = ( campo : 'nome' | 'descricao', valor:string) =>{
    setEquipe(
        valorAnterior => 
        new Equipe(

            valorAnterior.id,
            campo === 'nome' ? valor : valorAnterior.nome,
            campo === 'descricao' ? valor : valorAnterior.descricao,
            valorAnterior.statusEquipe
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(equipeExistente){
        var dadosRetorno = await atualizarEquipe(equipe);

        if(dadosRetorno){
            alert("Equipe foi atualizada com sucesso!");
        } else {
            alert("Erro ao atualizar a equipe!");
    
            return;
        }

    }else{
    var dadosRetorno = await salvarEquipe(equipe);
    if(dadosRetorno){
        alert("Equipe foi salva com sucesso!");
    }else{
        alert("Erro ao salvar Equipe!");

        return;
    }
}

    router.push("/equipes");

    }

    return (

        <form action = {handlerSalvar} className="space-y-6">

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Nome:
                    </label>

                    <input
                        name="nome"
                        value = {equipe.nome}
                        required 
                        onChange={(e)=> handlerChange('nome', e.target.value)}
                        placeholder="Equipe de Atendimento"
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
                        value = {equipe.descricao}
                        placeholder="Descrição da equipe"
                        onChange={(e)=> handlerChange('descricao', e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] placeholder:text-[#B8ADA2] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >
                    </input>

                </div>

            </div>

            <div className="flex items-center justify-end space-x-4 pt-4 border-t border-[#E8DED0]">

                <Link
                    href="/equipes"
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