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
                                "/usuarios/admin",
                                "/servico/adminServico",
                                "/error",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // a própria pessoa sempre pode ver seus dados (usado no login)
                        .requestMatchers("/usuarios/buscarusuariologado").authenticated()

                        // lista de usuários: usada no formulário de atendimento para escolher o profissional
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("ADMIN", "RECEPCIONISTA", "PROFISSIONAL")

                        // adicionar profissional numa equipe: recepcionista (que cuida das equipes)
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/equipe").hasRole("RECEPCIONISTA")

                        // usuários e empresas: só admin
                        .requestMatchers("/usuarios/**", "/empresa/**").hasRole("ADMIN")

                        // equipes: só recepcionista
                        .requestMatchers("/equipe/**").hasRole("RECEPCIONISTA")

                        // serviços: recepcionista faz tudo, profissional só vê
                        .requestMatchers(HttpMethod.GET, "/servico/**").hasAnyRole("RECEPCIONISTA", "PROFISSIONAL")
                        .requestMatchers("/servico/**").hasRole("RECEPCIONISTA")

                        // clientes: recepcionista faz tudo, profissional só vê
                        .requestMatchers(HttpMethod.GET, "/cliente/**").hasAnyRole("RECEPCIONISTA", "PROFISSIONAL")
                        .requestMatchers("/cliente/**").hasRole("RECEPCIONISTA")

                        // atendimentos: recepcionista e profissional
                        .requestMatchers("/atendimento/**").hasAnyRole("RECEPCIONISTA", "PROFISSIONAL")

                        .anyRequest().authenticated()
                )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


}