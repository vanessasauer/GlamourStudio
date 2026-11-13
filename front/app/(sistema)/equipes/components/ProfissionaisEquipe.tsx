'use client'

import { consultaEquipePorId } from "@/app/services/equipeService";
import { adicionarUsuarioNaEquipe, buscarListaUsuarios } from "@/app/services/usuarioService";
import { Usuario } from "@/app/types/usuario";
import { useEffect, useState } from "react";

interface ProfissionaisEquipeProps{
    equipeId:number
}

export default function ProfissionaisEquipe({equipeId}:ProfissionaisEquipeProps) {

    // quem já faz parte da equipe
    const [membros, setMembros] = useState<Usuario[]>([]);

    // profissionais da empresa que ainda não estão na equipe
    const [disponiveis, setDisponiveis] = useState<Usuario[]>([]);

    const [selecionado, setSelecionado] = useState("");

    useEffect(()=>{

        carregarDados();

    },[]);

    const carregarDados = async () => {

        try{

            const equipe = await consultaEquipePorId(equipeId);
            const membrosEquipe = equipe.profissionais ?? [];
            setMembros(membrosEquipe);

            const usuarios = await buscarListaUsuarios();
            setDisponiveis(usuarios.filter((usuario)=>
                usuario.status === "ATIVO"
                && usuario.role === "ROLE_PROFISSIONAL"
                && !membrosEquipe.some((membro)=> membro.id === usuario.id)
            ));

        } catch (error){

            alert("Erro ao carregar profissionais da equipe")

        }

    }

    const handlerAdicionar = async () => {

        if(!selecionado){
            alert("Selecione um profissional!");
            return;
        }

        const adicionou = await adicionarUsuarioNaEquipe(Number(selecionado), equipeId);

        if(adicionou){
            alert("Profissional adicionado à equipe!");
            setSelecionado("");
            carregarDados();
        }else{
            alert("Erro ao adicionar profissional!");
        }

    }

    return (

        <div className="space-y-4">

            <h2 className="text-lg font-semibold text-[#5C5145]">
                Profissionais da equipe
            </h2>

            {membros.length === 0 ? (

                <p className="text-sm text-[#8A7D70]">
                    Nenhum profissional nesta equipe ainda.
                </p>

            ) : (

                <ul className="divide-y divide-[#E8DED0] border border-[#E8DED0] rounded-xl">

                    {membros.map((membro)=>(

                        <li key={membro.id} className="px-4 py-3 text-[#4F463D] flex justify-between">
                            <span>{membro.nome}</span>
                            <span className="text-sm text-[#8A7D70]">{membro.email}</span>
                        </li>

                    ))}

                </ul>

            )}

            <div className="flex flex-col sm:flex-row gap-3 pt-2">

                <select
                    value={selecionado}
                    onChange={(e)=> setSelecionado(e.target.value)}
                    className="flex-1 px-4 py-2.5 bg-[#FAF7F2] border border-[#D8CBBB] rounded-xl text-[#4F463D] focus:outline-none focus:ring-2 focus:ring-[#D8CBBB] focus:border-[#B9A58D] transition-all duration-200 shadow-inner"
                >

                    <option value="">Selecione um profissional</option>

                    {disponiveis.map((usuario)=>(

                        <option key={usuario.id} value={usuario.id ?? ""}>
                            {usuario.nome}
                        </option>

                    ))}

                </select>

                <button
                    type="button"
                    onClick={handlerAdicionar}
                    className="px-6 py-2.5 bg-[#B9A58D] hover:bg-[#A58F76] text-white font-semibold text-sm rounded-xl shadow-md transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-[#D8CBBB]"
                >
                    Adicionar
                </button>

            </div>

        </div>

    );

}