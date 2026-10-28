package com.example.glamourstudio.domain.valueobject;

public class TELEFONE {

    private String telefone;

    public TELEFONE() {
        this.telefone = "";
    }

    public TELEFONE(String telefone) {

        if (!telefoneValido(telefone)) {
            throw new IllegalArgumentException("Telefone Inválido!");
        }

        this.telefone = formatar(telefone);
    }

    public static boolean telefoneValido(String telefone) {
        if (telefone == null) {
            return false;
        }

        String numero = telefone.replaceAll("\\D", "");

        return numero.length() == 10 || numero.length() == 11;
    }

    private static String formatar(String telefone) {
        String numero = telefone.replaceAll("\\D", "");

        if (numero.length() == 11) {
            return numero.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        return numero.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return telefone;
    }
}
