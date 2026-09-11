package com.liowarrior.users_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import  org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Configuration / Especifica que esta clase se define Beans (Objetos administrador por Framework)
@Configuration
public class Config {

     //@Bean Fabrica que registra metodos como fabrica de un objeto
    //Cualquier clase que necesite un PasswordEncoder, Spring le entrega esta misma instancia

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //CSRF - proteccion pensada para formularios HTML tradicional
                //Nuestra API es stateless (sin sesion)
                .csrf(csrf -> csrf.disable())
                //Peticiones explicitas
                .authorizeHttpRequests(auth -> auth
                        // Autorizacion sin Login
                        //Por ahora, todos los endpoints de "/api/users", "/api/users/**" quedan abierto
                        .requestMatchers("/api/users", "/api/users/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
