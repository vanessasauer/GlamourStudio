export class Usuario{

    constructor(
    
        public id:number | null,
        public nome:string,
        public email:string,
        public status:string,
        public cpf:string,
        public senha:string,
        public role:string = "RECEPCIONISTA",
        public empresaId:number | null = null

    ){}
    
}

export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}
