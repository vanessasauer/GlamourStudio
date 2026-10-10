package com.example.glamourstudio.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.glamourstudio.application.DTOs.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //A TokenService é uma classe de serviço responsável pelo JWT.
    // Ela possui um método para gerar tokens, um método para verificar tokens e um método auxiliar para calcular a data de expiração.

    @Value("${spring.secretkey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long tempo;

    private String emissor = "DEVTEST";


    public String gerarToken(LoginRequest loginRequest) {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer(emissor)
                    .withSubject(loginRequest.email())
                    .withExpiresAt(this.gerarDataExpiracao())
                    .sign(algorithm);

            return token;
        }

        public DecodedJWT verificarToken(String token) throws JWTVerificationException {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

            return verificador.verify(token);

        }

    private Instant gerarDataExpiracao(){
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));

    }

}
