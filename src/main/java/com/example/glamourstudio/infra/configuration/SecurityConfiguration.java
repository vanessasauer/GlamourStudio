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

    // Roles: ADMIN, RECEPCIONISTA, USER
    // (no banco ficam como ROLE_ADMIN, ROLE_RECEPCIONISTA, ROLE_USER;
    //  o hasRole("X") já procura por "ROLE_X" automaticamente)
    // ATENÇÃO: o primeiro match vence, então as regras específicas vêm antes das genéricas.

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // ===== PÚBLICO =====
                        .requestMatchers("/auth/login",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // ===== USUÁRIOS =====
                        // Listar usuários: ADMIN e RECEPCIONISTA (a recepcionista precisa escolher o profissional ao agendar)
                        // (precisa vir ANTES de "/usuarios/**")
                        .requestMatchers(HttpMethod.GET, "/usuarios")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // ===== APENAS ADMIN =====
                        // Todo o resto de usuários (inclui /usuarios/admin, /status, /equipe, /excluir, /perfil...)
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // Empresas
                        .requestMatchers("/empresa/**").hasRole("ADMIN")

                        // ===== SERVIÇOS =====
                        // Consultar: todos | Criar, editar, status, excluir, /adminServico: só ADMIN
                        .requestMatchers(HttpMethod.GET, "/servico", "/servico/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA", "USER")
                        .requestMatchers("/servico/**").hasRole("ADMIN")

                        // ===== EQUIPES =====
                        // Consultar: todos | Criar, editar, status, excluir: só ADMIN
                        .requestMatchers(HttpMethod.GET, "/equipe", "/equipe/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA", "USER")
                        .requestMatchers("/equipe/**").hasRole("ADMIN")

                        // ===== CLIENTES =====
                        // Consultar: todos
                        .requestMatchers(HttpMethod.GET, "/cliente", "/cliente/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA", "USER")
                        // Excluir: só ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/cliente/*/excluir")
                        .hasRole("ADMIN")
                        // Criar, editar, alterar status: ADMIN e RECEPCIONISTA
                        .requestMatchers(HttpMethod.POST, "/cliente")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.PUT, "/cliente/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.PATCH, "/cliente/*/status")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // ===== ATENDIMENTOS =====
                        // Agenda do dia e painel do mês do profissional logado: ADMIN (a dona atende) e USER
                        // (precisa vir ANTES de "/atendimento/*", senão esse padrão captura essas rotas)
                        .requestMatchers(HttpMethod.GET, "/atendimento/minha-agenda", "/atendimento/painel")
                        .hasAnyRole("ADMIN", "USER")
                        // Consultar lista completa e por id: ADMIN e RECEPCIONISTA
                        .requestMatchers(HttpMethod.GET, "/atendimento", "/atendimento/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")
                        // Alterar status (ex.: marcar como ATENDIDO): todos
                        .requestMatchers(HttpMethod.PATCH, "/atendimento/*/status")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA", "USER")
                        // Criar, editar e cancelar: ADMIN e RECEPCIONISTA
                        .requestMatchers(HttpMethod.POST, "/atendimento")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.PUT, "/atendimento/*")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.DELETE, "/atendimento/*/excluir")
                        .hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // SEMPRE por último
                        .anyRequest().authenticated()
                )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}