import { Usuario } from "./usuario";

export class Equipe{

    constructor(
    
        public id:number | null,
        public nome:string,
        public descricao:string,
        public statusEquipe:string,
        public profissionais:Usuario[] = []

    ){}
    
}

export interface EquipeFormProps{
    equipeExistente?:Equipe
}