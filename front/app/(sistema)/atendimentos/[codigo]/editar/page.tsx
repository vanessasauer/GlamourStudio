import Link from "next/link";
import { useParams } from "next/navigation";
import AtendimentosForm from "../../components/AtendimentosForm";

export default function EditarAtendimento(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    return(

        <div className="space-y-6">

            <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 bg-[#FFFDF9] border border-[#E8DED0] p-6 rounded-2xl shadow-sm">

                <div className="space-y-1">

                    <h1 className="text-2xl font-bold tracking-tight text-[#5C5145] flex items-center space-x-2">

                        <span className="w-2.5 h-2.5 bg-[#B9A58D] rounded-full inline-block"></span>

                        <span>Editar Atendimento {codigo}</span>

                    </h1>

                    <p className="text-sm text-[#8A7D70]">
                        Preencha os dados para editar o Atendimento
                    </p>

                </div>

                <Link
                    href="/atendimentos"
                    className="inline-flex items-center justify-center text-sm font-medium text-[#6B6054] hover:text-[#4F463D] bg-[#EFE7DC] hover:bg-[#E8DED0] border border-[#D8CBBB] px-4 py-2.5 rounded-xl transition-all duration-200 shadow-sm w-full sm:w-auto"
                >
                    &larr; Voltar para Listagem
                </Link>

            </div>

            <div className="bg-[#FFFDF9] border border-[#E8DED0] rounded-2xl p-6 md:p-8 shadow-sm">

                <AtendimentosForm/>

            </div>

        </div>

    )

}