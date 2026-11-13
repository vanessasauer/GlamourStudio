"use client"
import { useRouter } from "next/navigation";
import Footer from "../components/Footer";
import dynamic from "next/dynamic";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";

// menu e cabeçalho dependem do cookie do usuário, que só existe no navegador
const Sidebar = dynamic(() => import("../components/Sidebar"), { ssr: false });
const Header = dynamic(() => import("../components/Header"), { ssr: false });

export default function SistemaLayout({children}){

    const usuario = useSelector((state:RootState)=> state.auth.usuario);

    const router = useRouter();

    useEffect(()=>{
        if(usuario == null){
            router.push("/login");
        }
    })

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