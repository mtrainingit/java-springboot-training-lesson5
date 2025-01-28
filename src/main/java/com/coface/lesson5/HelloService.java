package com.coface.lesson5;

public class HelloService implements MensajeService {
    @Override
    public String enviarMensaje() {
        return "hello";
    }
}
