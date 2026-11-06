
import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice"
import agendaReducer from "../redux/slices/agendaSlice"

export const store = configureStore({
    reducer:{
        auth : authReducer,
        agenda : agendaReducer
    }
});

export type RootState = ReturnType<typeof store.getState >;
export type AppDispatch = typeof store.dispatch;
