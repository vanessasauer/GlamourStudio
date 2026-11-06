package com.example.glamourstudio.infra.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/login",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-resources/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                        // a própria pessoa sempre pode ver seus dados (usado no login)
                        .requestMatchers("/usuarios/buscarusuariologado").authenticated()

                        // usuários e empresas: só admin
                        .requestMatchers("/usuarios/**", "/empresa/**").hasRole("ADMIN")

                        // equipes: recepcionista só vê, admin faz tudo
                        .requestMatchers(HttpMethod.GET, "/equipe/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/equipe/**").hasRole("ADMIN")

                        // serviços: todos veem, só admin altera
                        .requestMatchers(HttpMethod.GET, "/servico/**").hasAnyRole("ADMIN", "RECEPCIONISTA", "PROFISSIONAL")
                        .requestMatchers("/servico/**").hasRole("ADMIN")

                        // clientes: profissional só vê, admin e recepcionista fazem tudo
                        .requestMatchers(HttpMethod.GET, "/cliente/**").hasAnyRole("ADMIN", "RECEPCIONISTA", "PROFISSIONAL")
                        .requestMatchers("/cliente/**").hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // atendimentos: todos
                        .requestMatchers("/atendimento/**").hasAnyRole("ADMIN", "RECEPCIONISTA", "PROFISSIONAL")

                                .anyRequest().authenticated()
                )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


}