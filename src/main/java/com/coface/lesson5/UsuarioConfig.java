package com.coface.lesson5;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioRepository usuarioRepository() {
        return new DummyRepository();
    }

    @Bean
    @Qualifier("bcrypt")
    public PasswordEncoder passwordBCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Qualifier("md4")
    public PasswordEncoder passwordEncoder() {
        return new Md4PasswordEncoder();
    }
}
