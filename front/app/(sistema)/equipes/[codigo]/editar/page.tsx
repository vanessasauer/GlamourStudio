"use client"

import { consultaEquipePorId } from "@/app/services/equipeService";
import { Equipe } from "@/app/types/equipe";
import Link from "@/node_modules/next/link";
import { useParams, useRouter } from "@/node_modules/next/navigation";
import { useEffect, useState } from "react";
import EquipeForm from "../../components/EquipeForm";



export default function EditarEquipe(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [equipe, setEquipe] = useState<Equipe|null>(null)

    const router = useRouter();

    useEffect(()=>{

        buscarDados();

    },[]);

    const buscarDados = async() =>{

        const valorEquipeBack = await consultaEquipePorId(codigo);
        if(valorEquipeBack.statusEquipe){
            setEquipe(valorEquipeBack);
        }else{
            router.push("/equipes")
        }

    
    }


    if(!equipe) return(<div className="p-8"> Carregando Dados ... </div>)

    return(

        <div className="space-y-6">

            <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 bg-[#FFFDF9] border border-[#E8DED0] p-6 rounded-2xl shadow-sm">

                <div className="space-y-1">

                    <h1 className="text-2xl font-bold tracking-tight text-[#5C5145] flex items-center space-x-2">

                        <span className="w-2.5 h-2.5 bg-[#B9A58D] rounded-full inline-block"></span>

                        <span>Editar Equipe {codigo}</span>

                    </h1>

                    <p className="text-sm text-[#8A7D70]">
                        Preencha os dados para editar a Equipe
                    </p>

                </div>

                <Link
                    href="/equipes"
                    className="inline-flex items-center justify-center text-sm font-medium text-[#6B6054] hover:text-[#4F463D] bg-[#EFE7DC] hover:bg-[#E8DED0] border border-[#D8CBBB] px-4 py-2.5 rounded-xl transition-all duration-200 shadow-sm w-full sm:w-auto"
                >
                    &larr; Voltar para Listagem
                </Link>

            </div>

            <div className="bg-[#FFFDF9] border border-[#E8DED0] rounded-2xl p-6 md:p-8 shadow-sm">

                <EquipeForm equipeExistente={equipe}/>

            </div>

        </div>

    )

}
