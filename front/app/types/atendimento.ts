export class Atendimento{

    constructor(

        public id:number | null,
 
        public cliente:string,
 
        public profissional:string,
 
        public profissionalId:number | null,
 
        public dataHora:string,
 
        public servico:string,
 
        public valor:number | null,
 
        public statusAtendimento:string
 
    ){}
 
}
 
export interface AtendimentoFormProps{
 
    atendimentoExistente?:Atendimento
 
}
 
export interface PainelFaturamento{
 
    mes:string,
 
    quantidadeAtendimentos:number,
 
    totalFaturado:number
 
}
