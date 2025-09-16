package com.josuejimenez.AplicacionAhorcado.controller;

public class ValidacionCorreo {
    private static  String dominioGmail = "@gmail.com";
    private static  String dominioEdu = "@edu.gt";
    private static  String correominusculas;

    public static void validarCorreo (String correo) {

        if (correo == null || correo.trim().isEmpty()){
            throw new CorreoInvalido("El correo no puede estar vacio");
        }
        correominusculas = correo.toLowerCase().trim();

        if (!correominusculas.endsWith(dominioGmail) && !correominusculas.endsWith(dominioEdu)) {
            throw new CorreoInvalido("El correo debe terminar en @gmail.com o @edu.gt");
        }

        if (!correominusculas.matches("^[A-Za-z0-9+_.-]+@(gmail\\.com|edu\\.gt)$")) {
            throw new CorreoInvalido("El formato del correo no es valido");
        }
    }

    public static boolean correoValido(String correo){
        try {
            validarCorreo(correo);
            return true;
        } catch (CorreoInvalido e) {
            return false;
        }
    }
}
