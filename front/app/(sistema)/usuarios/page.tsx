import Link from "next/link";

export default function Usuarios(){

    return (<div className="min-h-screen bg-[#F8F5F0] px-6 py-10">

        <div className="mx-auto mb-8 flex w-full max-w-5xl items-center justify-between">

            <h1 className="text-3xl font-semibold text-[#5C5145]">
                Gestão de usuários
            </h1>

            <Link 
                href="/usuarios/novo"
                className="rounded-lg bg-[#B9A58D] px-5 py-2.5 text-sm font-medium text-white transition duration-200 hover:bg-[#A58F76]"
            >
                Novo
            </Link>

        </div>

        <div className="mx-auto w-full max-w-5xl">

            <div className="overflow-hidden rounded-2xl border border-[#E8DED0] bg-[#FFFDF9] shadow-sm">

                <table className="w-full text-left">

                    <thead className="bg-[#EFE7DC]">

                        <tr>

                            <th className="px-6 py-4 text-sm font-semibold text-[#5C5145]">
                                Nome
                            </th>

                        </tr>

                    </thead>

                    <tbody>

                        <tr className="border-t border-[#E8DED0] transition hover:bg-[#F5EFE7]">

                            <td className="px-6 py-4 text-sm text-[#6B6054]">
                                Vanessa
                            </td>

                        </tr>

                    </tbody>

                </table>

            </div>

        </div>

    </div>)

}