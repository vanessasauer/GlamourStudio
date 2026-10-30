export class Empresa{

    constructor(
    
        public id:number | null,
        public razaoSocial:string,
        public cnpj:string,
        public nomeFantasia:string

    ){}
    
}

export interface EmpresaFormProps{
    empresaExistente?:Empresa
}
