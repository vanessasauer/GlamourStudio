import Link from "next/link";

export default function Sidebar(){

    return(

        <aside className="w-64 min-h-screen bg-[#FFFDF9] border-r border-[#E8DED0] flex flex-col p-6 shadow-md">

            <div className="text-xl font-bold text-[#5C5145] tracking-wide mb-8 px-2 flex items-center space-x-2">

                <span className="w-3 h-3 bg-[#B9A58D] rounded-full inline-block"></span>

                <span>GlamourStudio</span>

            </div>

            <nav className="flex flex-col space-y-2">

                <Link 
                    href="/home" 
                    className="flex items-center px-4 py-3 text-[#6B6054] hover:text-[#4F463D] hover:bg-[#EFE7DC] rounded-xl transition-all duration-200 font-medium"
                >
                    Home
                </Link>

                <Link 
                    href="/usuarios" 
                    className="flex items-center px-4 py-3 text-[#6B6054] hover:text-[#4F463D] hover:bg-[#EFE7DC] rounded-xl transition-all duration-200 font-medium"
                >
                    Usuários
                </Link>

                    <Link 
                        href="/servicos" 
                        className="flex items-center px-4 py-3 text-[#6B6054] hover:text-[#4F463D] hover:bg-[#EFE7DC] rounded-xl transition-all duration-200 font-medium"
                    >
                        Serviços
                    </Link>

                    <Link 
                    href="/clientes" 
                    className="flex items-center px-4 py-3 text-[#6B6054] hover:text-[#4F463D] hover:bg-[#EFE7DC] rounded-xl transition-all duration-200 font-medium"
                >
                    Clientes
                </Link>
                <Link 
                    href="/atendimentos" 
                    className="flex items-center px-4 py-3 text-[#6B6054] hover:text-[#4F463D] hover:bg-[#EFE7DC] rounded-xl transition-all duration-200 font-medium"
                >
                    Atendimentos
                </Link>

            </nav>

        </aside>);

}