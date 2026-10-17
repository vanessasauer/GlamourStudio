package com.example.glamourstudio.domain.valueobject;

public class EMAIL {

    private String email;

    public EMAIL() {
        this.email = "";
    }

    public EMAIL(String email) {
        if (!emailValido(email)) {
            throw new IllegalArgumentException("E-mail Inválido!");
        }
        this.email = email;
    }

    public static boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        if (!email.isEmpty() && !email.contains("@")) {
            return false;
        }

        return true;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.email;
    }
}
