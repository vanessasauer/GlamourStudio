'use client'

import { Provider } from "react-redux"
import { store } from "./store"
import { configurarInterceptors } from "../services/api"

// registra os interceptors do Axios passando a store como parâmetro (uma única vez)
configurarInterceptors(store);

export default function StoreProvider({ children }: { children: React.ReactNode }) {
  return <Provider store={store}>{children}</Provider>
}