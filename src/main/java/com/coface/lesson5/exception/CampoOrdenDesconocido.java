package com.coface.lesson5.exception;

public class CampoOrdenDesconocido extends RuntimeException {
    public CampoOrdenDesconocido(String mensaje) {
        super(mensaje);
    }
}
