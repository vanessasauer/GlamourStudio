export default function Footer(){

    const anoAtual = new Date().getFullYear();

    return(
        <footer className="w-full bg-[#E8DED0] border-t border-[#D8CBBB] py-6 px-4 text-[#6B6054]">

            <div className="max-w-7xl mx-auto flex flex-col items-center justify-center">

                <div className="text-center">

                    <p className="text-sm font-medium tracking-wide">

                        &copy;{anoAtual} 

                        <span className="text-[#A58F76] font-bold ml-1">GlamourStudio</span>. 

                        Todos os direitos reservados.

                    </p>

                </div>

            </div>

        </footer>
    );

}