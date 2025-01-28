package com.coface.lesson5;

public class HolaService implements MensajeService {

    @Override
    public String enviarMensaje() {
        return "hola";
    }
}
