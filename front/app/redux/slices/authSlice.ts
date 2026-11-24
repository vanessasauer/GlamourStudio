import { AuthState } from "@/app/types/auth";
import { Usuario } from "@/app/types/usuario";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// O token e o usuário ficam SOMENTE aqui na store do Redux (na memória da página).
// Por isso, ao apertar F5 o estado volta para o inicial e é preciso logar de novo.
const initialState: AuthState = {
    usuario: null,
    token: ""
}


const authSlice = createSlice({
    name :'auth',
    initialState,
    reducers:{
        // guarda só o token, logo depois do /auth/login,
        // para que a busca do usuário logado já vá com o token no cabeçalho
        definirToken:(state, action: PayloadAction<string>) =>{
            state.token = action.payload;
        },
        login:( state, action: PayloadAction<{token:string, usuario:Usuario}>) =>{
            state.token = action.payload.token;
            state.usuario = action.payload.usuario;
        },
        logout:(state) =>{
            state.token = "";
            state.usuario = null;
        }
    }
});

export const {definirToken, login, logout} = authSlice.actions;
export default authSlice.reducer;