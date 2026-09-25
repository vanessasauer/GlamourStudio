export class Servico{
    constructor(
        public id:number | null,
        public nome:string,
        public descricao:string,
        public valor:number,
        public duracaoMinutos:number,
        public status:string
    ){}
}

export interface ServicoFormProps{
    servicoExistente?:Servico
}