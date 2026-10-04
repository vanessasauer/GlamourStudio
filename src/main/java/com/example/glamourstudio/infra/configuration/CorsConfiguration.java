package com.example.glamourstudio.infra.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//Configura quais origens e métodos HTTP podem acessar a API.
//Permite a comunicação com o frontend no localhost:3000.

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD");
        //O frontend que está no localhost:3000 pode acessar as rotas da API utilizando esses métodos HTTP.
    }
}
