import { Ddd } from "../types/ddd";
import api from "./api";

// consulta o estado de um DDD; devolve null se o DDD não existir
export async function buscarDdd(ddd:string) : Promise<Ddd | null> {

    try {

        const dados = await api.get<Ddd>("/ddd/" + ddd);

        return dados.data;

    } catch (error) {

        return null;

    }

}