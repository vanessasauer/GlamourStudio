"use client";

import { useState, useEffect } from "react";
// ☝️ Importa os hooks do React:
// useState -> guarda um valor que pode mudar e atualiza a tela sozinho
// useEffect -> executa uma ação automática em certos momentos


//Lista com as URLs das imagens do carrossel
const imagens = [
  "https://i.postimg.cc/ncvGTbkV/Cabeleleiro.jpg",
  "https://i.postimg.cc/9FGY15p4/escova.jpg",
  "https://i.postimg.cc/hP91MWrX/salao.jpg"
];



 //Componente separado só para o carrossel de imagens.
function Carrossel() {
  const [indiceAtual, setIndiceAtual] = useState(0);
  // "indiceAtual" = posição da imagem exibida agora (começa em 0 = primeira).
  // "setIndiceAtual" = função usada para trocar esse valor.



  // Bloco que troca de imagem automaticamente a cada 4 segundos
  useEffect(() => {
    const intervalo = setInterval(() => { // Cria um "relógio, onde cada 4 segundos, troca para a próxima imagem

      setIndiceAtual((prev) => (prev + 1) % imagens.length); // Avança para a próxima imagem. O "%" (resto da divisão), fazendo voltar para 0 quando chega ao final da lista (looping infinito).
    }, 4000);


    //"Desliga o relógio" quando o componente sai da tela
    return () => clearInterval(intervalo);

  }, []);


    // Função chamada ao clicar no botão "anterior".
  const irParaAnterior = () => {

    setIndiceAtual((prev) => (prev === 0 ? imagens.length - 1 : prev - 1));
    // Se estiver na primeira imagem (0), pula para a última.
    // Senão, apenas volta uma posição.

  };


    // Função chamada ao clicar no botão "próxima".
  const irParaProxima = () => {
    setIndiceAtual((prev) => (prev + 1) % imagens.length);
  };


  return (
    <div className="relative w-full max-w-3xl mx-auto mt-10 rounded-2xl overflow-hidden shadow-lg">
       {/*Caixa que envolve o carrossel: bordas arredondadas, sombra e
          largura máxima para não ficar gigante em telas grandes. */}
 
      
      <img
        src={imagens[indiceAtual]}
        // Mostra a imagem da lista na posição atual (indiceAtual).
        // Quando indiceAtual muda, essa imagem troca automaticamente.

        alt={`Imagem do salão ${indiceAtual + 1}`}
        className="w-full h-[320px] md:h-[420px] object-cover transition-all duration-500"
      />

      {/* Botão anterior */}
      <button
        onClick={irParaAnterior}
        // Ao clicar, executa a função que volta uma imagem.

        aria-label="Imagem anterior"
        className="absolute left-3 top-1/2 -translate-y-1/2 bg-[#F8F5F0]/80 hover:bg-[#F8F5F0] text-[#5C5145] rounded-full w-9 h-9 flex items-center justify-center shadow transition"
      >
        ‹
      </button>

      {/* Botão próxima */}
      <button
        onClick={irParaProxima}
        // Ao clicar, executa a função que avança para a próxima imagem.

        aria-label="Próxima imagem"
        className="absolute right-3 top-1/2 -translate-y-1/2 bg-[#F8F5F0]/80 hover:bg-[#F8F5F0] text-[#5C5145] rounded-full w-9 h-9 flex items-center justify-center shadow transition"
      >
        ›
      </button>



      {/* Bolinhas indicadoras */}
      <div className="absolute bottom-3 left-0 right-0 flex justify-center gap-2">
        {imagens.map((_, indice) => (
             // Percorre a lista de imagens e cria uma bolinha para cada uma.

          <button
            key={indice}
            onClick={() => setIndiceAtual(indice)}
            // Ao clicar numa bolinha, pula direto para aquela imagem.

            aria-label={`Ir para imagem ${indice + 1}`}
            className={`w-2.5 h-2.5 rounded-full transition ${
              indice === indiceAtual ? "bg-[#B8794F]" : "bg-[#E4DCCB]"
                // Se essa bolinha for a da imagem atual, fica terracota.
              // Se não for, fica areia clara.

            }`}
          />
        ))}
      </div>
    </div>
  );
}

export default function Home() {
  return (
    <div className="min-h-screen w-full flex flex-col items-center justify-center bg-[#F8F5F0] px-4 py-12">
      <div className="text-center max-w-2xl">
        <h1 className="text-4xl font-semibold text-[#5C5145]">
          Bem-vindo ao seu sistema de gestão para salões de beleza! ✨
        </h1>
        <p className="mt-4 text-lg text-[#8A7F6E]">
          Organize seus atendimentos, clientes e serviços de forma simples,
          prática e eficiente.
        </p>
      </div>

      <Carrossel />

        {/* Aqui "chamamos" o componente Carrossel criado acima,
          como uma peça encaixada dentro da página Home. */}
          
    </div>
  );
}