package com.coface.lesson5;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    @Qualifier("spanish")
    public MensajeService holaService() {
        return new HolaService();
    }

    @Bean
    @Qualifier("english")
    public MensajeService helloService() {
        return new HelloService();
    }

    @Bean
    public UsuarioRepository usuarioRepository() {
        return new DummyRepository();
    }
}
