export class Equipe{

    constructor(
    
        public id:number | null,
        public nome:string,
        public descricao:string,
        public statusEquipe:string



    ){}
    
}

export interface EquipeFormProps{
    equipeExistente?:Equipe
}
