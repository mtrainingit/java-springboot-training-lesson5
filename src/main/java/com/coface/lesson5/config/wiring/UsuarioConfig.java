package com.coface.lesson5.config.wiring;

import com.coface.lesson5.db.dao.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Bean
    @Qualifier("dummy")
    public UsuarioRepository usuarioDummyRepository() {
        return new UsuarioDummyRepository();
    }

    @Bean
    @Qualifier("jdbc")
    public UsuarioRepository usuarioJDBCRepository(JdbcTemplate jdbcTemplate) {
        return new UsuarioJDBCRepository(jdbcTemplate);
    }

    @Bean
    @Qualifier("jpa")
    public UsuarioRepository usuarioJPARepositoryAdapter(UsuarioJPARepository usuarioJPARepository) {
        return new UsuarioJPARepositoryAdapter(usuarioJPARepository);
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
