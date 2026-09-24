import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

export default function SistemaLayout({children}){

    return (

        <div className="flex min-h-screen bg-[#F8F5F0]">

            <Sidebar />

            <div className="flex-1 flex flex-col min-w-0 overflow-hidden">

                <Header />

                <main className="flex-1 overflow-y-auto bg-[#F8F5F0] p-6 text-[#5C5145]">

                    {children}

                </main>

                <Footer /> 

            </div>

        </div>

    );
}