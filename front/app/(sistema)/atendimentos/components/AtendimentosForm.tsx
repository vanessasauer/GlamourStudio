"use client"
import { atualizarAtendimento, salvarAtendimento } from "@/app/services/atendimentoService";
import { buscarListaUsuarios } from "@/app/services/usuarioService";
import { Atendimento, AtendimentoFormProps } from "@/app/types/atendimento";
import { Usuario } from "@/app/types/usuario";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function AtendimentosForm({atendimentoExistente}:AtendimentoFormProps) {
    const router = useRouter();

const  [atendimento, setAtendimento] = useState<Atendimento>(
    atendimentoExistente ||
    new Atendimento(null,"","",null,"","",null,"AGENDADO")
);

const [profissionais, setProfissionais] = useState<Usuario[]>([]);

useEffect(()=>{

    carregarProfissionais();

},[]);

const carregarProfissionais = async () => {

    try{

        const dados = await buscarListaUsuarios();

        setProfissionais(dados.filter((usuario)=> usuario.status === "ATIVO"));

    } catch (error){

        alert("Erro ao carregar profissionais")

    }

}

const handlerChange = ( campo : 'cliente' | 'dataHora' | 'servico' | 'valor', valor:string) =>{
    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            campo === 'cliente' ? valor : valorAnterior.cliente,
            valorAnterior.profissional,
            valorAnterior.profissionalId,
            campo === 'dataHora' ? valor : valorAnterior.dataHora,
            campo === 'servico' ? valor : valorAnterior.servico,
            campo === 'valor' ? (valor === "" ? null : Number(valor)) : valorAnterior.valor,
            valorAnterior.statusAtendimento
            
        )
    )
}

// Ao escolher o profissional, guarda o id (para agenda/painel) e o nome (para exibição).
const handlerProfissional = (valor:string) =>{

    const escolhido = profissionais.find((usuario)=> String(usuario.id) === valor);

    setAtendimento(
        valorAnterior => 
        new Atendimento(

            valorAnterior.id,
            valorAnterior.cliente,
            escolhido ? escolhido.nome : "",
            escolhido ? escolhido.id : null,
            valorAnterior.dataHora,
            valorAnterior.servico,
            valorAnterior.valor,
            valorAnterior.statusAtendimento
            
        )
    )
}

   

const handlerSalvar = async (formdata : FormData) =>{

    if(atendimentoExistente){
        var dadosRetorno = await atualizarAtendimento(atendimento);

        if(dadosRetorno){
            alert("Atendimento foi salvo com sucesso!");
        } else {
            alert("Erro ao atualizar atendimento!");
    
            return;
        }

    }else{
    var dadosRetorno = await salvarAtendimento(atendimento);

    if(dadosRetorno){
        alert("Atendimento foi salvo com sucesso!");
    } else {
        alert("Erro ao salvar atendimento!");

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

                    <select
                        name="profissionalId"
                        value = {atendimento.profissionalId ?? ""}
                        required
                        onChange={(e)=> handlerProfissional(e.target.value)}
                        className="w-full px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                    >

                        <option value="">Selecione o profissional</option>

                        {profissionais.map((usuario)=>(

                            <option key={usuario.id} value={usuario.id ?? ""}>
                                {usuario.nome}
                            </option>

                        ))}

                    </select>

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

                <div className="space-y-2">

                    <label className="block text-sm font-medium text-[#6B6054]">
                        Valor (R$):
                    </label>

                    <input
                        name="valor"
                        type="number"
                        min="0"
                        step="0.01"
                        value = {atendimento.valor ?? ""}
                        required
                        placeholder="80.00"
                        onChange={(e)=> handlerChange('valor', e.target.value)}
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