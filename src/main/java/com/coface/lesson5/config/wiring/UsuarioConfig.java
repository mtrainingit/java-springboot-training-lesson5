package com.coface.lesson5.config.wiring;

import com.coface.lesson5.db.dao.DummyRepository;
import com.coface.lesson5.db.dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioRepository usuarioDummyRepository() {
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
