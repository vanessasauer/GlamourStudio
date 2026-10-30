export class Equipe{

    constructor(
    
        public id:number | null,
        public nome:string,
        public descricao:string

    ){}
    
}

export interface EquipeFormProps{
    equipeExistente?:Equipe
}
