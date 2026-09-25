export class Atendimento{

    constructor(

        public id:number | null,

        public cliente:string,

        public profissional:string,

        public dataHora:string,

        public servico:string,

        public statusAtendimento:string

    ){}

}

export interface AtendimentoFormProps{

    atendimentoExistente?:Atendimento

}