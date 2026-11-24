'use client'

import axios from "axios";
import { logout } from "../redux/slices/authSlice";
// "import type" só traz o TIPO da store (some na hora de rodar),
// então não cria o círculo store -> agendaSlice -> service -> api -> store
import type { AppStore } from "../redux/store";


const api = axios.create({
    baseURL: 'http://localhost:8080'
});

// evita registrar os interceptors duas vezes (ex.: quando o Next recarrega o arquivo em desenvolvimento)
let interceptorsConfigurados = false;

// Recebe a store do Redux como parâmetro e registra os interceptors.
// É chamada uma única vez, no StoreProvider, onde a store já existe.
export function configurarInterceptors(store: AppStore) {

    if (interceptorsConfigurados) {
        return;
    }
    interceptorsConfigurados = true;

    // Interceptor de REQUISIÇÃO: pega o token da store do Redux e coloca no cabeçalho
    api.interceptors.request.use(
        (config) => {
            const token = store.getState().auth.token;

            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }

            return config;
        }
    );

    // Interceptor de RESPOSTA: se o back-end responder 401 (token expirado ou inválido),
    // despacha o logout para o Redux. O usuário vira null e o layout do sistema
    // renderiza a tela de login sozinho, sem recarregar a página.
    api.interceptors.response.use(
        (response) => response,
        (error) => {
            if (error.response?.status === 401) {
                store.dispatch(logout());
            }

            return Promise.reject(error);
        }
    );
}

export default api