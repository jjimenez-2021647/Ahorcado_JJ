package com.josuejimenez.AplicacionAhorcado.controller;

public class CorreoInvalido extends RuntimeException {
    public CorreoInvalido(String message) {
        super(message);
    }
}
