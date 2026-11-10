package com.example.glamourstudio.presentation;


import com.example.glamourstudio.application.services.DddService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ddd")
@Tag(name = "DDD", description = "API responsável por consultar o estado e as cidades de um DDD (via BrasilAPI).")
public class DddController {

    @Autowired
    private DddService dddService;

    @GetMapping("/{ddd}")
    @Operation(summary = "Método de consulta de DDD!",
            description = "Retorna o estado e a lista de cidades que usam o DDD informado.")
    public ResponseEntity<?> buscarDdd(@PathVariable String ddd){

        try {
            return ResponseEntity.ok(dddService.buscarDdd(ddd));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
